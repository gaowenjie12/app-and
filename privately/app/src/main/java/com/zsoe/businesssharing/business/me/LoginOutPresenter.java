package com.zsoe.businesssharing.business.me;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;
import com.zsoe.businesssharing.bean.VersionBean;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class LoginOutPresenter extends BasePresenter<SetUpActivity> {
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


        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse<VersionBean>>>() {
                    @Override
                    public Observable<RootResponse<VersionBean>> call() {

                        return DApplication.getServerAPI().check_version(body);
                    }
                },
                new NetCallBack<SetUpActivity, VersionBean>() {
                    @Override
                    public void callBack(SetUpActivity v, VersionBean versionBean) {
                        v.setDate(versionBean);
                    }
                },
                new BaseToastNetError<SetUpActivity>());


    }

    public void logout() {
        HashMap<String, String> map = new HashMap<>();
        body = signForm(map);
        start(REQUEST_LOGIN);

    }


    public void check_version() {
        HashMap<String, String> map = new HashMap<>();
        body = signForm(map);
        start(REQUEST_LOGIN2);

    }


}
