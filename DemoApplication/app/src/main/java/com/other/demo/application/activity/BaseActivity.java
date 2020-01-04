package com.other.demo.application.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    public static BaseActivity baseActivity;
    public static List<Activity> activities = new ArrayList<>();
    protected static Application mAppContext = MainApplication.getInstance();
    protected Context mContext = null;
    private Unbinder mUnbinder;

    public static Application getMApplication() {
        return mAppContext;
    }

    public static void logOut(){
//        if (activities.size()>0){
//            for (Activity activity : activities) {
//                if (!activity.isFinishing() && !(activity instanceof LoginActivity)) {
//                    activity.finish();
//                }
//            }
//        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        baseActivity = this;
        addActivity(this);
        checkPermission();
        mContext = BaseActivity.this;
        try {
            int layoutResID = getContentView(savedInstanceState);
            if (layoutResID != 0) {
                setContentView(layoutResID);
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initData(savedInstanceState);

    }

    protected void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_EXTERNAL_STORAGE},
                        100);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 100: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        System.out.println("Permissions --> " + "Permission Granted: " + permissions[i]);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(baseActivity, "拒绝权部分功能会有问题", Toast.LENGTH_SHORT).show();
                        System.out.println("Permissions --> " + "Permission Denied: " + permissions[i]);
                    }
                }
            }
            break;
        }
    }

    protected static void showToast(String text){
        Toast.makeText(mAppContext, text, Toast.LENGTH_SHORT).show();

    }

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 设置布局文件
     *
     * @param savedInstanceState
     * @return
     */
    protected abstract int getContentView(@Nullable Bundle savedInstanceState);


    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected abstract void initData(@Nullable Bundle savedInstanceState);

    @Override
    protected void onStop() {
        super.onStop();
        hideInput();
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        super.onDestroy();
    }

    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        baseActivity = this;
        super.onResume();
    }
}
