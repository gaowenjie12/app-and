package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.IndustryRoot;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class IndustryPresenter extends BasePresenter<IndustryActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<IndustryRoot>>>() {
                    @Override
                    public Observable<RootResponse<IndustryRoot>> call() {

                        return DApplication.getServerAPI().industry_info(body);
                    }
                },

                new NetCallBack<IndustryActivity, IndustryRoot>() {
                    @Override
                    public void callBack(IndustryActivity v, IndustryRoot homeBean) {
                        v.setData(homeBean);
                    }
                }
                , new BaseToastNetError<IndustryActivity>() {
                    @Override
                    public void call(IndustryActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void industry_info(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        body = signForm(params);
        start(REQUEST_LOGIN);

    }


}
