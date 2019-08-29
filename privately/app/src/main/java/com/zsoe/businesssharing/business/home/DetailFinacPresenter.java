package com.zsoe.businesssharing.business.home;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.DetailFinanceBean;
import com.zsoe.businesssharing.bean.FileDownBean;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class DetailFinacPresenter extends BasePresenter<FinancingLoansDetailActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<DetailFinanceBean>>>() {
                    @Override
                    public Observable<RootResponse<DetailFinanceBean>> call() {

                        return DApplication.getServerAPI().finance_info(body);
                    }
                },

                new NetCallBack<FinancingLoansDetailActivity, DetailFinanceBean>() {
                    @Override
                    public void callBack(FinancingLoansDetailActivity v, DetailFinanceBean homeBean) {
                        v.setDate(homeBean);
                    }
                }
                , new BaseToastNetError<FinancingLoansDetailActivity>() {
                    @Override
                    public void call(FinancingLoansDetailActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse<FileDownBean>>>() {
                    @Override
                    public Observable<RootResponse<FileDownBean>> call() {

                        return DApplication.getServerAPI().finance_file_download(body);
                    }
                },

                new NetCallBack<FinancingLoansDetailActivity, FileDownBean>() {
                    @Override
                    public void callBack(FinancingLoansDetailActivity v, FileDownBean homeBean) {
                        v.getFile(homeBean);
                    }
                }
                , new BaseToastNetError<FinancingLoansDetailActivity>() {
                    @Override
                    public void call(FinancingLoansDetailActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void finance_info(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        body = signForm(params);
        start(REQUEST_LOGIN);

    }

    public void finance_file_download(String id, String name, String mobile) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        params.put("mobile", mobile);
        body = signForm(params);
        start(REQUEST_LOGIN2);

    }


}
