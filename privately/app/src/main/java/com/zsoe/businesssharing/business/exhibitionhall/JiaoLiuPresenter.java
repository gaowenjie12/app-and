package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.DetailJiaoLiuBean;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class JiaoLiuPresenter extends BasePresenter<EventDetailsActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<DetailJiaoLiuBean>>>() {
                    @Override
                    public Observable<RootResponse<DetailJiaoLiuBean>> call() {

                        return DApplication.getServerAPI().communication_info(body);
                    }
                },

                new NetCallBack<EventDetailsActivity, DetailJiaoLiuBean>() {
                    @Override
                    public void callBack(EventDetailsActivity v, DetailJiaoLiuBean homeBean) {
                        v.setData(homeBean);
                    }
                }
                , new BaseToastNetError<EventDetailsActivity>() {
                    @Override
                    public void call(EventDetailsActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void communication_info(String id,String uid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("uid", uid);
        body = signForm(params);
        start(REQUEST_LOGIN);

    }


}
