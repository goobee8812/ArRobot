package com.cloudring.arrobot.gelin.utils;

/**
 * Created by zhuyaoting on 2018/5/19.
 */
public class Constant{

    public static final String BaseUrl = "http://test-rscloud.getlearn.cn/api/index/index/"; //正式地址
    //域名，用于拼接图片
    public static final String BaseHttp = "http://images.gelearning.net";

    //主界面分类列表
    public static final String GET_TYPE = BaseUrl + "getCategoryList";

    //获取搜索内容,已经分类列表数据
    public static final String GET_SEARCH_CATEGORY = BaseUrl + "getResList";
}
