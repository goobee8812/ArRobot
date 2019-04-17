package com.cloudring.arrobot.gelin.mvp.network.response;

import com.cloudring.arrobot.gelin.base.BaseResponse;
import com.cloudring.arrobot.gelin.mvp.modle.AppItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class GetListAppByTypeIdResponse extends BaseResponse {

    @SerializedName("data")
    public  GetListAppBean data;

    public class GetListAppBean{
        @SerializedName("typeDeviceId")
        public String typeDeviceId;
        @SerializedName("donutAppList")
        public List<AppItem> listApp;
        @SerializedName("md5")
        public String md5;

    }

}
