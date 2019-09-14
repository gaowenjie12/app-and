package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;
import com.zsoe.businesssharing.bean.CompanyInfo;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class CompanyInfoPresenter extends BasePresenter<CompanyProfilesActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;

    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<CompanyInfo>>>() {
                    @Override
                    public Observable<RootResponse<CompanyInfo>> call() {

                        return DApplication.getServerAPI().company_info(body);
                    }
                },

                new NetCallBack<CompanyProfilesActivity, CompanyInfo>() {
                    @Override
                    public void callBack(CompanyProfilesActivity v, CompanyInfo homeBean) {
                        v.setData(homeBean);
                    }
                }
                , new BaseToastNetError<CompanyProfilesActivity>() {
                    @Override
                    public void call(CompanyProfilesActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().save_mailbox_msg(body);
                    }
                },
                new NetCompleteBack<CompanyProfilesActivity>() {
                    @Override
                    public void onComplete(CompanyProfilesActivity v, RootResponse t) {
                        v.setMsgSuccess(t);
                    }
                }
                , new BaseToastNetError<CompanyProfilesActivity>() {
                    @Override
                    public void call(CompanyProfilesActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });

    }

    public void company_info(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        body = signForm(params);
        start(REQUEST_LOGIN);

    }

    public void save_mailbox_msg(String name, String tel, String mid,String uid, String msg) {

        HashMap<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("msg", msg);
        params.put("type", "1");
        params.put("tel", tel);
        params.put("name", name);
        params.put("mid", mid);

        body = signForm(params);
        start(REQUEST_LOGIN2);

    }

}
