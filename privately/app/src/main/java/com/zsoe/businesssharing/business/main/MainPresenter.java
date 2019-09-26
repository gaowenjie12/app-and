package com.zsoe.businesssharing.business.main;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.FancyUtils;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.GongYouBean;
import com.zsoe.businesssharing.bean.VersionBean;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class MainPresenter extends BasePresenter<MainActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<GongYouBean>>>() {
                    @Override
                    public Observable<RootResponse<GongYouBean>> call() {

                        return DApplication.getServerAPI().industry_allcate(body);
                    }
                },

                new NetCallBack<MainActivity,GongYouBean>() {
                    @Override
                    public void callBack(MainActivity v, GongYouBean o) {
                        FancyUtils.setRootHangYe(o);
                    }
                },
                new BaseToastNetError<MainActivity>());



        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse<VersionBean>>>() {
                    @Override
                    public Observable<RootResponse<VersionBean>> call() {

                        return DApplication.getServerAPI().check_version(body);
                    }
                },
                new NetCallBack<MainActivity, VersionBean>() {
                    @Override
                    public void callBack(MainActivity v, VersionBean versionBean) {
                        v.setDate(versionBean);
                    }
                },
                new BaseToastNetError<MainActivity>());
    }


    public void industry_allcate() {
        HashMap<String, String> params = new HashMap<>();
        body = signForm(params);
        start(REQUEST_LOGIN);

    }


    public void check_version() {
        HashMap<String, String> map = new HashMap<>();
        body = signForm(map);
        start(REQUEST_LOGIN2);

    }
}
