package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ItemWenZhangBean;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class LatestNewsPresenter extends BasePresenter<LatestNewsActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<ItemWenZhangBean> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<ItemWenZhangBean>>>>() {
                    @Override
                    public Observable<RootResponse<List<ItemWenZhangBean>>> call() {

                        return DApplication.getServerAPI().article_list(body);
                    }
                },
                new NetCallBack<LatestNewsActivity, List<ItemWenZhangBean>>() {
                    @Override
                    public void callBack(LatestNewsActivity v, List<ItemWenZhangBean> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<LatestNewsActivity>());


    }

    public void article_list(String type,String daliangId) {
        loadMoreDefault.pagerAble.put("type", type);
        loadMoreDefault.pagerAble.put("daliang", daliangId);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
