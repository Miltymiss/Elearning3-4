package com.other.demo.application.mvvm;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.other.demo.application.R;
import com.other.demo.application.activity.CourseDetailViewTypeActivity;
import com.other.demo.application.activity.TableCourseActivity;
import com.other.demo.application.adapter.BindingAdapter;
import com.other.demo.application.bean.CourseListBean;
import com.other.demo.application.databinding.ActivityMvvmMainBinding;

import java.util.ArrayList;

public class MvvmMainActivity extends AppCompatActivity {

    ArrayList<CourseListBean> courseListBean;
    private ActivityMvvmMainBinding viewDataBinding;
    private BindingAdapter adapter;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm_main);

        initRecyclerView();
    }


    private void initRecyclerView() {

        //构建ViewModel实例
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewDataBinding.recycleView.setLayoutManager(layoutManager);
        viewDataBinding.recycleView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BindingAdapter(this);
        viewDataBinding.recycleView.setAdapter(adapter);

        findViewById(R.id.table_course).setOnClickListener(view -> {
            Intent intent = new Intent(MvvmMainActivity.this, TableCourseActivity.class);
            startActivity(intent);
        });


        adapter.setItemOnClickInterface((view, position) -> {
            Intent intent = new Intent(MvvmMainActivity.this, CourseDetailViewTypeActivity.class);
            intent.putExtra("list", courseListBean.get(position));
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        });

        courseViewModel.mutableLiveData.observe(this, new Observer<ArrayList<CourseListBean>>() {
            @Override
            public void onChanged(@Nullable ArrayList<CourseListBean> courseListBean) {
                MvvmMainActivity.this.courseListBean = courseListBean;
                adapter.replaceData(courseListBean);
            }
        });

        courseViewModel.getDatas();
    }
}
