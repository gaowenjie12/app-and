package com.zsoe.businesssharing.business.login;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class LoginPresenter extends BasePresenter<LoginActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<LoginUser>>>() {
                    @Override
                    public Observable<RootResponse<LoginUser>> call() {

                        return DApplication.getServerAPI().login(body);
                    }
                },

                new NetCallBack<LoginActivity, LoginUser>() {
                    @Override
                    public void callBack(LoginActivity v, LoginUser o) {
                        v.loginSuccess(o);
                    }
                },
                new BaseToastNetError<LoginActivity>());
    }


    public void login(String account, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("password", password);
        body = signForm(params);
        start(REQUEST_LOGIN);

    }

}
