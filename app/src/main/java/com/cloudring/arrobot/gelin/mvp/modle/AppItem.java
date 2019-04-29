package com.cloudring.arrobot.gelin.mvp.modle;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;

/**
 * yc
 */
public class AppItem {
    @JSONField(ordinal = 1)
    @SerializedName("id")
    private String id;  //App ID

    @JSONField(ordinal = 2)
    @SerializedName("subject")
    private String subject;  //

    @JSONField(ordinal = 3)
    @SerializedName("studySection")
    private String studySection;  //

    @JSONField(ordinal = 4)
    @SerializedName("categoryId")
    private String categoryId;  //APP 分类

    @JSONField(ordinal = 5)
    @SerializedName("fileName")
    private String fileName;  //APP 名字

    @JSONField(ordinal = 6)
    @SerializedName("grade")
    private String grade;  //APP

    @JSONField(ordinal = 7)
    @SerializedName("courseId")
    private String courseId;  //APP

    @JSONField(ordinal = 8)
    @SerializedName("version")
    private String version;  //APP

    @JSONField(ordinal = 9)
    @SerializedName("semester")
    private String semester;  //APP

    @JSONField(ordinal = 10)
    @SerializedName("icon1")
    private String icon1;  //APP

    @JSONField(ordinal = 11)
    @SerializedName("topCategoryId")
    private String topCategoryId;  //topCategoryId

    @SerializedName("packageName")
    private String packageName;  //APP 包名

    @SerializedName("type")
    private String type;  //APP 0：默认  1：已下载  2：收藏

    public AppItem(String id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public AppItem(String id, String fileName,String packageName,String icon1,String topCategoryId) {
        this.id = id;
        this.fileName = fileName;
        this.packageName = packageName;
        this.icon1 = icon1;
        this.topCategoryId = topCategoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStudySection() {
        return studySection;
    }

    public void setStudySection(String studySection) {
        this.studySection = studySection;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    public String toString() {
        return super.toString();
    }
}
