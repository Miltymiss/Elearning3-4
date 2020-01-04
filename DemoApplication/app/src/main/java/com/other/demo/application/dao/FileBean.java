package com.other.demo.application.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * author: hedianbo.
 * date: 2019-12-16
 * desc:
 */
@DatabaseTable()
public class FileBean implements Serializable {

    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;

    @DatabaseField()
    private String course_id;//课程id

    @DatabaseField()
    private String material_id;//具体哪个文件id

    @DatabaseField()
    private String mediatype; //image pdf video audio

    @DatabaseField()
    private String localPath;

    @DatabaseField()
    private String material_url;


    public String getMaterial_url() {
        return material_url == null ? "" : material_url;
    }

    public void setMaterial_url(String material_url) {
        this.material_url = material_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourse_id() {
        return course_id == null ? "" : course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getMaterial_id() {
        return material_id == null ? "" : material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public String getMediatype() {
        return mediatype == null ? "" : mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public String getLocalPath() {
        return localPath == null ? "" : localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
