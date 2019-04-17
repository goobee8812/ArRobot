package com.cloudring.arrobot.gelin.mvp.network.request;


/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class GetListTypeRequest {

    GetListTypeByCodeBody data;

    public GetListTypeRequest(String deviceId, String md5) {
        data = new GetListTypeByCodeBody();
        data.deviceId = deviceId;
        data.md5=md5;
    }

    class  GetListTypeByCodeBody {
        public String deviceId;
        public String md5;
    }
}
