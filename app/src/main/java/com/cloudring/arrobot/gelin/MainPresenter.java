package com.cloudring.arrobot.gelin;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.arellomobile.mvp.InjectViewState;
import com.cloudring.arrobot.gelin.base.BasePresenter;
import com.cloudring.arrobot.gelin.download.Check;
import com.cloudring.arrobot.gelin.download.NetworkUtil;
import com.cloudring.arrobot.gelin.manager.PRClient;
import com.cloudring.arrobot.gelin.mvp.modle.MainType;
import com.cloudring.arrobot.gelin.utils.ApiUtils;
import com.cloudring.arrobot.gelin.utils.CallBackUtil;
import com.cloudring.arrobot.gelin.utils.Constant;
import com.cloudring.arrobot.gelin.utils.ContantsUtil;
import com.cloudring.arrobot.gelin.utils.LogUtil;
import com.cloudring.arrobot.gelin.utils.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;

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

    private void getTypeIdList(String categoryId){
        SortedMap<String, Object> parameters = new TreeMap<>();
        Long timStamp = ApiUtils.generateTimestamp();
        parameters.put("appAk", ContantsUtil.geling_ak);
        parameters.put("apiName", "getCategoryList");
        parameters.put("timeStamp", timStamp.toString());
        parameters.put("categoryId", categoryId);
        parameters.put("recursion", "1");
        RequestBody body = new FormBody.Builder().add("categoryId", categoryId).add("appAk", ContantsUtil.geling_ak).add("apiName", "getCategoryList").add("timeStamp", timStamp.toString()).add("recursion", "1").add("sign", ApiUtils.generateSignature(parameters, ContantsUtil.geling_sk)).build();
        OkHttpUtil.doPost(Constant.GET_TYPE, body, new CallBackUtil.IRequestCallback(){
            @Override
            public void success(Object o){
                LogUtil.e("o = " + o.toString());
                try{
                    JSONObject root = new JSONObject(o.toString());
                    int code = root.getInt("code");
                    if(code == 0){
                        String result1 = ApiUtils.decryptData(root.getString("data"), ContantsUtil.geling_sk);
                        result1 = ApiUtils.decodeUnicode(result1);
                        LogUtil.e("result1 = " + result1);
                        JSONObject resultObj = new JSONObject(result1);
                        JSONArray list = resultObj.getJSONArray("list");
                        List<MainType> mainTypes = MainType.arrayMainTypeFromData(list.toString());
                        if(mainTypes != null && mainTypes.size() > 0){
                            int resCategoryId = mainTypes.get(0).getResCategoryId();
                            String upTime = mainTypes.get(0).getUpTime();
                            LogUtil.e("resCategoryId = " + resCategoryId);
                            getTypes(resCategoryId+"");
                        }
                    }else{
                        getViewState().loadFail();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String msg){
            }
        });
    }

    private void getTypes(String resCategoryId){
        SortedMap<String, Object> parameters = new TreeMap<>();
        Long timStamp = ApiUtils.generateTimestamp();
        parameters.put("appAk", ContantsUtil.geling_ak);
        parameters.put("apiName", "getCategoryList");
        parameters.put("timeStamp", timStamp.toString());
        parameters.put("categoryId", resCategoryId);
        parameters.put("recursion", "1");
        RequestBody body = new FormBody.Builder().add("categoryId", resCategoryId).add("appAk", ContantsUtil.geling_ak).add("apiName", "getCategoryList").add("timeStamp", timStamp.toString()).add("recursion", "1").add("sign", ApiUtils.generateSignature(parameters, ContantsUtil.geling_sk)).build();
        OkHttpUtil.doPost(Constant.GET_TYPE, body, new CallBackUtil.IRequestCallback(){
            @Override
            public void success(Object o){
                LogUtil.e("o = " + o.toString());
                try{
                    JSONObject root = new JSONObject(o.toString());
                    int code = root.getInt("code");
                    if(code == 0){
                        String result1 = ApiUtils.decryptData(root.getString("data"), ContantsUtil.geling_sk);
                        result1 = ApiUtils.decodeUnicode(result1);
                        LogUtil.e("result1 = " + result1);
                        JSONObject resultObj = new JSONObject(result1);
                        JSONArray list = resultObj.getJSONArray("list");
                        List<MainType> mainTypes = MainType.arrayMainTypeFromData(list.toString());
                        if(!Check.isEmpty(mainTypes)){
                            PRClient.getInstance().setTypeResultList(mainTypes);
                        }
                        getViewState().refreshList(mainTypes);
                    }else{
                        getViewState().loadFail();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String msg){
            }
        });
    }
        /*APIService apiService = NetworkClient.getInstance().getService(APIService.class);
        GetMainTypeRequest request = new GetMainTypeRequest(deviceId);
        apiService.getListType(request).enqueue(new Callback<GetMainTypeResponce>(){
            @Override
            public void onResponse(Call<GetMainTypeResponce> call, Response<GetMainTypeResponce> response){
                LogUtil.e("response = " +response.toString());
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
        });*/
}
