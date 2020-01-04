package com.other.demo.application.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static DatabaseHelper dataBaseHelper;
    private final Context mContext;
    private Dao<FileBean, Integer> fileDao = null;
    private Dao<UserBean, Integer> userDao;

    private DatabaseHelper(Context context) {
        super(context,  DbConst.DB_NAME, null, DbConst.DB_VERSION);
        mContext = context;
    }

    public synchronized static DatabaseHelper getInstance(Context mContext) {

        if (dataBaseHelper == null) {
            dataBaseHelper = new DatabaseHelper(mContext);
        }
        return dataBaseHelper;
    }

    public Dao<FileBean, Integer> getFileDao() throws SQLException {
        if (fileDao == null) {
            fileDao = getDao(FileBean.class);
        }
        return fileDao;
    }


    public Dao<UserBean, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(UserBean.class);
        }
        return userDao;
    }





    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i("hedb", "SQLiteDatabase onCreate: ");
            TableUtils.createTableIfNotExists(connectionSource, FileBean.class);
            TableUtils.createTableIfNotExists(connectionSource, UserBean.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据库更新
     */
    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource connectionSource, int arg2, int arg3) {
        Log.i("hedb", "SQLiteDatabase onUpgrade: arg2:" + arg2 + "arg3:  " + arg3);
        try {
            TableUtils.createTableIfNotExists(connectionSource, FileBean.class);
            TableUtils.createTableIfNotExists(connectionSource, UserBean.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
    }

}
