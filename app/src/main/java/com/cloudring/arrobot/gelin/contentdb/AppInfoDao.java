package com.cloudring.arrobot.gelin.contentdb;

import android.content.Context;

import com.cloudring.arrobot.gelin.utils.GlobalUtil;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;


public class AppInfoDao {

    public AppInfoDao(Context context) {
    }

    public static void add(AppInfo appInfo) {
        //txBinderInfoDaoOpe.create(appInfo);
        if (getByFileName(appInfo.getFileName()) == null){
            appInfo.save();
        }
    }

    public static AppInfo getByFileName(String fileName){
        return new Select().from(AppInfo.class).where(AppInfo_Table.fileName.eq(fileName)).querySingle();
//        return null;
    }

    public static List<AppInfo> getAppList() {
        return new Select().from(AppInfo.class).queryList();
    }

    /**
     * 获取收藏内容列表
     * @return
     */
    public static List<AppInfo> getCollectionList() {
        return new Select().from(AppInfo.class).where(AppInfo_Table.type.eq(GlobalUtil.CATEGORY_TYPE_COLLECTION)).queryList();
    }

    /**
     * 获取我的游戏内容列表
     * @return
     */
    public static List<AppInfo> getGameAppList() {
        return new Select().from(AppInfo.class).where(AppInfo_Table.type.eq(GlobalUtil.CATEGORY_TYPE_MY_GAME)).queryList();
    }


    /**
     *
     * @param type     收藏或我的游戏
     * @param CategoryId  分类
     * @return
     */
    public static List<AppInfo> getCategoryAppList(String type, String CategoryId) {
        OperatorGroup op = OperatorGroup.clause(OperatorGroup.clause()
                .and(AppInfo_Table.type.eq(type))
                .and(AppInfo_Table.topCategoryId.eq(CategoryId)));
        return new Select().from(AppInfo.class).where(op).queryList();
    }

    public static void update(AppInfo appInfo, String userSN, String tinyid){
        appInfo.update();

    }

    public static void update(String appId, String type, String packName){
        SQLite.update(AppInfo.class)
                .set(AppInfo_Table.type.eq(type),AppInfo_Table.packageName.eq(packName))
                .where(AppInfo_Table.id.eq(appId)).execute();
    }

    public static void deleteAll(){
        List<AppInfo> list = getAppList();
        if (list.size() > 0){
            for (AppInfo info : list){
                info.delete();
            }
        }
    }

    public static void deleteList(List<AppInfo> list){
        if(list.size() > 0){
            for(AppInfo info : list){
                info.delete();
            }
        }
    }

    public static void delete(AppInfo appInfo){
        appInfo.delete();
    }

    public static void deleteTXBinderInfoByID(AppInfo appInfo) {
        appInfo.delete();
    }

    public void addList(List<AppInfo> list) {

        if (null != list && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                AppInfo appInfo = list.get(i);
                appInfo.save();
            }
        }
    }


}
