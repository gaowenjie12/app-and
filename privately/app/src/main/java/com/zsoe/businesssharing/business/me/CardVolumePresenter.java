package com.zsoe.businesssharing.business.me;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;
import com.zsoe.businesssharing.bean.ItemInsdustry;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class CardVolumePresenter extends BasePresenter<CardVolumeListActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;
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

                        return DApplication.getServerAPI().mycardticket_list(body);
                    }
                },
                new NetCallBack<CardVolumeListActivity, List<ItemInsdustry>>() {
                    @Override
                    public void callBack(CardVolumeListActivity v, List<ItemInsdustry> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<CardVolumeListActivity>());

        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().mycardticket_del(body);
                    }
                },
                new NetCompleteBack<CardVolumeListActivity>() {
                    @Override
                    public void onComplete(CardVolumeListActivity v, RootResponse t) {
                        v.delSuccess();
                    }
                },
                new BaseToastNetError<CardVolumeListActivity>());


    }

    public void mycardticket_list(String uid) {
        loadMoreDefault.pagerAble.put("uid", uid);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


    public void mycardticket_del(String uid, String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("id", id);
        body = signForm(map);
        start(REQUEST_LOGIN2);
    }
}
