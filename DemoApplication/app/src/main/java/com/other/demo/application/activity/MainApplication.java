package com.other.demo.application.activity;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.mob.MobSDK;
import com.other.demo.application.dao.DatabaseHelper;
import com.other.demo.application.utlis.SpUtil;


public class MainApplication extends Application {

    private static final String TAG = MainApplication.class.getName();

    private static MainApplication mApplication;

    public static synchronized MainApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        MobSDK.init(this);
        //必须调用初始化
        OkGo.getInstance().init(this);
        DatabaseHelper.getInstance(this);
        SpUtil.init(this);
    }

}
