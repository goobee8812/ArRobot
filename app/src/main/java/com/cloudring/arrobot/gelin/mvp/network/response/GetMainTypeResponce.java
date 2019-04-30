package com.cloudring.arrobot.gelin.mvp.network.response;

import com.cloudring.arrobot.gelin.base.BaseResponse;
import com.cloudring.arrobot.gelin.mvp.modle.MainType;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 主页游戏分类响应体
 */
public class GetMainTypeResponce extends BaseResponse{

    @SerializedName("data")
    public GetMainTypeResponce.GetListAppBean data;

    public class GetListAppBean{
        @SerializedName("categoryList")
        public List<MainType> categoryList;
    }
}
