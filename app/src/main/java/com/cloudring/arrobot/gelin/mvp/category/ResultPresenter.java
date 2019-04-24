package com.cloudring.arrobot.gelin.mvp.category;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.cloudring.arrobot.gelin.MainApplication;
import com.cloudring.arrobot.gelin.base.BasePresenter;
import com.cloudring.arrobot.gelin.contentdb.AppInfo;
import com.cloudring.arrobot.gelin.contentdb.AppInfoDao;
import com.cloudring.arrobot.gelin.download.Check;
import com.cloudring.arrobot.gelin.download.NetworkClient;
import com.cloudring.arrobot.gelin.download.NetworkUtil;
import com.cloudring.arrobot.gelin.manager.PRClient;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.cloudring.arrobot.gelin.mvp.network.APIService;
import com.cloudring.arrobot.gelin.mvp.network.APIUtils;
import com.cloudring.arrobot.gelin.mvp.network.request.GetAppListByTypeRequest;
import com.cloudring.arrobot.gelin.mvp.network.response.GetListAppByTypeIdResponse;


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
}
