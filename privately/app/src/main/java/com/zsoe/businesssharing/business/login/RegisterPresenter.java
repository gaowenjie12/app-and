package com.zsoe.businesssharing.business.login;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class RegisterPresenter extends BasePresenter<RegisterActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;
    final public int REQUEST_LOGIN3 = 3;
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
                new NetCompleteBack<RegisterActivity>() {
                    @Override
                    public void onComplete(RegisterActivity v, RootResponse t) {
                            v.chenggong();
                    }
                },
                new BaseToastNetError<RegisterActivity>());


        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().register(body);
                    }
                },
                new NetCompleteBack<RegisterActivity>() {
                    @Override
                    public void onComplete(RegisterActivity v, RootResponse t) {

                        v.toLogin();

                    }
                },
                new BaseToastNetError<RegisterActivity>());


        restartableFirst(REQUEST_LOGIN3,
                new Func0<Observable<RootResponse<LoginUser>>>() {
                    @Override
                    public Observable<RootResponse<LoginUser>> call() {

                        return DApplication.getServerAPI().third(body);
                    }
                },

                new NetCallBack<RegisterActivity, LoginUser>() {
                    @Override
                    public void callBack(RegisterActivity v, LoginUser o) {
                        v.loginSuccess(o);
                    }
                },
                new BaseToastNetError<RegisterActivity>());


    }


    public void getSms(String mobile, String event) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("event", event);
        body = signForm(params);
        start(REQUEST_LOGIN);

    }

    public void register(String mobile, String captcha, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("captcha", captcha);
        params.put("password", password);
        body = signForm(params);
        start(REQUEST_LOGIN2);

    }

    public void third(String platform, String thirduid, String thirdname, String avatar) {
        HashMap<String, String> params = new HashMap<>();
        params.put("platform", platform);
        params.put("thirduid", thirduid);
        params.put("thirdname", thirdname);
        params.put("avatar", avatar);
        body = signForm(params);
        start(REQUEST_LOGIN3);
    }
}
