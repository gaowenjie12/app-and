package com.zsoe.businesssharing.business.home;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.HomeBean;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class SearchPresenter extends BasePresenter<SearchActivity> {
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

                new NetCallBack<SearchActivity, HomeBean>() {
                    @Override
                    public void callBack(SearchActivity v, HomeBean homeBean) {
//                        v.setDate(homeBean);
                    }
                }
                , new BaseToastNetError<SearchActivity>() {
                    @Override
                    public void call(SearchActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void home() {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        body = signForm(params);
        start(REQUEST_LOGIN);

    }


}
