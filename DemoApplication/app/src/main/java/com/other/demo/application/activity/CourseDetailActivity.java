package com.other.demo.application.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.other.demo.application.R;
import com.other.demo.application.adapter.CourseVideoAdapter;
import com.other.demo.application.bean.CourseListBean;
import com.other.demo.application.bean.Coursevideo;
import com.rxjava.rxlife.RxLife;

import java.util.List;

import butterknife.BindView;

public class CourseDetailActivity extends BaseActivity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private CourseVideoAdapter adapter;
    private CourseListBean courseListBean;
    private List<Coursevideo> list;

    @Override
    protected int getContentView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_course_detail;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {


        courseListBean = (CourseListBean) getIntent().getSerializableExtra("list");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);

        recycleView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CourseVideoAdapter(R.layout.item_video_list);
        recycleView.setAdapter(adapter);

        recycleView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.cardView) {
                    switch (list.get(position).getMediatype()) {
                        case "video":
                        case "audio":

                            Intent intent = new Intent(CourseDetailActivity.this, PlayVideoActivity.class);
                            intent.putExtra("list", list.get(position));
                            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

                            startActivity(intent);
                            break;
                        case "image":
                        case "pdf":

                            Intent intent1 = new Intent(CourseDetailActivity.this, ImageOrPdfActivity.class);
                            intent1.putExtra("list", list.get(position));
                            startActivity(intent1);
                            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

                            break;
                    }
                }
            }
        });

        if (courseListBean != null) {
            TextView header = new TextView(this);
            header.setLineSpacing(1.6f, 1.6f);
            header.setPadding(20, 20, 20, 20);
            header.setTextSize(22);
            header.setText("Course Description:" + courseListBean.getDescription());
            adapter.addHeaderView(header);

            getDatas(courseListBean.getId());
        }
    }

    private void getDatas(String courseId) {

//        RxHttp.get("courses/" + courseId + "/materials")
//                .asString()
//                .as(RxLife.as(this))
//                .subscribe(str -> {
//                    Gson gson = new Gson();
//                    list = gson.fromJson(str, new TypeToken<List<Coursevideo>>() {
//                    }.getType());
//                    adapter.setNewData(list);
//                }, throwable -> {
//                    Toast.makeText(mContext, "数据获取异常", Toast.LENGTH_SHORT).show();
//                });
    }
}
