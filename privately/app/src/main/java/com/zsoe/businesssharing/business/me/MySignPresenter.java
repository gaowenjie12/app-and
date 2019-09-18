package com.zsoe.businesssharing.business.me;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;
import com.zsoe.businesssharing.bean.MySignBean;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class MySignPresenter extends BasePresenter<MySignActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;
    final public int REQUEST_LOGIN3 = 3;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<MySignBean>>>() {
                    @Override
                    public Observable<RootResponse<MySignBean>> call() {

                        return DApplication.getServerAPI().mysign(body);
                    }
                },

                new NetCallBack<MySignActivity, MySignBean>() {
                    @Override
                    public void callBack(MySignActivity v, MySignBean mySignBean) {
                        v.setDate(mySignBean);
                    }
                },
                new BaseToastNetError<MySignActivity>());


        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().use_rankticket(body);
                    }
                },
                new NetCompleteBack<MySignActivity>() {
                    @Override
                    public void onComplete(MySignActivity v, RootResponse t) {
                        v.success(t);
                    }
                },
                new BaseToastNetError<MySignActivity>());

        restartableFirst(REQUEST_LOGIN3,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().sign(body);
                    }
                },
                new NetCompleteBack<MySignActivity>() {
                    @Override
                    public void onComplete(MySignActivity v, RootResponse t) {
                        v.success(t);
                    }
                },
                new BaseToastNetError<MySignActivity>());


    }


    public void mysign(String uid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", uid);
        body = signForm(params);
        start(REQUEST_LOGIN);

    }

    public void use_rankticket(String uid, String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("id", id);
        body = signForm(params);
        start(REQUEST_LOGIN2);

    }

    public void sign(String uid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", uid);
        body = signForm(params);
        start(REQUEST_LOGIN3);

    }

}
