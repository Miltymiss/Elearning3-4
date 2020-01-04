package com.other.demo.application.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.other.demo.application.R;
import com.other.demo.application.mvvm.MvvmMainActivity;
import com.other.demo.application.utlis.Constant;
import com.other.demo.application.utlis.SpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;


public class LoginActivity extends BaseActivity implements PlatformActionListener {

    private TextView userName;
    private TextView password;
    private Button register;
    private Button login;

    @Override
    protected int getContentView(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        return R.layout.activity_login;
    }


    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

        if ( SpUtil.getBoolean("login")){
            startActivity(MvvmMainActivity.class);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();
        }

        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        findViewById(R.id.QQ_login).setOnClickListener(view -> loginByQQ());

        register.setOnClickListener(view -> {
           startActivity(RegisterActivity.class);
        });

        login.setOnClickListener(view -> {

            String username = userName.getText().toString().trim();
            if (TextUtils.isEmpty(username)){
                Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_SHORT).show();
                return;
            }

            String password = this.password.getText().toString().trim();
            if (TextUtils.isEmpty(password)){
                Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Constant.userName1.equals(username)&&Constant.pass1.equals(password)){
                startActivity(MvvmMainActivity.class);
                SpUtil.setBoolean("login",true);
                finish();
            }else if (Constant.userName2.equals(username)&&Constant.pass2.equals(password)){
                startActivity(MvvmMainActivity.class);
                SpUtil.setBoolean("login",true);
                finish();
            }else if (Constant.userName3.equals(username)&&Constant.pass3.equals(password)){
                startActivity(MvvmMainActivity.class);
                SpUtil.setBoolean("login",true);
                finish();
            }else {
                showToast("用户名或者密码不正确");
            }
        });

    }



    private void loginByQQ() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(this);
        qq.SSOSetting(false);
        if (!qq.isClientValid()) {
            Toast.makeText(this, "QQ未安装,请先安装QQ", Toast.LENGTH_SHORT).show();
        }
        authorize(qq);
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Platform platform = (Platform) msg.obj;
                    String userId = platform.getDb().getUserId();//获取用户账号
                    String userName = platform.getDb().getUserName();//获取用户名字
                    String userIcon = platform.getDb().getUserIcon();//获取用户头像
                    String userGender = platform.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
                    startActivity(MvvmMainActivity.class);
                    SpUtil.setBoolean("login",true);
                    finish();

                    break;
                case 2:
                    Toast.makeText(LoginActivity.this, "授权登陆失败", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(LoginActivity.this, "授权登陆取消", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    private void authorize(Platform platform) {
        if (platform == null) {
            return;
        }
        if (platform.isAuthValid()) {  //如果授权就删除授权资料
            platform.removeAccount(true);
        }
        platform.showUser(null); //授权并获取用户信息
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = platform;
        mHandler.sendMessage(message);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Message message = Message.obtain();
        message.what = 2;
        message.obj = platform;
        mHandler.sendMessage(message);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Message message = Message.obtain();
        message.what = 3;
        message.obj = platform;
        mHandler.sendMessage(message);
    }
}
