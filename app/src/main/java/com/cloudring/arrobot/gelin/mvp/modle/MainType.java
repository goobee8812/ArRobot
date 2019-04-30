package com.cloudring.arrobot.gelin.mvp.modle;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MainType implements Serializable{

    /**
     * id : 1
     * icon : http://images.gelearning.net/bbmonkey/20190422/JoinSunResources/logo/cate/19.png
     * categroyName : 记忆
     * categroyPId :
     * orderNo : 1
     * categroyId : 51
     */

    @SerializedName("id")
    private String id;

    @SerializedName("icon")
    private String icon;

    @SerializedName("categroyName")
    private String categroyName;

    @SerializedName("categroyPId")
    private String categroyPId;

    @SerializedName("orderNo")
    private String orderNo;

    @SerializedName("categroyId")
    private String categroyId;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public String getCategroyName(){
        return categroyName;
    }

    public void setCategroyName(String categroyName){
        this.categroyName = categroyName;
    }

    public String getCategroyPId(){
        return categroyPId;
    }

    public void setCategroyPId(String categroyPId){
        this.categroyPId = categroyPId;
    }

    public String getOrderNo(){
        return orderNo;
    }

    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }

    public String getCategroyId(){
        return categroyId;
    }

    public void setCategroyId(String categroyId){
        this.categroyId = categroyId;
    }
}
