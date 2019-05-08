package com.cloudring.arrobot.gelin.mvp.modle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainType implements Serializable{

    /**
     * categName : 数理
     * pid : 49
     * addTime : 2019-03-18 16:04:07
     * upTime : 2019-05-08 17:26:22
     * status : 0
     * orderNo : 6
     * icon : /bbmonkey/20190422/JoinSunResources/logo/cate/ar_robot_shuli.png
     * id : 67
     */

    private String categName;
    private int pid;
    private String addTime;
    private String upTime;
    private int status;
    private int orderNo;
    private String icon;
    private int id;
    private int resCategoryId;

    public static MainType objectFromData(String str){
        return new Gson().fromJson(str, MainType.class);
    }

    public static List<MainType> arrayMainTypeFromData(String str){
        Type listType = new TypeToken<ArrayList<MainType>>(){
        }.getType();
        return new Gson().fromJson(str, listType);
    }

    public String getCategName(){
        return categName;
    }

    public void setCategName(String categName){
        this.categName = categName;
    }

    public int getPid(){
        return pid;
    }

    public void setPid(int pid){
        this.pid = pid;
    }

    public String getAddTime(){
        return addTime;
    }

    public void setAddTime(String addTime){
        this.addTime = addTime;
    }

    public String getUpTime(){
        return upTime;
    }

    public void setUpTime(String upTime){
        this.upTime = upTime;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getOrderNo(){
        return orderNo;
    }

    public void setOrderNo(int orderNo){
        this.orderNo = orderNo;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getResCategoryId(){
        return resCategoryId;
    }

    public void setResCategoryId(int resCategoryId){
        this.resCategoryId = resCategoryId;
    }
}
