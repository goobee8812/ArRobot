package com.cloudring.arrobot.gelin.mvp.network;


import com.cloudring.arrobot.gelin.mvp.network.request.GetAppListByTypeRequest;
import com.cloudring.arrobot.gelin.mvp.network.request.GetListTypeRequest;
import com.cloudring.arrobot.gelin.mvp.network.request.GetMarketZipDataRequest;
import com.cloudring.arrobot.gelin.mvp.network.response.GetListAppByTypeIdResponse;
import com.cloudring.arrobot.gelin.mvp.network.response.GetListTypeResponse;
import com.cloudring.arrobot.gelin.mvp.network.response.GetMarketZipDataResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by:cc on 2017/5/20 11:19
 *
 */

public interface APIService {

    //获取app列表接口
    @POST(API.URL.REQUEST_MARKET_LISTAPP)
    Call<GetListAppByTypeIdResponse> getListApp(@Body GetAppListByTypeRequest registerRequest);


    //获取应用市场列表资源更新包
    @POST(API.URL.REQUEST_YYSC_ZIP)
    Call<GetMarketZipDataResponse> getMarktDataUpdateInfo(@Body GetMarketZipDataRequest registerRequest);
}
