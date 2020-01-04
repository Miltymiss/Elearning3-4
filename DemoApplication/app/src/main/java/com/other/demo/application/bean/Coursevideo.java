package com.other.demo.application.bean;

import android.app.Service;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.other.demo.application.R;

import java.io.Serializable;

/**
 * author: hedianbo.
 * date: 2019-12-23
 * desc:
 */
public class Coursevideo implements Serializable , MultiItemEntity {

    public static final int NORMAL_TYPE = 1;
    public static final int VIDEO_TYPE = 2;
    public static final int IMAGE_TYPE = 3;

    /**
     * id : 2
     * courseId : 001
     * mediatype : image
     * materialType : image
     * materialUrl : 001\boat.jpg
     * createDate : 2019-12-04T22:07:49.000+0000
     * description : null
     * status : 1
     */

    private String id;
    private String courseId;
    private String mediatype;
    private String materialType;
    private String materialUrl;
    private String createDate;
    private Object description;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int getItemType() {
        if (mediatype.equals("image")) {
            return IMAGE_TYPE;
        } else if (mediatype.equals("video")) {
            return VIDEO_TYPE;
        } else {
            return NORMAL_TYPE;
        }
    }

}
