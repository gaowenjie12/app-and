package com.zsoe.businesssharing.business.home;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.SearchBean;

import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class SearchListPresenter extends BasePresenter<SearchListActivity> {
    final public int REQUEST_LOGIN3 = 3;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN3,
                new Func0<Observable<RootResponse<List<SearchBean>>>>() {
                    @Override
                    public Observable<RootResponse<List<SearchBean>>> call() {

                        return DApplication.getServerAPI().search(body);
                    }
                },

                new NetCallBack<SearchListActivity, List<SearchBean>>() {
                    @Override
                    public void callBack(SearchListActivity v, List<SearchBean> searchBeanList) {
                        v.searchSuccess(searchBeanList);
                    }
                }

                , new BaseToastNetError<SearchListActivity>() {
                    @Override
                    public void call(SearchListActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }


    public void search(String type, String keywords) {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        params.put("type", type);
        params.put("keywords", keywords);
        body = signForm(params);
        start(REQUEST_LOGIN3);

    }


}
