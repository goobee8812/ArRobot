package com.cloudring.arrobot.gelin.mvp.network.response;

import com.cloudring.arrobot.gelin.base.BaseResponse;
import com.cloudring.arrobot.gelin.mvp.modle.AppType;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class GetListTypeResponse extends BaseResponse {

    @SerializedName("data")
    public  GetListTypeBean data;

    public class GetListTypeBean{

        @SerializedName("donutTypeList")
        public List<AppType> listType;
        @SerializedName("md5")
        public String md5;

    }



}
