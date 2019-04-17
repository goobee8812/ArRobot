package com.cloudring.arrobot.gelin.mvp;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.arellomobile.mvp.InjectViewState;
import com.cloudring.arrobot.gelin.MainApplication;
import com.cloudring.arrobot.gelin.base.BasePresenter;
import com.cloudring.arrobot.gelin.download.Check;
import com.cloudring.arrobot.gelin.download.NetworkClient;
import com.cloudring.arrobot.gelin.download.NetworkUtil;
import com.cloudring.arrobot.gelin.mvp.network.APIService;
import com.cloudring.arrobot.gelin.mvp.network.APIUtils;
import com.cloudring.arrobot.gelin.mvp.network.request.GetAppListByTypeRequest;


import java.util.Map;


@InjectViewState
public class ResultPresenter extends BasePresenter<ResultView> {

    // 获取阅读记录
    @TargetApi(Build.VERSION_CODES.M)
    public void getNormalList(String userid, String deviceid, Context context) {
        if (NetworkUtil.isNetworkConnected(MainApplication.getInstance())) {
            Map<String, Object> map = APIUtils.toMap(context);
            getNormalList(map, userid,deviceid,context);
        } else {
            getViewState().showMsg("无网络···");
        }
    }

    public void getNormalList(Map<String, Object> map, String userid, String deviceId, Context context){
        APIService apiService = NetworkClient.getInstance().getService(APIService.class);
        GetAppListByTypeRequest lookUpBookReadCountRequest = new GetAppListByTypeRequest(
                deviceId
                ,"");
//        apiService.getListApp(map,lookUpBookReadCountRequest).enqueue(new Callback<LookUpBookCountResponse>() {
//            @Override
//            public void onResponse(Call<LookUpBookCountResponse> call, Response<LookUpBookCountResponse> response) {
//                if(response.isSuccessful() && response.body().isResult()){
//
//                    if(!Check.isEmpty(response.body().data.appBookVOList))
//                    {
//                        PRClien.getInstance().setmBookHistoryList(response.body().data.appBookVOList);
//                    }
//
//                    if(!Check.isEmpty(response.body().data.appBookReadRecordList))
//                    {
//                        PRClien.getInstance().setmBookRecordList(response.body().data.appBookReadRecordList);
//
//                    }
//                    getViewState().refreshList(response.body().data.booksCount,response.body().data.acount,response.body().data.timesLength);
//                }else {
//                    getViewState().loadFail();
//                    if(response.body().message!=null)
//                        getViewState().showMsg(response.body().message);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LookUpBookCountResponse> call, Throwable t) {
//                getViewState().loadFail();
//            }
//        });
    }
}
