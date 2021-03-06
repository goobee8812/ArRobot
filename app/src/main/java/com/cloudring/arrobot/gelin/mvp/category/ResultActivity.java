package com.cloudring.arrobot.gelin.mvp.category;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.adapter.AppAdapter;
import com.cloudring.arrobot.gelin.adapter.OnItemClickCallback;
import com.cloudring.arrobot.gelin.contentdb.AppInfo;
import com.cloudring.arrobot.gelin.contentdb.AppInfoDao;
import com.cloudring.arrobot.gelin.download.FileHelper;
import com.cloudring.arrobot.gelin.download.UniversalDialog;
import com.cloudring.arrobot.gelin.download.Utils;
import com.cloudring.arrobot.gelin.download.test.DownloadUtil;
import com.cloudring.arrobot.gelin.manager.PRClient;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.cloudring.arrobot.gelin.mvp.modle.MainType;
import com.cloudring.arrobot.gelin.utils.ContantsUtil;
import com.cloudring.arrobot.gelin.utils.GlobalUtil;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultActivity extends MvpAppCompatActivity implements ResultView {


    @BindView(R.id.id_normal_recycler_view)
    RecyclerView normalRecycler;
    @BindView(R.id.id_hot_recycler_view)
    RecyclerView hotRecycler;
    @BindView(R.id.id_back_iv)
    ImageView backImg;
    @BindView(R.id.id_top_title_tv)
    TextView titleTv;

    private static String type = "";

    private AppAdapter normalAdapter;
    private AppAdapter hotAdapter;

    private List<AppItem> normalList;
    private List<AppItem> hotList;

    private GetLearnSdk mGetLearnSdk;

    private AppInfo appInfo1;

    @InjectPresenter
    public ResultPresenter mPresenter;

    private String test = "http://image.czbsit.com/static/Beesmart_1.0.12_2019-03-20.apk";

    private static final int REFRESH_DATA = 0x001;      //刷新数据
    private static final int START_DOWNLOAD = 0x002;      //启动下载
    private static final int REFRESH_ITEM = 0x003;      //个别刷新

    private MainType mainType;

    private MyHandler myHandler = new MyHandler(this);
    static class MyHandler extends Handler {
        WeakReference<ResultActivity> mActivityReference;

        MyHandler(ResultActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final ResultActivity activity = mActivityReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case REFRESH_DATA:
                        LogUtil.LogShow("normalList.sizeREFRESH_DATA = " + activity.normalList.size(), LogUtil.ERROR);
                        activity.normalAdapter.setDataChanged(activity.normalList);
                        activity.hotAdapter.notifyDataSetChanged();
                        //    Toast.makeText(activity, "更新数据" + activity.normalList.get(0).getFileName(), Toast.LENGTH_SHORT).show();
                        break;
                    case START_DOWNLOAD:
                        String url = (String) msg.obj;
                        activity.downFile(url, activity);
                        break;
                    case REFRESH_ITEM:
                        break;
                    default:
                        break;
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resutl);
        ButterKnife.bind(this);
        mGetLearnSdk = new GetLearnSdk();
        initData();
        initView();
        //安装apk 回调
        mGetLearnSdk.setOnApkInstallListener(new OnApkInstallListener(){
            @Override
            public void sdkSetApkInsta(String s, String packName){
                LogUtil.LogShow("安装结果是：" + s+"----包名="+packName, LogUtil.DEBUG);//0 成功, 1 失败
                if (s.equals("0")) {
                    //安装成功，写入数据库，显示在游戏界面，根据包名跳转
                    appInfo1.setPackageName(packName);
                    LogUtil.e("ApkInsta appInfo1 = "+appInfo1.toString());
                    AppInfoDao.add(appInfo1);
                    List<AppItem> listTiem = new ArrayList<>();
                    if (normalList.size() > 0){
                        for (AppItem item : normalList){
                            AppInfo byFileName = AppInfoDao.getByFileName(item.getFileName());
                            LogUtil.e("ApkInsta byFileName = "+byFileName);
                            if (AppInfoDao.getByFileName(item.getFileName()) == null){
                                listTiem.add(item);
                            }
                        }
                    }
                    refreshList(listTiem);

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            ToastUtils.showToast(ResultActivity.this, "安装成功，可到我的游戏中打开游戏");
                        }
                    });
                }else {
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            ToastUtils.showToast(ResultActivity.this, "安装或更新失败，请先卸载后安装");
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

    private void initData() {
        /*titleMap = new HashMap<>();
        titleMap.put(GlobalUtil.INTENT_TYPE_JIYI, "记忆");
        titleMap.put(GlobalUtil.INTENT_TYPE_QINGSHANG, "情商");
        titleMap.put(GlobalUtil.INTENT_TYPE_HOBBY, "习惯");
        titleMap.put(GlobalUtil.INTENT_TYPE_LANGUAGE, "语言");
        titleMap.put(GlobalUtil.INTENT_TYPE_MATH, "数理");
        titleMap.put(GlobalUtil.INTENT_TYPE_COGNITION, "认知");
        titleMap.put(GlobalUtil.INTENT_TYPE_LOGIC, "逻辑");

        if (getIntent().hasExtra(GlobalUtil.INTENT_TYPE_KEY)) {
            type = getIntent().getStringExtra(GlobalUtil.INTENT_TYPE_KEY);
            if (titleMap.get(type) != null) {
                titleTv.setText(titleMap.get(type));
            } else {
                titleTv.setText("");
            }
        }*/
        //主界面分类对象
        mainType = (MainType) getIntent().getSerializableExtra(ContantsUtil.MAIN_TYPE);
        String categroyName = mainType.getCategName();
        titleTv.setText(categroyName);

        hotList = new ArrayList<>();
        normalList = new ArrayList<>();
        normalAdapter = new AppAdapter(normalList, new OnItemClickCallback<AppItem>() {
            @Override
            public void onClick(View view, AppItem info, int position) {
                if(MyUtil.isFastClick()){
                    return;
                }
           //     Toast.makeText(ResultActivity.this, "点击了下载" + info.getId(), Toast.LENGTH_SHORT).show();
                mGetLearnSdk.getResUrl(ResultActivity.this, info.getId());
                //标记对象
                appInfo1 = new AppInfo();
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
                LogUtil.e("点击CategoryId = "+appInfo.getCategoryId());

                AppInfoDao.add(appInfo);
                //删掉数据
                normalList.remove(position);
                refreshList(normalList);
//                normalAdapter.notifyItemChanged(position);
               /* normalAdapter.notifyItemRemoved(position);
                if (position != normalList.size()) {
                    normalAdapter.notifyItemRangeChanged(position, normalList.size() - position);
                }*/

            }
        });
        hotAdapter = new AppAdapter(hotList, new OnItemClickCallback<AppItem>() {
            @Override
            public void onClick(View view, AppItem info, int position) {
                if(MyUtil.isFastClick()){
                    return;
                }
             //   Toast.makeText(ResultActivity.this, "点击了下载" + info.getId(), Toast.LENGTH_SHORT).show();
                mGetLearnSdk.getResUrl(ResultActivity.this, info.getId());
                //标记对象
                appInfo1 = new AppInfo();
                appInfo1.setId(info.getId());
                appInfo1.setCategoryId(info.getCategoryId()+"");
                appInfo1.setFileName(info.getFileName());
                appInfo1.setTopCategoryId(info.getTopCategoryId());
                appInfo1.setIcon1(info.getIcon1());
                appInfo1.setType("1");

                LogUtil.e("hotAdapter appInfo1 = "+appInfo1.toString());
            }
        }, new OnItemClickCallback<AppItem>() {

            @Override
            public void onClick(View view, AppItem info, int position) {
                if(MyUtil.isFastClick()){
                    return;
                }
             //   Toast.makeText(ResultActivity.this, "点击了收藏" + info.getId(), Toast.LENGTH_SHORT).show();
                AppInfo appInfo = new AppInfo();
                appInfo.setId(info.getId());
                appInfo.setCategoryId(info.getCategoryId()+"");
                appInfo.setFileName(info.getFileName());
                appInfo.setTopCategoryId(info.getTopCategoryId());
                appInfo.setIcon1(info.getIcon1());
                appInfo.setType("2");
                AppInfoDao.add(appInfo);
                //删掉数据
                hotList.remove(position);
                //                normalAdapter.notifyItemChanged(position);
                //热门游戏更新数据
                hotAdapter.notifyItemRemoved(position);
                if(position != hotList.size()){
                    hotAdapter.notifyItemRangeChanged(position, hotList.size() - position);
                }
                //分类列表更新数据,过滤掉已收藏的数据
                LogUtil.LogShow("normalList.size = " + normalList.size(), LogUtil.ERROR);
                for(int i = 0; i < normalList.size(); i++){
                    if(info.getId().equals(normalList.get(i).getId())){
                        normalList.remove(i);
                        break;
                    }
                }
                LogUtil.LogShow("normalList.size = " + normalList.size(), LogUtil.ERROR);
                refreshList(normalList);
            }
        });
        mPresenter.getNormalList(mainType.getId()+"", this);
    }

    private void initHotData(List<AppItem> normalList){
        if(hotList != null){
            hotList.clear();
        }
        for(int i = 0; i < normalList.size(); i++){
            int recommended = normalList.get(i).getRecommended();
            if(recommended > 0){
                hotList.add(normalList.get(i));
            }
        }
        if(hotList.size() == 0 && normalList.size() >= 1){
            hotList.add(normalList.get(0));
        }
    }

    private void initNormalData() {
        //测试
        normalList.add(new AppItem("001", "www.baidu.com"));
        normalList.add(new AppItem("002", "www.baidu.com"));
        normalList.add(new AppItem("003", "www.baidu.com"));
        normalList.add(new AppItem("004", "www.baidu.com"));
        normalList.add(new AppItem("005", "www.baidu.com"));
        normalList.add(new AppItem("006", "www.baidu.com"));
        normalList.add(new AppItem("007", "www.baidu.com"));
        normalList.add(new AppItem("008", "www.baidu.com"));
        normalList.add(new AppItem("009", "www.baidu.com"));
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        normalRecycler.setLayoutManager(gridLayoutManager);
        normalRecycler.setAdapter(normalAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hotRecycler.setLayoutManager(linearLayoutManager);
        hotRecycler.setAdapter(hotAdapter);
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showMsg(int msg) {
    }

    @Override
    public void loadFail() {

    }

    @Override
    public void refreshList(String bookCount, String acount, String time) {
//        initNormalData();
        Log.d("Test", "refreshList: " + normalList.size());
        normalList = PRClient.getInstance().getApkResultList();
        Log.d("Test", "refreshList: " + normalList.get(0).getFileName());
//        initNormalData();
        Log.d("Test", "refreshList: " + normalList.size());
        initHotData(normalList);
        myHandler.sendEmptyMessage(REFRESH_DATA);
    }

    @Override
    public void refreshList(List<AppItem> list) {
        normalList = list;
        initHotData(normalList);
        myHandler.sendEmptyMessage(REFRESH_DATA);
    }

    @OnClick({R.id.id_back_iv})
    public void onClickEvent(View v) {
        switch (v.getId()) {
            case R.id.id_back_iv:
                finish();
                break;
            default:
                break;
        }
    }

    private Map<String, String> titleMap;

    private String getRequestType(String request) {
        if (request.equals(GlobalUtil.INTENT_TYPE_JIYI)) {
            return "54";
        } else if (request.equals(GlobalUtil.INTENT_TYPE_QINGSHANG)) {
            return "51";
        } else if (request.equals(GlobalUtil.INTENT_TYPE_HOBBY)) {
            return "52";
        } else if (request.equals(GlobalUtil.INTENT_TYPE_LANGUAGE)) {
            return "66";
        } else if (request.equals(GlobalUtil.INTENT_TYPE_MATH)) {
            return "67";
        } else if (request.equals(GlobalUtil.INTENT_TYPE_COGNITION)) {
            return "68";
        } else if (request.equals(GlobalUtil.INTENT_TYPE_LOGIC)) {
            return "69";
        } else {
            return "";
        }
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
        noticeDialog = new UniversalDialog.Builder(ResultActivity.this)
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
                        LogUtil.e("isdelete = "+isdelete);
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
                                        waitDialog =  new WaitDialog(ResultActivity.this);
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

                        mGetLearnSdk.setApkInstall(ResultActivity.this, path, name);
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
