package com.other.demo.application.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.other.demo.application.bean.CourseListBean;
import com.other.demo.application.utlis.BaseUrl;

import java.util.ArrayList;
import java.util.List;



/**
 * author: hedianbo.
 * date: 2019-12-26
 * desc:
 */
public class CourseViewModel extends ViewModel {

    public final MutableLiveData<ArrayList<CourseListBean>> mutableLiveData = new MutableLiveData<>();


    public void getDatas() {

        OkGo.<String>get(BaseUrl.baseUrl+"courses")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("hedb", "onSuccess: "+response.body() );
                        Gson gson = new Gson();
                    ArrayList<CourseListBean> list = gson.fromJson(response.body(), new TypeToken<List<CourseListBean>>() {}.getType());
                    mutableLiveData.postValue(list);
                    }
                });
    }

}
