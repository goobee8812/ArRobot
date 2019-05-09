package com.cloudring.arrobot.gelin.contentdb;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = DBFlowDataBase.class)
public class AppInfo extends BaseModel {

    @PrimaryKey(autoincrement = true)
    private int autoId;
    @Column
    private String id;
    @Column
    private String categoryId;
    @Column
    private String fileName;
    @Column
    private String type;  // 0:more  1：我的游戏  2：收藏
    @Column
    private String packageName;  //包名
    @Column
    private String icon1;  //图片路径
    @Column
    private String topCategoryId;  //包名

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getIcon1() {
        return icon1;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }

    public String getTopCategoryId() {
        return topCategoryId;
    }

    public void setTopCategoryId(String topCategoryId) {
        this.topCategoryId = topCategoryId;
    }

    @Override
    public String toString(){
        return "AppInfo{" + "id='" + id + '\'' + ", categoryId='" + categoryId + '\'' + ", fileName='" + fileName + '\'' + ", type='" + type + '\'' + ", packageName='" + packageName + '\'' + '}';
    }
}