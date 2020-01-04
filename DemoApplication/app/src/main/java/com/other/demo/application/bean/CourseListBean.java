package com.other.demo.application.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;



import java.io.Serializable;

/**
 * author: hedianbo.
 * date: 2019-12-22
 * desc: 课程列表实体
 */
public class CourseListBean extends BaseObservable implements Serializable {

    /**
     * id : 001
     * name : Android Development
     * code : 2019SSE_001
     * categoryId : ICT_SW_001
     * description : A course for basic android appication development
     * price : 0
     * status : Open
     * openDate : 2019-09-01T00:00:00.000+0000
     * lastUpdateOn : 2019-11-25T09:01:20.000+0000
     * level : basic
     * shared : 0
     * sharedUrl : null
     * avatar : 001\av001.jpg
     * bigAvatar : null
     * certification : BJTU
     * certificationDuration : Forever
     */

    private String id;
    private String name;
    private String code;
    private String categoryId;
    private String description;
    private String price;
    private String status;
    private String openDate;
    private String lastUpdateOn;
    private String level;
    private String shared;
    private Object sharedUrl;
    private String avatar;
    private Object bigAvatar;
    private String certification;
    private String certificationDuration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Bindable
    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(com.other.demo.application.BR.name);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpenDate() {
        return openDate;
    }

    @Bindable
    public void setOpenDate(String openDate) {
        this.openDate = openDate;
        notifyPropertyChanged(com.other.demo.application.BR.openDate);

    }


    public String getLastUpdateOn() {
        return lastUpdateOn;
    }


    public void setLastUpdateOn(String lastUpdateOn) {
        this.lastUpdateOn = lastUpdateOn;

    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public Object getSharedUrl() {
        return sharedUrl;
    }

    public void setSharedUrl(Object sharedUrl) {
        this.sharedUrl = sharedUrl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Object getBigAvatar() {
        return bigAvatar;
    }

    public void setBigAvatar(Object bigAvatar) {
        this.bigAvatar = bigAvatar;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getCertificationDuration() {
        return certificationDuration;
    }

    public void setCertificationDuration(String certificationDuration) {
        this.certificationDuration = certificationDuration;
    }
}
