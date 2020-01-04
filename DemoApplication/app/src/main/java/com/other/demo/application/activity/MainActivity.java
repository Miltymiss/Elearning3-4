package com.other.demo.application.activity;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.other.demo.application.R;
import com.other.demo.application.adapter.CourseAdapter;
import com.other.demo.application.bean.CourseListBean;
import com.other.demo.application.utlis.BaseUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private CourseAdapter adapter;
    private ArrayList<CourseListBean> list;

    @Override
    protected int getContentView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);

        recycleView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CourseAdapter(R.layout.item_course_list);
        recycleView.setAdapter(adapter);

        recycleView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.cardView:
                        Intent intent = new Intent(MainActivity.this, CourseDetailViewTypeActivity.class);
                        intent.putExtra("list", list.get(position));
                        Bundle options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle();
                        startActivity(intent, options);
                        break;
                }
            }
        });

        findViewById(R.id.table_course).setOnClickListener(view -> startActivity(TableCourseActivity.class));

        getDatas();
    }

    private void getDatas() {

        OkGo.<String>get(BaseUrl.baseUrl + "courses")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("hedb", "onSuccess: " + response.body());
                        Gson gson = new Gson();
                        list = gson.fromJson(response.body(), new TypeToken<List<CourseListBean>>() {
                        }.getType());
                        adapter.setNewData(list);
                    }
                });

    }

}
