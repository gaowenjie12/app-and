package com.zsoe.businesssharing.business.main;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.FancyUtils;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.RootHangYe;

import java.util.HashMap;
import java.util.List;

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
                new Func0<Observable<RootResponse<List<RootHangYe>>>>() {
                    @Override
                    public Observable<RootResponse<List<RootHangYe>>> call() {

                        return DApplication.getServerAPI().industry_allcate(body);
                    }
                },

                new NetCallBack<MainActivity, List<RootHangYe>>() {
                    @Override
                    public void callBack(MainActivity v, List<RootHangYe> o) {
                        FancyUtils.setRootHangYe(o);
                    }
                },
                new BaseToastNetError<MainActivity>());
    }


    public void industry_allcate() {
        HashMap<String, String> params = new HashMap<>();
        body = signForm(params);
        start(REQUEST_LOGIN);

    }

}
