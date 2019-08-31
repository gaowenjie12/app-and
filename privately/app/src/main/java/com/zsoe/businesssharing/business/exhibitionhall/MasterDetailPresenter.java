package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.MasterBean;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class MasterDetailPresenter extends BasePresenter<MasterDetailActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<MasterBean>>>() {
                    @Override
                    public Observable<RootResponse<MasterBean>> call() {
                        return DApplication.getServerAPI().industry_person_info(body);
                    }
                },

                new NetCallBack<MasterDetailActivity, MasterBean>() {
                    @Override
                    public void callBack(MasterDetailActivity v, MasterBean homeBean) {
                        v.setData(homeBean);
                    }
                }
                , new BaseToastNetError<MasterDetailActivity>() {
                    @Override
                    public void call(MasterDetailActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void industry_person_info(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        body = signForm(params);
        start(REQUEST_LOGIN);

    }


}
