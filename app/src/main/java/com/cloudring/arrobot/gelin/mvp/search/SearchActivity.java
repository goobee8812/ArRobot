package com.cloudring.arrobot.gelin.mvp.search;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.adapter.AppAdapter;
import com.cloudring.arrobot.gelin.adapter.KeyboardAdapter;
import com.cloudring.arrobot.gelin.adapter.OnItemClickCallback;
import com.cloudring.arrobot.gelin.contentdb.AppInfo;
import com.cloudring.arrobot.gelin.contentdb.AppInfoDao;
import com.cloudring.arrobot.gelin.download.FileHelper;
import com.cloudring.arrobot.gelin.download.UniversalDialog;
import com.cloudring.arrobot.gelin.download.Utils;
import com.cloudring.arrobot.gelin.download.test.DownloadUtil;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.cloudring.arrobot.gelin.utils.LogUtil;
import com.cloudring.arrobot.gelin.utils.MyUtil;
import com.cloudring.arrobot.gelin.utils.ToastUtils;
import com.cloudring.arrobot.gelin.utils.WaitDialog;
import com.getlearn.library.GetLearnSdk;
import com.getlearn.library.Interface.OnApkInstallListener;
import com.getlearn.library.Interface.OnUrlListener;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends MvpAppCompatActivity implements SearchView{

    @BindView(R.id.id_normal_recycler_view)
    RecyclerView normalRecycler;
    @BindView(R.id.keyboard_recycler_view)
    RecyclerView recyclerKey;
    @BindView(R.id.id_back_iv)
    ImageView backImg;
    @BindView(R.id.id_top_title_tv)
    TextView titleTv;
    @BindView(R.id.search_et)
    EditText etSearch;
    @BindView(R.id.search_iv)
    ImageView ivSearch;
    private String keyString = "";
    private List<AppItem> normalList;
    private GetLearnSdk mGetLearnSdk;
    private AppInfo appInfo1 = new AppInfo();
    private static final int REFRESH_DATA = 0x001;      //刷新数据
    private static final int START_DOWNLOAD = 0x002;      //启动下载

    private String[] keys = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private KeyboardAdapter adapter;

    @InjectPresenter
    public SearchPresenter mPresenter;

    private AppAdapter normalAdapter;

    private SearchActivity.MyHandler myHandler = new SearchActivity.MyHandler(this);
    static class MyHandler extends Handler{
        WeakReference<SearchActivity> mActivityReference;

        MyHandler(SearchActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final SearchActivity activity = mActivityReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case REFRESH_DATA:
                        LogUtil.LogShow("normalList.sizeREFRESH_DATA = " + activity.normalList.size(), LogUtil.ERROR);
                        activity.normalAdapter.setDataChanged(activity.normalList);
                        //    Toast.makeText(activity, "更新数据" + activity.normalList.get(0).getFileName(), Toast.LENGTH_SHORT).show();
                        break;
                    case START_DOWNLOAD:
                        String url = (String) msg.obj;
                        activity.downFile(url, activity);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        titleTv.setText("搜索");
        MyUtil.disableShowSoftInput(etSearch);
        GridLayoutManager localGridLayoutManager = new GridLayoutManager(this, 5);
        recyclerKey.setLayoutManager(localGridLayoutManager);
        adapter = new KeyboardAdapter(this, keys);
        recyclerKey.setAdapter(adapter);

        mGetLearnSdk = new GetLearnSdk();
        initData();

        //安装apk 回调
        mGetLearnSdk.setOnApkInstallListener(new OnApkInstallListener(){
            @Override
            public void sdkSetApkInsta(String s, String packName){
                LogUtil.LogShow("安装结果是：" + s+"----包名="+packName, LogUtil.DEBUG);//0 成功, 1 失败
                if (s.equals("0")) {
                    //安装成功，写入数据库，显示在游戏界面，根据包名跳转
                    appInfo1.setPackageName(packName);
                    AppInfoDao.add(appInfo1);
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            ToastUtils.showToast(SearchActivity.this, "安装成功，可到我的游戏中打开游戏");
                        }
                    });
                }else {
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            ToastUtils.showToast(SearchActivity.this, "更新失败，请先卸载后安装");
                        }
                    });
                }
                waitDialog.dismiss();
            }
        });

        //url回调
        mGetLearnSdk.setOnUrlListener(new OnUrlListener() {
            @Override
            public void sdkGetTempUrl(String s, String s1, String s2, String s3) {
                LogUtil.LogShow("状态码：" + s + "，资源地址：" + s2, LogUtil.DEBUG);
                if (s.equals("0")) {
                    Message message = Message.obtain();
                    message.what = START_DOWNLOAD;
                    message.obj = s2;
                    myHandler.sendMessage(message);
                }
            }
        });
    }

    private void initData(){
        normalList = new ArrayList<>();
        normalAdapter = new AppAdapter(normalList, new OnItemClickCallback<AppItem>() {
            @Override
            public void onClick(View view, AppItem info, int position) {
                if(MyUtil.isFastClick()){
                    return;
                }
           //     Toast.makeText(SearchActivity.this, "点击了下载" + info.getId(), Toast.LENGTH_SHORT).show();
                mGetLearnSdk.getResUrl(SearchActivity.this, info.getId());
                //标记对象
                appInfo1.setId(info.getId());
                appInfo1.setCategoryId(info.getCategoryId()+"");
                appInfo1.setFileName(info.getFileName());
                appInfo1.setTopCategoryId(info.getTopCategoryId());
                appInfo1.setIcon1(info.getIcon1());
                appInfo1.setType("1");
            }
        }, new OnItemClickCallback<AppItem>() {
            @Override
            public void onClick(View view, AppItem info, int position) {
                if(MyUtil.isFastClick()){
                    return;
                }
                AppInfo appInfo = new AppInfo();
                appInfo.setId(info.getId());
                appInfo.setCategoryId(info.getCategoryId()+"");
                appInfo.setFileName(info.getFileName());
                appInfo.setTopCategoryId(info.getTopCategoryId());
                appInfo.setIcon1(info.getIcon1());
                appInfo.setType("2");

                AppInfoDao.add(appInfo);
                ToastUtils.showToast(SearchActivity.this,"收藏成功");
                //删掉数据
              /*  normalList.remove(position);
                refreshList(normalList);*/
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        normalRecycler.setLayoutManager(gridLayoutManager);
        normalRecycler.setAdapter(normalAdapter);

        mPresenter.getSearchList("", SearchActivity.this);
    }

    @OnClick({R.id.id_back_iv, R.id.search_iv})
    public void onClickEvent(View v){
        switch(v.getId()){
            case R.id.id_back_iv:
                finish();
                break;
            case R.id.search_iv://搜索
                String keyContent = etSearch.getText().toString();
                mPresenter.getSearchList(keyContent, SearchActivity.this);
                break;
            default:
                break;
        }
    }

    public void setKey(String key){
        LogUtil.LogShow("key = " + key, LogUtil.ERROR);
        keyString = keyString + key;
        LogUtil.LogShow("keyString = " + keyString, LogUtil.ERROR);
        etSearch.setText(keyString);
        etSearch.setSelection(keyString.length());
    }

    @Override
    public void showMsg(String msg){
    }

    @Override
    public void showMsg(int msg){
    }

    @Override
    public void loadFail(){
    }

    @Override
    public void refreshList(List<AppItem> list){
        normalList = list;
        myHandler.sendEmptyMessage(REFRESH_DATA);
    }


    UniversalDialog noticeDialog;
    TextView downprogress, progress_persent;
    SeekBar downApkBar;

    private boolean isDown = false;
    private String mFilpath = null;

    /**
     * 文件下载
     */
    public void downFile(String downloadUrl, Context context) {
        if (downloadUrl == null || downloadUrl.equals("")) {
            return;
        }
        noticeDialog = new UniversalDialog.Builder(SearchActivity.this)
                .setLayoutView(R.layout.dialog_down_apk)
                .setVagueBackground(true)
                //.setDialogItemClickListener(new int[]{R.id.btn_ok, R.id.btn_cancel})
                .setCanceledOnTouchOutside(false)
                .create();

        noticeDialog.show();

        downprogress = noticeDialog.findViewById(R.id.downprogress);
        progress_persent = noticeDialog.findViewById(R.id.progress_persent);
        downApkBar = noticeDialog.findViewById(R.id.downapk_seekbar);

        noticeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (DownloadUtil.get() != null) {
                    DownloadUtil.get().release();
                }
                if (isDown) {
                    if (mFilpath != null && mFilpath.length() > 1) {
                        boolean isdelete = Utils.deleteAPKExists(mFilpath);
                        mFilpath = "";
                    }
                    isDown = false;
                }
            }
        });

        String path = FileHelper.getDownloadApkCachePath();
        String name = getString(R.string.download_apkname, "test");

        final String apkPath = path + name;
        mFilpath = apkPath;
        DownloadUtil.get().download(downloadUrl
                , path, name, new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(File file) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (noticeDialog != null) {
                                    noticeDialog.dismiss();
                                    noticeDialog = null;

                                    if(waitDialog == null){
                                        waitDialog =  new WaitDialog(SearchActivity.this);
                                    }
                                    waitDialog.show();
                                }
                            }
                        });
                        //下载完成进行相关逻辑操作
                        isDown = false;
                        mFilpath = "";
                        //需要调用格林的安装方式
                        Log.e("ApkINs", String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()));

                        LogUtil.LogShow("安装的apk:" + name + "__路径：" + path, LogUtil.DEBUG);

                        mGetLearnSdk.setApkInstall(SearchActivity.this, path, name);
                    }

                    @Override
                    public void onDownloading(int progress) {
                        isDown = true;
                        Log.e("wfc", "onDownloading:  --->" + progress);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                downApkBar.setProgress(progress);
                                downprogress.setText(progress + "");
                                progress_persent.setText(progress + "/100");
                            }
                        });
                    }

                    @Override
                    public void onDownloadFailed(Exception e) {
                        //下载异常进行相关提示操作
                        mFilpath = "";
                        isDown = false;
                        boolean isdelete = Utils.deleteAPKExists(apkPath);
                    }
                });
    }

    private WaitDialog waitDialog;

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(waitDialog != null){
            waitDialog.dismiss();
        }
    }
}
