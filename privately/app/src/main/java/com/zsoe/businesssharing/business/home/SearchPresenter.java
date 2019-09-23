package com.zsoe.businesssharing.business.home;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;
import com.zsoe.businesssharing.bean.RootSearchBean;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class SearchPresenter extends BasePresenter<SearchActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<RootSearchBean>>>() {
                    @Override
                    public Observable<RootResponse<RootSearchBean>> call() {

                        return DApplication.getServerAPI().searchpage(body);
                    }
                },

                new NetCallBack<SearchActivity, RootSearchBean>() {
                    @Override
                    public void callBack(SearchActivity v, RootSearchBean rootSearchBean) {
                        v.setDate(rootSearchBean);
                    }
                }
                , new BaseToastNetError<SearchActivity>() {
                    @Override
                    public void call(SearchActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().historysearch_del(body);
                    }
                },

                new NetCompleteBack<SearchActivity>() {
                    @Override
                    public void onComplete(SearchActivity v, RootResponse t) {
                        v.delSuccess();
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

    public void searchpage() {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        body = signForm(params);
        start(REQUEST_LOGIN);

    }

    public void historysearch_del() {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        body = signForm(params);
        start(REQUEST_LOGIN2);

    }

}
