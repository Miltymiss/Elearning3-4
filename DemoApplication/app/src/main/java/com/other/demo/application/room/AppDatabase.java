package com.other.demo.application.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * author: hedianbo.
 * date: 2019-12-27
 * desc:
 */

@Database(entities = {VideoBean.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "room.db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .build();
    }

    //RoomDatabase提供直接访问底层数据库实现，我们通过定义抽象方法返回具体Dao
    //然后进行数据库增删该查的实现。
    public abstract VideoDao videoDao();

}
