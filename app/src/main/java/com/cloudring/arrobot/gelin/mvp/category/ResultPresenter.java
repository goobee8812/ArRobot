package com.cloudring.arrobot.gelin.mvp.category;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.arellomobile.mvp.InjectViewState;
import com.cloudring.arrobot.gelin.MainApplication;
import com.cloudring.arrobot.gelin.R;
import com.cloudring.arrobot.gelin.base.BasePresenter;
import com.cloudring.arrobot.gelin.contentdb.AppInfo;
import com.cloudring.arrobot.gelin.contentdb.AppInfoDao;
import com.cloudring.arrobot.gelin.download.Check;
import com.cloudring.arrobot.gelin.download.FileHelper;
import com.cloudring.arrobot.gelin.download.NetworkClient;
import com.cloudring.arrobot.gelin.download.NetworkUtil;
import com.cloudring.arrobot.gelin.download.UniversalDialog;
import com.cloudring.arrobot.gelin.download.Utils;
import com.cloudring.arrobot.gelin.download.test.DownloadUtil;
import com.cloudring.arrobot.gelin.manager.PRClient;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.cloudring.arrobot.gelin.mvp.network.APIService;
import com.cloudring.arrobot.gelin.mvp.network.APIUtils;
import com.cloudring.arrobot.gelin.mvp.network.request.GetAppListByTypeRequest;
import com.cloudring.arrobot.gelin.mvp.network.response.GetListAppByTypeIdResponse;
import com.cloudring.arrobot.gelin.utils.LogUtil;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@InjectViewState
public class ResultPresenter extends BasePresenter<ResultView> {
    private String TAG = "TestResult";
    // 获取阅读记录
    @TargetApi(Build.VERSION_CODES.M)
    public void getNormalList(String categoryId, Context context) {
        if (NetworkUtil.isNetworkConnected(MainApplication.getInstance())) {
//            Map<String, Object> map = APIUtils.toMap(context);
            getNormalList(categoryId);
        } else {
            getViewState().showMsg("无网络···");
        }
    }

    public void getNormalList(String categoryId){
        APIService apiService = NetworkClient.getInstance().getService(APIService.class);
        GetAppListByTypeRequest listAppRequest = new GetAppListByTypeRequest(categoryId);
//        getViewState().refreshList("","","");
        apiService.getListApp(listAppRequest)
                .enqueue(new Callback<GetListAppByTypeIdResponse>() {
            @Override
            public void onResponse(Call<GetListAppByTypeIdResponse> call, Response<GetListAppByTypeIdResponse> response) {
                if(response.isSuccessful() && response.body().isResult()){
                    Log.d(TAG, "onResponse: 成功" + response.body().data.apkList.size());
                    if(!Check.isEmpty(response.body().data.apkList))
                    {
                        PRClient.getInstance().setApkResultList(response.body().data.apkList);
                    }
                    //过滤已在数据库保存的数据
                    List<AppItem> list = new ArrayList<>();
                    if (response.body().data.apkList.size() > 0){
                        for (AppItem item : response.body().data.apkList){
                            if (AppInfoDao.getByFileName(item.getFileName()) == null){
                                list.add(item);
                            }
                        }
                    }
                    getViewState().refreshList(list);
                }else {
                    getViewState().loadFail();
                    if(response.body().message!=null)
                        getViewState().showMsg(response.body().message);
                }
            }

            @Override
            public void onFailure(Call<GetListAppByTypeIdResponse> call, Throwable t) {
                getViewState().loadFail();
            }
        });
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
        noticeDialog = new UniversalDialog.Builder(context)
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
        String name = context.getString(R.string.download_apkname, "test");

        final String apkPath = path + name;
        mFilpath = apkPath;
        DownloadUtil.get().download(downloadUrl
                , path, name, new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(File file) {
                        if (noticeDialog != null) {
                            noticeDialog.dismiss();
                            noticeDialog = null;
                        }
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (noticeDialog != null) {
//                                    noticeDialog.dismiss();
//                                    noticeDialog = null;
//                                }
//                            }
//                        });
                        //下载完成进行相关逻辑操作
                        isDown = false;
                        mFilpath = "";
                        //需要调用格林的安装方式
                        Log.e("ApkINs", String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()));

                        LogUtil.LogShow("安装的apk:" + name + "__路径：" + path, LogUtil.DEBUG);
//                        getViewState().mGetLearnSdk.setApkInstall(context, path, name);

                    }

                    @Override
                    public void onDownloading(int progress) {
                        isDown = true;
                        Log.e("wfc", "onDownloading:  --->" + progress);

                        downApkBar.setProgress(progress);
                        downprogress.setText(progress + "");
                        progress_persent.setText(progress + "/100");

//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                downApkBar.setProgress(ic_loading);
//                                downprogress.setText(ic_loading + "");
//                                progress_persent.setText(ic_loading + "/100");
//                            }
//                        });
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
