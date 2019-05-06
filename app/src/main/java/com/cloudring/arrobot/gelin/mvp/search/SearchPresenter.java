package com.cloudring.arrobot.gelin.mvp.search;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.arellomobile.mvp.InjectViewState;
import com.cloudring.arrobot.gelin.MainApplication;
import com.cloudring.arrobot.gelin.base.BasePresenter;
import com.cloudring.arrobot.gelin.download.NetworkUtil;

@InjectViewState
public class SearchPresenter extends BasePresenter<SearchView>{
    private String TAG = "TestResult";
    public static final String geling_ak="vcat8yoczbgjkiofrnp5yt4xpx2ojcv0b1xtpkc3znkkm4ctgelej8p5mo5oifsy";
    public static final String geling_sk="k49UIqY1GHGaKv1s5WMpkhHPCeynlYJv1czyqcfNGCD1c3xgxfyP2lROEHiuKxXc";
    public static final String geling_url="http://test-rscloud.getlearn.cn/api/index/index";

    // 获取搜索内容
    @TargetApi(Build.VERSION_CODES.M)
    public void getSearchList(String strKey, Context context){
        if(NetworkUtil.isNetworkConnected(MainApplication.getInstance())){
            getSearchList(strKey);
        }else{
            getViewState().showMsg("无网络···");
        }
    }

    private void getSearchList(String strKey){



        /*//接口调用前准备
        SortedMap<String, Object> parameters = new TreeMap<>();
        Long timStamp = ApiUtils.generateTimestamp();
        parameters.put("appAk", geling_ak);
        parameters.put("apiName", "getResList");
        parameters.put("timeStamp", timStamp);
        parameters.put("keyword", strKey);
        parameters.put("recursion", 1);

        //接口调用
        HttpResponse response = ApiUtils.create(geling_url, parameters, geling_sk);
        String result = response.body();
        LogUtil.LogShow("result="+result, LogUtil.ERROR);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String result1 = ApiUtils.decryptData(jsonObject.getString("data"), geling_sk);
        result1 = ApiUtils.decodeUnicode(result1);

        JSONObject jsonObject1 = JSONObject.parseObject(result1);
        JSONArray list = jsonObject1.getJSONArray("list");*/

    }
}
