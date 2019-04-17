package com.cloudring.arrobot.gelin.mvp.network.request;

/**
 * app列表请求
 */
public class GetAppListByTypeRequest {

    GetListAppByTypeId data;

    public GetAppListByTypeRequest(String typeDeviceId, String md5) {
        data = new GetListAppByTypeId();
        data.typeDeviceId = typeDeviceId;
        data.md5 = md5;
    }

    /**
     * 请求参数对象
     */
    class GetListAppByTypeId {
        public String typeDeviceId;
        public String md5;
    }
}
