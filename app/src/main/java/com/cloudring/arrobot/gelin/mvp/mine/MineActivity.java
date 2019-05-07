package com.cloudring.arrobot.gelin.mvp.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.adapter.AppAdapter;
import com.cloudring.arrobot.gelin.adapter.ListAdapter;
import com.cloudring.arrobot.gelin.adapter.OnItemClickCallback;
import com.cloudring.arrobot.gelin.contentdb.AppInfo;
import com.cloudring.arrobot.gelin.contentdb.AppInfoDao;
import com.cloudring.arrobot.gelin.download.FileHelper;
import com.cloudring.arrobot.gelin.download.UniversalDialog;
import com.cloudring.arrobot.gelin.download.Utils;
import com.cloudring.arrobot.gelin.download.test.DownloadUtil;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.cloudring.arrobot.gelin.mvp.modle.MainType;
import com.cloudring.arrobot.gelin.utils.ContantsUtil;
import com.cloudring.arrobot.gelin.utils.GlobalUtil;
import com.cloudring.arrobot.gelin.utils.LogUtil;
import com.cloudring.arrobot.gelin.utils.MyUtil;
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

public class MineActivity extends MvpAppCompatActivity implements MineView {


    @BindView(R.id.id_normal_recycler_view)
    RecyclerView normalRecycler;
    @BindView(R.id.id_list_view)
    ListView itemList;
    @BindView(R.id.id_back_iv)
    ImageView backImg;
    @BindView(R.id.id_top_title_tv)
    TextView titleTv;


    private AppAdapter normalAdapter;
    private ListAdapter listAdapter;

    private List<AppItem> normalListData;
    private List<String> itemListData;

    private static String type = "";
    private static String categoryType = "";
    private AppInfo appInfo1 = new AppInfo();

    @InjectPresenter
    public MinePresenter mPresenter;

    private static final int REFRESH_DATA = 0x001;      //刷新数据
    private static final int START_DOWNLOAD = 0x002;      //启动下载
    private int currentPosition = 0;

    private GetLearnSdk mGetLearnSdk;

    private MyHandler myHandler = new MyHandler(this);
    static class MyHandler extends Handler {
        WeakReference<MineActivity> mActivityReference;

        MyHandler(MineActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MineActivity activity = mActivityReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case REFRESH_DATA:
                        activity.normalAdapter.setDataChanged(activity.normalListData);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ButterKnife.bind(this);
        initData();
        initEvent();
        initView();
        mGetLearnSdk = new GetLearnSdk();
        //安装apk 回调
        mGetLearnSdk.setOnApkInstallListener(new OnApkInstallListener(){
            @Override
            public void sdkSetApkInsta(String s, String packName){
                LogUtil.LogShow("安装结果是：" + s+"----包名="+packName, LogUtil.DEBUG);//0 成功, 1 失败
                if(s.equals("0")){
                    //安装成功，修改为我的游戏，显示在游戏界面，根据包名跳转
                    AppInfoDao.update(appInfo1.getId(), "1", packName);
                    if(currentPosition == 0){
                        mPresenter.getMineData(categoryType);
                    }else{
                        mPresenter.getMineCategoryData(categoryType, getCategoryId(currentPosition));
                    }
                }
                waitDialog.dismiss();
            }
        });

        //url回调
        mGetLearnSdk.setOnUrlListener(new OnUrlListener(){
            @Override
            public void sdkGetTempUrl(String s, String s1, String s2, String s3){
                LogUtil.LogShow("状态码：" + s + "，资源地址：" + s2, LogUtil.DEBUG);
                if(s.equals("0")){
                    Message message = Message.obtain();
                    message.what = START_DOWNLOAD;
                    message.obj = s2;
                    myHandler.sendMessage(message);
                }
            }
        });

    }

