package com.other.demo.application.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * author: hedianbo.
 * date: 2019-12-18
 * desc:
 */
@DatabaseTable
public class UserBean {

    @DatabaseField(generatedId = true, canBeNull = false)
    private int userId;
    @DatabaseField
    private String userName;
    @DatabaseField
    private String userPass;
    @DatabaseField
    private String imagePath;

    public String getImagePath() {
        return imagePath == null ? "" : imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass == null ? "" : userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
