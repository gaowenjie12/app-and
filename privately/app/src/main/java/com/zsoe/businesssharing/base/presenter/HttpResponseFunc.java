package com.zsoe.businesssharing.base.presenter;


import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;

import rx.functions.Func1;

/**
 * Created by shiyenba163.com on 16/11/9.
 */

public class HttpResponseFunc<T> implements Func1<RootResponse<T>, T> {

    @Override
    public T call(RootResponse<T> httpResult) {

        int status = httpResult.getCode();

        if (status == 1) {
            return httpResult.getData();
        } else if (status == 401) {
            //登录失效，被踢出
            ToastUtils.showShort(httpResult.getMsg());
            DApplication.getInstance().exit();
            DApplication.getInstance().startLogin();
        } else {
            Toast.makeText(DApplication.getInstance(), httpResult.getMsg(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}

