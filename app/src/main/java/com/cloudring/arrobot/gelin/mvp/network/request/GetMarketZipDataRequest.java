package com.cloudring.arrobot.gelin.mvp.network.request;


/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class GetMarketZipDataRequest {

    GetMarketZipDataReqeustData data;

    public GetMarketZipDataRequest(String deviceId, String versionNum) {
        data = new GetMarketZipDataReqeustData();
        data.deviceId = deviceId;
        data.versionNum = versionNum;
    }

    class GetMarketZipDataReqeustData {
        public String deviceId;
        public String versionNum;
    }
}
