package com.other.demo.application.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.other.demo.application.R;
import com.other.demo.application.bean.CourseListBean;
import com.other.demo.application.databinding.ItemMvvmVideoListBinding;

import java.util.ArrayList;

/**
 * author: hedianbo.
 * date: 2019-12-26
 * desc:
 */
public class BindingAdapter extends RecyclerView.Adapter<BindingViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private Activity context;
    private ArrayList<CourseListBean> hallList = new ArrayList<>();
    private ItemOnClickInterface itemOnClickInterface;

    public BindingAdapter(Activity context) {
        this.context = context;
        mLayoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //定义回调方法
    public void setItemOnClickInterface(ItemOnClickInterface itemOnClickInterface) {
        this.itemOnClickInterface = itemOnClickInterface;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemMvvmVideoListBinding itemHallBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout.item_mvvm_video_list, parent, false);
        return new BindingViewHolder(itemHallBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder bindingViewHolder, final int position) {

        ItemMvvmVideoListBinding binding = (ItemMvvmVideoListBinding) bindingViewHolder.getBinding();
        binding.setVariable(com.other.demo.application.BR.itemVideo, hallList.get(position));
        binding.executePendingBindings();

        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOnClickInterface != null) {
                    itemOnClickInterface.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hallList.size();
    }

    public void replaceData(ArrayList<CourseListBean> datas) {

        hallList.clear();
        hallList.addAll(datas);
        notifyDataSetChanged();
    }

    //回调接口
    public interface ItemOnClickInterface {
        void onItemClick(View view, int position);
    }
}
