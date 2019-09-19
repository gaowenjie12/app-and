package com.zsoe.businesssharing.business.login;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class ChangePwPresenter extends BasePresenter<ChangePwActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().getSms(body);
                    }
                },
                new NetCompleteBack<ChangePwActivity>() {
                    @Override
                    public void onComplete(ChangePwActivity v, RootResponse t) {
                            v.chenggong();
                    }
                },
                new BaseToastNetError<ChangePwActivity>());


        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().resetpwd(body);
                    }
                },
                new NetCompleteBack<ChangePwActivity>() {
                    @Override
                    public void onComplete(ChangePwActivity v, RootResponse t) {

                        v.toLogin();

                    }
                },
                new BaseToastNetError<ChangePwActivity>());


    }


    public void getSms(String mobile, String event) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("event", event);
        body = signForm(params);
        start(REQUEST_LOGIN);

    }

    public void resetpwd(String mobile, String newpassword) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("newpassword", newpassword);
        body = signForm(params);
        start(REQUEST_LOGIN2);

    }


}
