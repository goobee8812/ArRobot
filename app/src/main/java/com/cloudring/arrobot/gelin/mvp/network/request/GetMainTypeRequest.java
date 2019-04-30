package com.cloudring.arrobot.gelin.mvp.network.request;

public class GetMainTypeRequest{
    GetMainTypeRequest.GetListTypeId data;

    public GetMainTypeRequest(String deviceId){
        data = new GetMainTypeRequest.GetListTypeId();
        data.deviceId = deviceId;
    }

    /**
     * 请求参数对象
     */
    class GetListTypeId{
        public String deviceId;
    }
}
