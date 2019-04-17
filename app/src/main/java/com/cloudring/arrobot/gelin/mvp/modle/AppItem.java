package com.cloudring.arrobot.gelin.mvp.modle;

import com.google.gson.annotations.SerializedName;

/**
 * yc
 */
public class AppItem {

    @SerializedName("appId")
    private String appId;  //App ID

    @SerializedName("appName")
    private String appName;  //App 名字

    @SerializedName("icoUrl")
    private String icoUrl;  //APP 图片地址

    @SerializedName("appData")
    private String appData;  //APP 包名

    @SerializedName("downloadUrl")
    private String downloadUrl;  //APP 下载地址


    @SerializedName("className")
    private String className;  //APP 包名

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getIcoUrl() {
        return icoUrl;
    }

    public void setIcoUrl(String icoUrl) {
        this.icoUrl = icoUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getAppData() {
        return appData;
    }

    public void setAppData(String appData) {
        this.appData = appData;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "AppItem{" +
                "appName='" + appName + '\'' +
                ", appId=" + appId +
                ", icoUrl='" + icoUrl + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", appData='" + appData + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
