package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ChanPinBeanItem;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class ProductListPresenter extends BasePresenter<ProductListActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<ChanPinBeanItem> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<ChanPinBeanItem>>>>() {
                    @Override
                    public Observable<RootResponse<List<ChanPinBeanItem>>> call() {

                        return DApplication.getServerAPI().product_list(body);
                    }
                },
                new NetCallBack<ProductListActivity, List<ChanPinBeanItem>>() {
                    @Override
                    public void callBack(ProductListActivity v, List<ChanPinBeanItem> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<ProductListActivity>());


    }

    public void product_list(String keyword) {
        loadMoreDefault.pagerAble.put("keyword", keyword);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
