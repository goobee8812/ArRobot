package com.cloudring.arrobot.gelin.mvp.network.request;


/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class GetMarketZipDataRequest {

    GetMarketZipDataRequestData data;

    public GetMarketZipDataRequest(String deviceId, String versionNum) {
        data = new GetMarketZipDataRequestData();
        data.deviceId = deviceId;
        data.versionNum = versionNum;
    }

    class GetMarketZipDataRequestData {
        public String deviceId;
        public String versionNum;
    }
}
