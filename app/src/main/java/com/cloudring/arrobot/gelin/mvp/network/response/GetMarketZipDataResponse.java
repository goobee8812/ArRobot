package com.cloudring.arrobot.gelin.mvp.network.response;

import com.cloudring.arrobot.gelin.base.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class GetMarketZipDataResponse extends BaseResponse {

    @SerializedName("data")
    public GetMarketZipUpdateData data;

    public class GetMarketZipUpdateData {

        @SerializedName("jsonYyscZipUrl")
        public String jsonYyscZipUrl;
        @SerializedName("versionNum")
        public String versionNum;

    }


}
