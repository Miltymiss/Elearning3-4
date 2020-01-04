package com.other.demo.application.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.other.demo.application.R;


public class RegisterActivity extends BaseActivity {


    private Button register;
    private TextView userName;
    private TextView password;
    private ImageView back_iv;

    @Override
    protected int getContentView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

        register = findViewById(R.id.register);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        back_iv = findViewById(R.id.back_iv);


        back_iv.setOnClickListener(view -> onBackPressed());

        register.setOnClickListener(view -> {
            String username = userName.getText().toString().trim();
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_SHORT).show();
                return;
            }

            String password = this.password.getText().toString().trim();
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}
