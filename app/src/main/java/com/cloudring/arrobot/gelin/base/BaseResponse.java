package com.cloudring.arrobot.gelin.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by:liulonggang on 2016/6/20 10:28
 * Class: BaseResponse
 * Description: 请求返回的结果；
 */
public abstract class BaseResponse implements Serializable {

    @SerializedName("code")
    public String code;

    @SerializedName("message")
    public String message;

    public boolean isResult() {
        return "0".equals(code);
    }
}