    private void initEvent(){
        normalAdapter.setOnItemClickListener(new AppAdapter.OnItemClickListener(){
            @Override
            public void onItemClickListener(int pos, List<AppItem> myLiveList){
                AppItem myLive = myLiveList.get(pos);
                boolean isSelect = myLive.isSelect();
                if (!isSelect) {
                    myLive.setSelect(true);
                } else {
                    myLive.setSelect(false);
                }
                normalAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        List<MainType> typeList = (List<MainType>) getIntent().getSerializableExtra(ContantsUtil.MAIN_TYPE_LIST);
        MainType mainType = new MainType();
        mainType.setCategroyName("全部");
        typeList.add(0,mainType);
        LogUtil.e("typeList.size = "+typeList.size());

        normalListData = new ArrayList<>();
        itemListData = new ArrayList<>();
        initItemData();
        normalAdapter = new AppAdapter(normalListData, new OnItemClickCallback<AppItem>() {
            @Override
            public void onClick(View view, AppItem info,int position) {
                if(type.equals(GlobalUtil.INTENT_TYPE_GAME)){//点击根据包名进入游戏
                    //获取已安装所有应用的包名
                    //   loadApps();
                    //在我的游戏界面
                    LogUtil.LogShow("包名=" + info.getPackageName(), LogUtil.ERROR);
                    if(MyUtil.isPkgInstalled(MineActivity.this, info.getPackageName())){
                        PackageManager packageManager = getPackageManager();
                        Intent intent = new Intent();
                        // 这里面的值是你要跳转app的包名，你跳转的清单文件里的package名
                        intent = packageManager.getLaunchIntentForPackage(info.getPackageName());
                        startActivity(intent);
                    }else{
                        Toast.makeText(MineActivity.this, "尚未安装此应用", Toast.LENGTH_SHORT).show();
                    }
                }else if(type.equals(GlobalUtil.INTENT_TYPE_COLLECTION)){//点击下载游戏
                    //在我的收藏界面
                    mGetLearnSdk.getResUrl(MineActivity.this, info.getId());
                    //标记对象
                    appInfo1.setId(info.getId());
                    appInfo1.setCategoryId(info.getCategoryId()+"");
                    appInfo1.setFileName(info.getFileName());
                    appInfo1.setTopCategoryId(info.getTopCategoryId());
                    appInfo1.setIcon1(info.getIcon1());
                    appInfo1.setType("1");//我的游戏
                }
            }
        }, new OnItemClickCallback<AppItem>() {

            @Override
            public void onClick(View view, AppItem info,int position) {
                Toast.makeText(MineActivity.this, "点击了收藏" + info.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        listAdapter = new ListAdapter(this, typeList, new OnItemClickCallback<MainType>() {
            @Override
            public void onClick(View view, MainType mainType1,int position) {
                if (currentPosition == position) return;
                currentPosition = position;
                if (position == 0){
                    mPresenter.getMineData(categoryType);
                }else {
                    mPresenter.getMineCategoryData(categoryType,getCategoryId(position));
                    LogUtil.e("currentPosition = "+currentPosition);
                }
            }
        });
        itemList.setAdapter(listAdapter);
        if (getIntent().hasExtra(GlobalUtil.INTENT_TYPE_KEY)){
            type = getIntent().getStringExtra(GlobalUtil.INTENT_TYPE_KEY);
            if (type.equals(GlobalUtil.INTENT_TYPE_GAME)){
                normalAdapter.setType(1);
                categoryType  = GlobalUtil.CATEGORY_TYPE_MY_GAME;
                titleTv.setText("我的游戏");
            }else if (type.equals(GlobalUtil.INTENT_TYPE_COLLECTION)){
                titleTv.setText("我的收藏");
                categoryType  = GlobalUtil.CATEGORY_TYPE_COLLECTION;
                normalAdapter.setType(2);
            }
        }
    }

    private void loadApps(){
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = getPackageManager().queryIntentActivities(intent, 0);
        //for循环遍历ResolveInfo对象获取包名和类名
        for(int i = 0; i < apps.size(); i++){
            ResolveInfo info = apps.get(i);
            String packageName = info.activityInfo.packageName;
            CharSequence cls = info.activityInfo.name;
            CharSequence name = info.activityInfo.loadLabel(getPackageManager());
            Log.e("！！！！！", name + "----" + packageName + "----" + cls);
        }
    }

    private void initItemData() {
       /* itemListData.add("全部");
        itemListData.add("记忆");
        itemListData.add("情商");
        itemListData.add("习惯");
        itemListData.add("语言");
        itemListData.add("数理");
        itemListData.add("认知");
        itemListData.add("逻辑");*/
    }

    private void initNormalData() {
        //测试
        normalListData.add(new AppItem("001", "www.baidu.com"));
        normalListData.add(new AppItem("002", "www.baidu.com"));
        normalListData.add(new AppItem("003", "www.baidu.com"));
        normalListData.add(new AppItem("004", "www.baidu.com"));
        normalListData.add(new AppItem("005", "www.baidu.com"));
        normalListData.add(new AppItem("006", "www.baidu.com"));
        normalListData.add(new AppItem("007", "www.baidu.com"));
        normalListData.add(new AppItem("008", "www.baidu.com"));
        normalListData.add(new AppItem("009", "www.baidu.com"));
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        normalRecycler.setLayoutManager(gridLayoutManager);
        normalRecycler.setAdapter(normalAdapter);

        mPresenter.getMineData(categoryType);
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
    public void refreshList(List<AppItem> list) {
        normalListData = list;
        myHandler.sendEmptyMessage(REFRESH_DATA);
    }

    @OnClick({R.id.id_back_iv,R.id.delete_tv})
    public void onClickEvent(View v) {
        switch (v.getId()){
            case R.id.id_back_iv:
                finish();
                break;
            case R.id.delete_tv:
                for(int i = 0; i<normalListData.size();i++){
                    boolean isSelect = normalListData.get(i).isSelect;
                    if(isSelect){//从数据库中删除
                        String id = normalListData.get(i).getId();
                        AppInfoDao.delete(id);
                    }
                }
                if (currentPosition == 0){
                    mPresenter.getMineData(categoryType);
                }else {
                    mPresenter.getMineCategoryData(categoryType,getCategoryId(currentPosition));
                }
                break;
            default:
                break;
        }
    }

    private String getCategoryId(int request){
        if (request == 1){
            return "54";
        }else if (request == 2){
            return "51";
        }else if (request == 3){
            return "52";
        }else if (request == 4){
            return "66";
        }else if (request == 5){
            return "67";
        }else if (request == 6){
            return "68";
        }else if (request == 7){
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
     */
    public void downFile(String downloadUrl, Context context){
        if(downloadUrl == null || downloadUrl.equals("")){
            return;
        }
        noticeDialog = new UniversalDialog.Builder(MineActivity.this).setLayoutView(R.layout.dialog_down_apk).setVagueBackground(true)
                //.setDialogItemClickListener(new int[]{R.id.btn_ok, R.id.btn_cancel})
                .setCanceledOnTouchOutside(false).create();
        noticeDialog.show();
        downprogress = noticeDialog.findViewById(R.id.downprogress);
        progress_persent = noticeDialog.findViewById(R.id.progress_persent);
        downApkBar = noticeDialog.findViewById(R.id.downapk_seekbar);
        noticeDialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialog){
                if(DownloadUtil.get() != null){
                    DownloadUtil.get().release();
                }
                if(isDown){
                    if(mFilpath != null && mFilpath.length() > 1){
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
        DownloadUtil.get().download(downloadUrl, path, name, new DownloadUtil.OnDownloadListener(){
            @Override
            public void onDownloadSuccess(File file){
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        if(noticeDialog != null){
                            noticeDialog.dismiss();
                            noticeDialog = null;
                            if(waitDialog == null){
                                waitDialog = new WaitDialog(MineActivity.this);
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
                mGetLearnSdk.setApkInstall(MineActivity.this, path, name);
            }

            @Override
            public void onDownloading(int progress){
                isDown = true;
                Log.e("wfc", "onDownloading:  --->" + progress);
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        downApkBar.setProgress(progress);
                        downprogress.setText(progress + "");
                        progress_persent.setText(progress + "/100");
                    }
                });
            }

            @Override
            public void onDownloadFailed(Exception e){
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
