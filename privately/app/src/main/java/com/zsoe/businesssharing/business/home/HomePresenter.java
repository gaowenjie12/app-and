package com.zsoe.businesssharing.business.home;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.HomeBean;
import com.zsoe.businesssharing.business.main.HomeFragment;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class HomePresenter extends BasePresenter<HomeFragment> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<HomeBean>>>() {
                    @Override
                    public Observable<RootResponse<HomeBean>> call() {

                        return DApplication.getServerAPI().index(body);
                    }
                },

                new NetCallBack<HomeFragment, HomeBean>() {
                    @Override
                    public void callBack(HomeFragment v, HomeBean homeBean) {
                        v.setDate(homeBean);
                    }
                }
                , new BaseToastNetError<HomeFragment>(){
                    @Override
                    public void call(HomeFragment v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void home() {
        HashMap<String, String> params = new HashMap<>();
        body = signForm(params);
        start(REQUEST_LOGIN);

    }


}
