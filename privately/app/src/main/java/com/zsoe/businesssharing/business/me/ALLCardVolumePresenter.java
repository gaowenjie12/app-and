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


public class ALLCardVolumePresenter extends BasePresenter<ALLCardVolumeListActivity> {
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

                        return DApplication.getServerAPI().cardticket_list(body);
                    }
                },
                new NetCallBack<ALLCardVolumeListActivity, List<ItemInsdustry>>() {
                    @Override
                    public void callBack(ALLCardVolumeListActivity v, List<ItemInsdustry> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<ALLCardVolumeListActivity>());

        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().getcardticket(body);
                    }
                },
                new NetCompleteBack<ALLCardVolumeListActivity>() {
                    @Override
                    public void onComplete(ALLCardVolumeListActivity v, RootResponse t) {
                        v.delSuccess();
                    }
                },
                new BaseToastNetError<ALLCardVolumeListActivity>());


    }

    public void cardticket_list(String uid) {
        loadMoreDefault.pagerAble.put("uid", uid);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


    public void getcardticket(String uid, String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("id", id);
        body = signForm(map);
        start(REQUEST_LOGIN2);
    }
}
