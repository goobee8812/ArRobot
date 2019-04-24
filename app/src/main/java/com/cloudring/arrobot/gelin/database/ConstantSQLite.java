package com.cloudring.arrobot.gelin.database;

public class ConstantSQLite {
    /**
     * app列表
     */
    public static String APP_LIST_TABLE_NAME = "tab_app_list";

//    /**
//     * app分类
//     */
//    public static String APP_TYPE_TABLE_NAME = "tab_app_type";
//
//
//    public static String AUDIO_ALBUM_NAME = "tab_audio_album";
    /**
     * 创建表的SQLite语句
     */
    public static String createAppListTab = "create table if not exists " + APP_LIST_TABLE_NAME + " ("
            + " _id integer PRIMARY KEY autoincrement," + " id text unique," + " categoryId  text," + " fileName  text," + "type  text" + ");";

//    /**
//     * 创建app分类表SQLite语句
//     */
//    public static String createAppTypeTab = "create table if not exists " + APP_TYPE_TABLE_NAME + " ("
//            + " _id integer PRIMARY KEY autoincrement," + " _type_list text," + "_md5  text" + ");";
//
//    public static String createAblumListTab = "create table if not exists " + AUDIO_ALBUM_NAME + " ("
//            + " _id integer PRIMARY KEY autoincrement," + " _category_id text," + "_ablum_content text," + "_version_name  text,"+" _language text"+");";
}
