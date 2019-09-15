package com.zsoe.businesssharing.business.me;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ItemInsdustry;

import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class BasicInformationPresenter extends BasePresenter<BasicInformationActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<ItemInsdustry>>>>() {
                    @Override
                    public Observable<RootResponse<List<ItemInsdustry>>> call() {

                        return DApplication.getServerAPI().service_station(body);
                    }
                },
                new NetCallBack<BasicInformationActivity, List<ItemInsdustry>>() {
                    @Override
                    public void callBack(BasicInformationActivity v, List<ItemInsdustry> noticeBeanList) {
                        v.serviceStation(noticeBeanList);
                    }
                },
                new BaseToastNetError<BasicInformationActivity>());


    }

    public void service_station() {
        HashMap<String, String> map = new HashMap<>();
        body = signForm(map);
        start(REQUEST_LOGIN);

    }


}
