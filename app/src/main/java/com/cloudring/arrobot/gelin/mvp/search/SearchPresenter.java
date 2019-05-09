package com.cloudring.arrobot.gelin.mvp.search;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.cloudring.arrobot.gelin.MainApplication;
import com.cloudring.arrobot.gelin.base.BasePresenter;
import com.cloudring.arrobot.gelin.contentdb.AppInfoDao;
import com.cloudring.arrobot.gelin.download.NetworkUtil;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.cloudring.arrobot.gelin.utils.ApiUtils;
import com.cloudring.arrobot.gelin.utils.CallBackUtil;
import com.cloudring.arrobot.gelin.utils.Constant;
import com.cloudring.arrobot.gelin.utils.ContantsUtil;
import com.cloudring.arrobot.gelin.utils.LogUtil;
import com.cloudring.arrobot.gelin.utils.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;

@InjectViewState
public class SearchPresenter extends BasePresenter<SearchView>{
    private String TAG = "TestResult";

    // 获取搜索内容
    @TargetApi(Build.VERSION_CODES.M)
    public void getSearchList(String strKey, Context context){
        if(NetworkUtil.isNetworkConnected(MainApplication.getInstance())){
            if(!TextUtils.isEmpty(strKey)){
                getSearchList(strKey);
            }else {
                getSearchList();
            }
        }else{
            getViewState().showMsg("无网络···");
        }
    }

    private void getSearchList(){
        SortedMap<String, Object> parameters = new TreeMap<>();
        Long timStamp = ApiUtils.generateTimestamp();
        parameters.put("appAk", ContantsUtil.geling_ak);
        parameters.put("apiName", "getResList");
        parameters.put("timeStamp", timStamp.toString());
        parameters.put("pageSize","100");
    //    parameters.put("keyword", strKey);
        parameters.put("recursion", "1");

        RequestBody body = new FormBody.Builder()
            //    .add("keyword", strKey)
                .add("appAk", ContantsUtil.geling_ak)
                .add("apiName","getResList")
                .add("timeStamp", timStamp.toString())
                .add("recursion", "1")
                .add("pageSize","100")
                .add("sign", ApiUtils.generateSignature(parameters, ContantsUtil.geling_sk))
                .build();
        OkHttpUtil.doPost(Constant.GET_SEARCH_CATEGORY, body, new CallBackUtil.IRequestCallback(){
            @Override
            public void success(Object o){
                LogUtil.e("o = "+o.toString());
                try{
                    JSONObject root = new JSONObject(o.toString());
                    int code = root.getInt("code");
                    if(code == 0){
                        String result1 = ApiUtils.decryptData(root.getString("data"), ContantsUtil.geling_sk);
                        result1 = ApiUtils.decodeUnicode(result1);
                        LogUtil.e("result1 = " + result1);
                        JSONObject resultObj = new JSONObject(result1);
                        JSONArray list = resultObj.getJSONArray("list");
                        List<AppItem> appItems = AppItem.arrayAppItemFromData(list.toString());

                        List<AppItem> listTiem = new ArrayList<>();
                        if (appItems.size() > 0){
                            for (AppItem item : appItems){
                                if (AppInfoDao.getByFileName(item.getFileName()) == null){
                                    listTiem.add(item);
                                }
                            }
                        }
                        getViewState().refreshList(listTiem);
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

    private void getSearchList(String strKey){
        SortedMap<String, Object> parameters = new TreeMap<>();
        Long timStamp = ApiUtils.generateTimestamp();
        parameters.put("appAk", ContantsUtil.geling_ak);
        parameters.put("apiName", "getResList");
        parameters.put("timeStamp", timStamp.toString());
        parameters.put("keyword", strKey);
        parameters.put("pageSize","100");
        parameters.put("recursion", "1");

        RequestBody body = new FormBody.Builder()
                .add("keyword", strKey)
                .add("appAk", ContantsUtil.geling_ak)
                .add("apiName","getResList")
                .add("timeStamp", timStamp.toString())
                .add("recursion", "1")
                .add("pageSize","100")
                .add("sign", ApiUtils.generateSignature(parameters, ContantsUtil.geling_sk))
                .build();
        OkHttpUtil.doPost(Constant.GET_SEARCH_CATEGORY, body, new CallBackUtil.IRequestCallback(){
            @Override
            public void success(Object o){
                LogUtil.e("o = "+o.toString());
                try{
                    JSONObject root = new JSONObject(o.toString());
                    int code = root.getInt("code");
                    if(code == 0){
                        String result1 = ApiUtils.decryptData(root.getString("data"), ContantsUtil.geling_sk);
                        result1 = ApiUtils.decodeUnicode(result1);
                        LogUtil.e("result1 = " + result1);
                        JSONObject resultObj = new JSONObject(result1);
                        JSONArray list = resultObj.getJSONArray("list");
                        List<AppItem> appItems = AppItem.arrayAppItemFromData(list.toString());
                        getViewState().refreshList(appItems);
                    }else {
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
