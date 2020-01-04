package com.other.demo.application.adapter;

/**
 * author: hedianbo.
 * date: 2019-12-26
 * desc:
 */

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder{

    private T mBinding;

    public BindingViewHolder(T mBinding) {
        super(mBinding.getRoot());
        this.mBinding = mBinding;
    }

    public T getBinding(){
        return mBinding;
    }

}