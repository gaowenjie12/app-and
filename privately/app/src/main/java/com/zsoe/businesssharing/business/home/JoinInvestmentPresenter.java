package com.zsoe.businesssharing.business.home;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ItemJoinInvestmentBean;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class JoinInvestmentPresenter extends BasePresenter<JoinInvestmentActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<ItemJoinInvestmentBean> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<ItemJoinInvestmentBean>>>>() {
                    @Override
                    public Observable<RootResponse<List<ItemJoinInvestmentBean>>> call() {

                        return DApplication.getServerAPI().join_merchant_list(body);
                    }
                },
                new NetCallBack<JoinInvestmentActivity, List<ItemJoinInvestmentBean>>() {
                    @Override
                    public void callBack(JoinInvestmentActivity v, List<ItemJoinInvestmentBean> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<JoinInvestmentActivity>());


    }

    public void join_merchant_list() {
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
