package com.cloudring.arrobot.gelin.manager;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class PREvent {

    /** 连接成功 */
    public static final char PR_MQTT_MSG_CONNECT_OK = 0x0101;
    /** 连接失败 */
    public static final char PR_MQTT_MSG_CONNECT_FAIL =0x0102;
    /** 断网 */
    public static final char PR_MQTT_MSG_NET_OFF = 0x0103;
    /** 有可用网络 */
    public static final char PR_MQTT_MSG_NET_ON = 0x0104;
    /** 有可用网络 */
    public static final char PR_MQTT_MSG_NET_CONNECTING = 0x0105;
    /** 已经在其他地方登录，通知退出 */
    public static final char PR_MQTT_MSG_REPEAT_LOGOUT =0x0106;
    /**连接断掉- */
    public static final char PR_MQTT_MSG_ERROR_CONNECTION_BROKEN = 0x0107;

    /**退出应用- */
    public static final char PR_HTTP_LOGINOUT = 0x0108;



    /*******************设备信息************/
    public static final char PR_DEVICE_AUTHORIZE =0x2001;
    public static final char PR_DEVICE_AUTHORIZE_RESP =0x2002;

    /*****************删除设备信息delete_device_resp******/
    public static final char PR_DELETE_DEVICE_RESP =0x2003;
    /*****************user_notice_msg ******/
    public static final char PR_USER_NOTICE_MSG =0x2004;
    /*****************获取设备的用户信息******/
    public static final char PR_GET_USER_DEVICE_LIST_RESP =0x2005;
    /*****************删除设备通知到其他用户******/
    public static final char PR_DELETE_DEVICE_NOTICE_MSG =0x2006;

    /*****************添加成员******/
    public static final char PR_ADD_FAMILY_MEMBER_RESP =0x2007;

    /*****************添加事务提醒******/
    public static final char PR_ADD_USER_TRANSACTION_RESP =0x2008;

    /*****************修改事务提醒******/
    public static final char PR_MODIFY_USER_TRANSACTION_RESP =0x2009;

    /*****************获取事务提醒******/
    public static final char PR_GET_USER_TRANSACTION_RESP =0x2010;

    /*****************删除事务提醒******/
    public static final char PR_DEIETE_USER_TRANSACTION_RESP =0x2011;

    /*****************爱心督导事件******/
    public static final char PR_ADULT_SET_DEVICE_EVENT_RESP =0x2012;

    /*****************添加成员成功******/
    public static final char PR_ADD_FAMILY_MEMBER_SUCCESS =0x2013;

    /*****************添加成员失败******/
    public static final char PR_ADD_FAMILY_MEMBER_FAIL =0x2014;

    /*****************删除成员成功******/
    public static final char PR_DELETE_FAMILY_MEMBER_SUCCESS =0x2015;

    /*****************删除成员失败******/
    public static final char PR_DELETE_FAMILY_MEMBER_FAIL =0x2016;

    /*****************获取成员成功******/
    public static final char PR_GET_FAMILY_MEMBER_SUCCESS =0x2017;

    /*****************获取成员失败******/
    public static final char PR_GET_FAMILY_MEMBER_FAIL =0x2018;

    /*****************获取设备信息******/
    public static final char PR_GET_DEVICE_INFO_RESP =0x2019;

    /*****************设备状态******/
    public static final char PR_DEVICE_STATE_RESP =0x2020;

    /*****************设备夜间模式******/
    public static final char PR_DEVICE_NIGHT_MODE_RESP =0x2021;


    /*****************刷新主界面Toast******/
    public static final char PR_REFRESH_TOAST_VIEW =0x3001;

    /*****************udp信息******/
    public static final char PR_SEND_MSG_VIEW =0x3002;
    /*****************UDP 文件完成******/
    public static final char PR_SEND_MSG_FINISH =0x3003;
    /*****************获取IP地址******/
    public static final char PR_GET_MSG_IP =0x3004;
    /*****************接收列表信息******/
    public static final char PR_GET_SOUND_LIST =0x3005;
    /*****************设置绘本声音******/
    public static final char PR_SET_BOOK_SOUND =0x3006;

    /******************获取音乐舞步*********************/
    public static final char PR_GET_DANCE_LIST = 0x3007;
    /******************增加音乐************************/
    public static final char PR_ADD_DANCE_MUSIC = 0x3008;
    /******************删除音乐************************/
    public static final char PR_DEL_DANCE_MUSIC = 0x3009;
    /******************修改音乐************************/
    public static final char PR_MODIFY_DANCE_MUSIC = 0x3010;



    public static final String UDP_BROADCAST_RECEIVE_CMD="udp_broadcast_receive_cmd";
    public static final String UDP_MSG_SEND_CMD="udp_msg_send_cmd";
    public static final String UDP_MSG_RECEIVE_CMD="udp_msg_receive_cmd";

    public static final String UDP_BROADCAST_RECEIVE_BOOK_CMD="udp_broadcast_receive_book_cmd";

    //局域网音乐同步
    public static final String UDP_BOBDCBST_SYCH_PHONE_MUSIC_CMD="udp_broadcast_sych_phone_music_cmd";

    //图片地址
//    public static final String BOOKPICTURE_URL = "http://120.78.175.1/";
    public static final String BOOKPICTURE_URL = "http://120.78.175.1/";

    //文件服务器地址
    public static final String FILE_SERVER_URL = "120.78.175.1:22122";
}
