package com.zsoe.businesssharing.business.home;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.DetailJoinInvestmentBean;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class DetailJoinInvestmentPresenter extends BasePresenter<JoinInvestmentDetailActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<DetailJoinInvestmentBean>>>() {
                    @Override
                    public Observable<RootResponse<DetailJoinInvestmentBean>> call() {

                        return DApplication.getServerAPI().join_merchant_info(body);
                    }
                },

                new NetCallBack<JoinInvestmentDetailActivity, DetailJoinInvestmentBean>() {
                    @Override
                    public void callBack(JoinInvestmentDetailActivity v, DetailJoinInvestmentBean homeBean) {
                        v.setDate(homeBean);
                    }
                }
                , new BaseToastNetError<JoinInvestmentDetailActivity>() {
                    @Override
                    public void call(JoinInvestmentDetailActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void join_merchant_info(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        body = signForm(params);
        start(REQUEST_LOGIN);

    }


}
