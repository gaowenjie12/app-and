package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ItemCompany;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class CompanyListPresenter extends BasePresenter<CompaniesListActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<ItemCompany> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<ItemCompany>>>>() {
                    @Override
                    public Observable<RootResponse<List<ItemCompany>>> call() {

                        return DApplication.getServerAPI().company_list(body);
                    }
                },
                new NetCallBack<CompaniesListActivity, List<ItemCompany>>() {
                    @Override
                    public void callBack(CompaniesListActivity v, List<ItemCompany> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<CompaniesListActivity>());


    }

    public void company_list(String keyword) {
        loadMoreDefault.pagerAble.put("keyword", keyword);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
