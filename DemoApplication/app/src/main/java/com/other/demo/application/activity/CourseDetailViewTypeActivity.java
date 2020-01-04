package com.other.demo.application.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.other.demo.application.R;
import com.other.demo.application.adapter.CourseVideoMuiltAdapter;
import com.other.demo.application.bean.CourseListBean;
import com.other.demo.application.bean.Coursevideo;
import com.other.demo.application.utlis.BaseUrl;

import java.util.List;

import butterknife.BindView;


/**
 * @author: hedianbo.
 * @date: 2019-12-26 13:14.
 * @desc: 多布局详情页面
 */
public class CourseDetailViewTypeActivity extends BaseActivity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private CourseVideoMuiltAdapter adapter;
    private CourseListBean courseListBean;
    private List<Coursevideo> list;

    @Override
    protected int getContentView(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return R.layout.activity_course_detail;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {



        getWindow().setEnterTransition(new Slide().setDuration(2000));

        courseListBean = (CourseListBean) getIntent().getSerializableExtra("list");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);

        recycleView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CourseVideoMuiltAdapter();
        recycleView.setAdapter(adapter);

        recycleView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.cardView1) {
                    switch (list.get(position).getMediatype()) {
                        case "video":
                        case "audio":
                            Intent intent = new Intent(CourseDetailViewTypeActivity.this, PlayVideoActivity.class);
                            intent.putExtra("list", list.get(position));
                            startActivity(intent);
                            break;
                        case "image":
                        case "pdf":
                            Intent intent1 = new Intent(CourseDetailViewTypeActivity.this, ImageOrPdfActivity.class);
                            intent1.putExtra("list", list.get(position));
                            startActivity(intent1);
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

        findViewById(R.id.share).setOnClickListener(view ->
                shareText(courseListBean.getDescription()));
    }

    /**
     * 分享文本
     */
    private void shareText(String content) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        //切记需要使用Intent.createChooser，否则会出现别样的应用选择框，您可以试试
        shareIntent = Intent.createChooser(shareIntent, "Here is the title of Select box");
        startActivity(shareIntent);
    }

    private void getDatas(String courseId) {

        OkGo.<String>get(BaseUrl.baseUrl + "courses/" + courseId + "/materials")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        list = gson.fromJson(response.body(), new TypeToken<List<Coursevideo>>() {
                        }.getType());
                        adapter.setNewData(list);
                    }
                });

    }


}
