package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ItemInsdustry;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class CollectAllLookListPresenter extends BasePresenter<CollectAllLookListActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<ItemInsdustry> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<ItemInsdustry>>>>() {
                    @Override
                    public Observable<RootResponse<List<ItemInsdustry>>> call() {

                        return DApplication.getServerAPI().collect_infolist(body);
                    }
                },
                new NetCallBack<CollectAllLookListActivity, List<ItemInsdustry>>() {
                    @Override
                    public void callBack(CollectAllLookListActivity v, List<ItemInsdustry> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<CollectAllLookListActivity>());


    }

    public void collect_infolist(String uid, String type) {
        loadMoreDefault.pagerAble.put("uid", uid);
        loadMoreDefault.pagerAble.put("type", type);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
