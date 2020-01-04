package com.other.demo.application.adapter;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.other.demo.application.R;
import com.other.demo.application.bean.CourseListBean;
import com.other.demo.application.utlis.DateUtils;

import java.security.Timestamp;

/**
 * author: hedianbo.
 * date: 2019-12-22
 * desc:
 */
public class CourseAdapter extends BaseQuickAdapter<CourseListBean, BaseViewHolder> {

    public CourseAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseListBean item) {

        TextView tv_course_name = helper.getView(R.id.tv_course_name);
        TextView time = helper.getView(R.id.time);

        tv_course_name.setText(item.getName());
        time.setText(DateUtils.parseString(item.getLastUpdateOn()));

        helper.addOnClickListener(R.id.cardView);

    }
}
