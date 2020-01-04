package com.other.demo.application.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.other.demo.application.R;
import com.other.demo.application.bean.Coursevideo;
import com.other.demo.application.utlis.BaseUrl;
import com.other.demo.application.utlis.DateUtils;

/**
 * author: hedianbo.
 * date: 2019-12-22
 * desc: 多布局的
 */
public class CourseVideoMuiltAdapter extends BaseMultiItemQuickAdapter<Coursevideo, BaseViewHolder> {

    public CourseVideoMuiltAdapter() {
        super(null);
        addItemType(Coursevideo.IMAGE_TYPE, R.layout.more_item_image);
        addItemType(Coursevideo.VIDEO_TYPE, R.layout.more_item_video);
        addItemType(Coursevideo.NORMAL_TYPE, R.layout.more_item_normal);
    }


    @Override
    protected void convert(BaseViewHolder helper, Coursevideo item) {

        int position = helper.getLayoutPosition();
        switch (helper.getItemViewType()) {
            case Coursevideo.IMAGE_TYPE:
                ImageView image = helper.getView(R.id.image);
                TextView creat_time = helper.getView(R.id.creat_time);

                Glide.with(mContext).load(BaseUrl.baseImage + item.getMaterialUrl()).into(image);
                creat_time.setText(DateUtils.parseString(item.getCreateDate()));
                break;
            case Coursevideo.VIDEO_TYPE:
                ImageView iamgev = helper.getView(R.id.list_item_container);
                if (position % 2 == 0) {
                    iamgev.setBackgroundResource(R.mipmap.xxx1);
                } else {
                    iamgev.setBackgroundResource(R.mipmap.xxx2);
                }

                break;
            case Coursevideo.NORMAL_TYPE:
                TextView courseId = helper.getView(R.id.courseId);
                TextView materialType = helper.getView(R.id.materialType);

                courseId.setText(item.getCourseId());
                materialType.setText(item.getMaterialType());
                break;
        }

        helper.addOnClickListener(R.id.cardView1);
    }
}
