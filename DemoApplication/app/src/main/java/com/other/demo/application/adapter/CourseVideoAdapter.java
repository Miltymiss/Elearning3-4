package com.other.demo.application.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.other.demo.application.R;
import com.other.demo.application.bean.CourseListBean;
import com.other.demo.application.bean.Coursevideo;
import com.other.demo.application.utlis.DateUtils;

/**
 * author: hedianbo.
 * date: 2019-12-22
 * desc:
 */
public class CourseVideoAdapter extends BaseQuickAdapter<Coursevideo, BaseViewHolder> {

    public CourseVideoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Coursevideo item) {

        TextView courseId = helper.getView(R.id.courseId);
        TextView materialType = helper.getView(R.id.materialType);

        courseId.setText(item.getCourseId());
        materialType.setText(item.getMaterialType());

        helper.addOnClickListener(R.id.cardView);
    }
}
