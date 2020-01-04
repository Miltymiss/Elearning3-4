package com.other.demo.application.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * author: hedianbo.
 * date: 2019-12-27
 * desc:
 */

//entity声明定义，并且指定了映射数据表明
@Entity(tableName = "video")
public class VideoBean {
    //设置主键，并且定义自增增
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String videoPath;//本地视频下载地址

    private String material_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoPath() {
        return videoPath == null ? "" : videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getMaterial_url() {
        return material_url == null ? "" : material_url;
    }

    public void setMaterial_url(String material_url) {
        this.material_url = material_url;
    }
}
