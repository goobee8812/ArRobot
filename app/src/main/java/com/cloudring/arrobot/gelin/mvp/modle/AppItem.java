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
    private String subject;  //App 名字

    @JSONField(ordinal = 3)
    @SerializedName("studySection")
    private String studySection;  //APP 图片地址

    @JSONField(ordinal = 4)
    @SerializedName("categoryId")
    private String categoryId;  //APP 包名

    @JSONField(ordinal = 5)
    @SerializedName("fileName")
    private String fileName;  //APP 下载地址

    @JSONField(ordinal = 6)
    @SerializedName("grade")
    private String grade;  //APP 包名

    @JSONField(ordinal = 7)
    @SerializedName("courseId")
    private String courseId;  //APP 包名

    @JSONField(ordinal = 8)
    @SerializedName("version")
    private String version;  //APP 包名

    @JSONField(ordinal = 9)
    @SerializedName("semester")
    private String semester;  //APP 包名



    public AppItem(String id, String fileName) {
        this.id = id;
        this.fileName = fileName;
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

    @Override
    public String toString() {
        return super.toString();
    }
}
