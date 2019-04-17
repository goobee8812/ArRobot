package com.cloudring.arrobot.gelin.mvp.modle;

import com.google.gson.annotations.SerializedName;

public class AppType {

    @SerializedName("id")
    private String id;//数据库id

    @SerializedName("typeId")
    private String typeId;  //类型id

    @SerializedName("typeName")
    private String typeName;  //App 名字

    @SerializedName("sort")
    private String sort;//排序id


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "AppType{" +
                "typeName='" + typeName + '\'' +
                ", id='" + id + '\'' +
                ", sort='" + sort + '\'' +
                ", typeId='" + typeId + '\'' +
                '}';
    }
}
