package com.cloudring.arrobot.gelin.mvp.network.request;

/**
 * app列表请求
 */
public class GetAppListByTypeRequest {

    GetListAppByTypeId data;

    public GetAppListByTypeRequest(String categoryId) {
        data = new GetListAppByTypeId();
        data.categoryId = categoryId;
    }

    /**
     * 请求参数对象
     */
    class GetListAppByTypeId {
        public String categoryId;
    }
}
