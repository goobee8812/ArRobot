package com.cloudring.arrobot.gelin.mvp.category;

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
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.cloudring.arrobot.gelin.MainActivity;
import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.adapter.AppAdapter;
import com.cloudring.arrobot.gelin.adapter.OnItemClickCallback;
import com.cloudring.arrobot.gelin.download.FileHelper;
import com.cloudring.arrobot.gelin.download.UniversalDialog;
import com.cloudring.arrobot.gelin.download.Utils;
import com.cloudring.arrobot.gelin.download.test.DownloadUtil;
import com.cloudring.arrobot.gelin.manager.PRClient;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.cloudring.arrobot.gelin.utils.GlobalUtil;
import com.cloudring.arrobot.gelin.utils.LogUtil;
import com.getlearn.library.GetLearnSdk;
import com.getlearn.library.Interface.OnApkInstallListener;
import com.getlearn.library.Interface.OnUrlListener;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultActivity extends MvpAppCompatActivity implements ResultView{


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

    @InjectPresenter
    public ResultPresenter mPresenter;

    private static final int REFRESH_DATA = 0x001;      //刷新数据

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
                switch (msg.what){
                    case REFRESH_DATA:
                        activity.normalAdapter.setDataChanged(activity.normalList);
                        activity.hotAdapter.notifyDataSetChanged();
                        Toast.makeText(activity, "更新数据" + activity.normalList.get(0).getFileName(), Toast.LENGTH_SHORT).show();
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
        mGetLearnSdk.setOnApkInstallListener(new OnApkInstallListener() {
            @Override
            public void sdkSetApkInsta(String s) {
                LogUtil.LogShow("安装结果是：" + s,LogUtil.DEBUG);
            }
        });
        //url回调
        mGetLearnSdk.setOnUrlListener(new OnUrlListener() {
            @Override
            public void sdkGetTempUrl(String s, String s1, String s2, String s3) {
                LogUtil.LogShow("状态码："+ s + "，资源地址：" + s2,LogUtil.DEBUG);
//                downFile(s2);
            }
        });
    }

    private void initData() {
        titleMap = new HashMap<>();
        titleMap.put(GlobalUtil.INTENT_TYPE_JIYI,"记忆");
        titleMap.put(GlobalUtil.INTENT_TYPE_QINGSHANG,"情商");
        titleMap.put(GlobalUtil.INTENT_TYPE_HOBBY,"习惯");
        titleMap.put(GlobalUtil.INTENT_TYPE_LANGUAGE,"语言");
        titleMap.put(GlobalUtil.INTENT_TYPE_MATH,"数理");
        titleMap.put(GlobalUtil.INTENT_TYPE_COGNITION,"认知");
        titleMap.put(GlobalUtil.INTENT_TYPE_LOGIC,"逻辑");

        if (getIntent().hasExtra(GlobalUtil.INTENT_TYPE_KEY)){
            type = getIntent().getStringExtra(GlobalUtil.INTENT_TYPE_KEY);
            if (titleMap.get(type) != null){
                titleTv.setText(titleMap.get(type));
            }else {
                titleTv.setText("");
            }
        }
        normalList = new ArrayList<>();
        hotList = new ArrayList<>();
        normalAdapter = new AppAdapter(normalList, new OnItemClickCallback<AppItem>() {
            @Override
            public void onClick(View view, AppItem info) {
                Toast.makeText(ResultActivity.this, "点击了下载" + info.getId(), Toast.LENGTH_SHORT).show();
                mGetLearnSdk.getResUrl(ResultActivity.this,info.getId());
            }
        }, new OnItemClickCallback<AppItem>() {
            @Override
            public void onClick(View view, AppItem info) {
                Toast.makeText(ResultActivity.this, "点击了收藏" + info.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        hotAdapter = new AppAdapter(hotList, new OnItemClickCallback<AppItem>() {
            @Override
            public void onClick(View view, AppItem info) {
                Toast.makeText(ResultActivity.this, "点击了下载" + info.getId(), Toast.LENGTH_SHORT).show();
            }
        }, new OnItemClickCallback<AppItem>() {

            @Override
            public void onClick(View view, AppItem info) {
                Toast.makeText(ResultActivity.this, "点击了收藏" + info.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        mPresenter.getNormalList(getRequestType(type),this);
    }

    private void initHotData() {
        hotList.add(new AppItem("001","www.ww.ww.www"));
        hotList.add(new AppItem("002","www.ww.ww.www"));
        hotList.add(new AppItem("003","www.ww.ww.www"));
        hotList.add(new AppItem("004","www.ww.ww.www"));
        hotList.add(new AppItem("005","www.ww.ww.www"));
    }

    private void initNormalData() {
        //测试
        normalList.add(new AppItem("001","www.baidu.com"));
        normalList.add(new AppItem("002","www.baidu.com"));
        normalList.add(new AppItem("003","www.baidu.com"));
        normalList.add(new AppItem("004","www.baidu.com"));
        normalList.add(new AppItem("005","www.baidu.com"));
        normalList.add(new AppItem("006","www.baidu.com"));
        normalList.add(new AppItem("007","www.baidu.com"));
        normalList.add(new AppItem("008","www.baidu.com"));
        normalList.add(new AppItem("009","www.baidu.com"));
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
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
        initHotData();
        myHandler.sendEmptyMessage(REFRESH_DATA);
    }

    @Override
    public void refreshList(List<AppItem> list) {
        normalList = list;
        initHotData();
        myHandler.sendEmptyMessage(REFRESH_DATA);
    }

    @OnClick({R.id.id_back_iv})
    public void onClickEvent(View v) {
        switch (v.getId()){
            case R.id.id_back_iv:
                finish();
                break;
            default:
                break;
        }
    }

    private Map<String,String> titleMap;

    private String getRequestType(String request){
        if (request.equals(GlobalUtil.INTENT_TYPE_JIYI)){
            return "54";
        }else if (request.equals(GlobalUtil.INTENT_TYPE_QINGSHANG)){
            return "51";
        }else if (request.equals(GlobalUtil.INTENT_TYPE_HOBBY)){
            return "52";
        }else if (request.equals(GlobalUtil.INTENT_TYPE_LANGUAGE)){
            return "66";
        }else if (request.equals(GlobalUtil.INTENT_TYPE_MATH)){
            return "67";
        }else if (request.equals(GlobalUtil.INTENT_TYPE_COGNITION)){
            return "68";
        }else if (request.equals(GlobalUtil.INTENT_TYPE_LOGIC)){
            return "69";
        }else {
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
     *
     */
    public void downFile(String downloadUrl) {
        if (downloadUrl == null || downloadUrl.equals("")) {
            return;
        }

        noticeDialog = new UniversalDialog.Builder(ResultActivity.this)
                .setLayoutView(R.layout.dialog_down_apk)
                .setVagueBackground(true)
                //.setDialogItemClickListener(new int[]{R.id.btn_ok, R.id.btn_cancel})
                .setCanceledOnTouchOutside(true)
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

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (noticeDialog != null) {
                                            noticeDialog.dismiss();
                                            noticeDialog = null;
                                        }
                                    }
                                });

                            }
                        });
                        //下载完成进行相关逻辑操作
                        isDown = false;
                        mFilpath = "";
                        //需要调用格林的安装方式
                        mGetLearnSdk.setApkInstall(ResultActivity.this,Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download","996.apk");
//                        install(file);
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
}