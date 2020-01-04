package com.other.demo.application.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;


public class DaoHelper {

    private static DaoHelper daoHelper;
    private Dao<FileBean, Integer> fileDao;
    private Dao<UserBean, Integer> userBeans;

    private DaoHelper(Context mContext) {
        try {
            fileDao = DatabaseHelper.getInstance(mContext).getFileDao();
            userBeans = DatabaseHelper.getInstance(mContext).getUserDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized static DaoHelper getInstance(Context mContext) {

        if (daoHelper == null) {
            daoHelper = new DaoHelper(mContext);
        }
        return daoHelper;
    }


    //存储下载的文件
    public void addFileDown(FileBean fileBean) {
        try {
            fileDao.createIfNotExists(fileBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //查询文件是否下载了
    public FileBean queryFileIsExist(String material_url) {
        try {
            QueryBuilder<FileBean, Integer> queryBuilder = fileDao.queryBuilder();
            queryBuilder.where().eq("material_url", material_url);
            FileBean fileBean = queryBuilder.queryForFirst();
            return fileBean;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
