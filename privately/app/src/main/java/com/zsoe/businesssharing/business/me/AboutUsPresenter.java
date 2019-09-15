package com.zsoe.businesssharing.business.me;


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


public class AboutUsPresenter extends BasePresenter<AboutUsActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<String>>>() {
                    @Override
                    public Observable<RootResponse<String>> call() {

                        return DApplication.getServerAPI().about_us(body);
                    }
                },
                new NetCallBack<AboutUsActivity, String>() {
                    @Override
                    public void callBack(AboutUsActivity v, String noticeBeanList) {
                        v.setDate(noticeBeanList);
                    }
                },
                new BaseToastNetError<AboutUsActivity>());


    }

    public void about_us() {
        HashMap<String, String> map = new HashMap<>();
        body = signForm(map);
        start(REQUEST_LOGIN);

    }


}
