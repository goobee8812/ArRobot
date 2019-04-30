package com.cloudring.arrobot.gelin;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.cloudring.arrobot.gelin.base.BasePresenter;
import com.cloudring.arrobot.gelin.download.Check;
import com.cloudring.arrobot.gelin.download.NetworkClient;
import com.cloudring.arrobot.gelin.download.NetworkUtil;
import com.cloudring.arrobot.gelin.manager.PRClient;
import com.cloudring.arrobot.gelin.mvp.network.APIService;
import com.cloudring.arrobot.gelin.mvp.network.request.GetMainTypeRequest;
import com.cloudring.arrobot.gelin.mvp.network.response.GetMainTypeResponce;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView>{
    private String TAG = "TestResult";

    // 获取主界面游戏分类
    @TargetApi(Build.VERSION_CODES.M)
    public void getTypeList(String deviceId, Context context){
        if(NetworkUtil.isNetworkConnected(MainApplication.getInstance())){
            getTypeIdList(deviceId);
        }else{
            getViewState().showMsg("无网络···");
        }
    }

    private void getTypeIdList(String deviceId){
        APIService apiService = NetworkClient.getInstance().getService(APIService.class);
        GetMainTypeRequest request = new GetMainTypeRequest(deviceId);
        apiService.getListType(request).enqueue(new Callback<GetMainTypeResponce>(){
            @Override
            public void onResponse(Call<GetMainTypeResponce> call, Response<GetMainTypeResponce> response){
                if(response.isSuccessful() && response.body().isResult()){
                    Log.d(TAG, "onResponse: 成功" + response.body().data.categoryList.size());
                    if(!Check.isEmpty(response.body().data.categoryList)){
                        PRClient.getInstance().setTypeResultList(response.body().data.categoryList);
                    }
                    getViewState().refreshList(response.body().data.categoryList);
                }else{
                    getViewState().loadFail();
                    if(response.body().message != null)
                        getViewState().showMsg(response.body().message);
                }
            }

            @Override
            public void onFailure(Call<GetMainTypeResponce> call, Throwable t){
                getViewState().loadFail();
            }
        });
    }
}