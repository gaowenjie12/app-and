package com.zsoe.businesssharing.business.me;


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


public class LoginOutPresenter extends BasePresenter<SetUpActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().logout(body);
                    }
                },
                new NetCompleteBack<SetUpActivity>() {
                    @Override
                    public void onComplete(SetUpActivity v, RootResponse t) {
                        v.loginOut();
                    }
                },
                new BaseToastNetError<SetUpActivity>());


    }

    public void logout() {
        HashMap<String, String> map = new HashMap<>();
        body = signForm(map);
        start(REQUEST_LOGIN);

    }


}
