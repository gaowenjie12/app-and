package com.zsoe.businesssharing.business.me;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ItemCollectBean;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class CollectionListPresenter extends BasePresenter<CollectionListActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<ItemCollectBean>>>() {
                    @Override
                    public Observable<RootResponse<ItemCollectBean>> call() {

                        return DApplication.getServerAPI().collect_list(body);
                    }
                },
                new NetCallBack<CollectionListActivity, ItemCollectBean>() {
                    @Override
                    public void callBack(CollectionListActivity v, ItemCollectBean noticeBeanList) {
                        v.setDate(noticeBeanList);
                    }
                },
                new BaseToastNetError<CollectionListActivity>());


    }

    public void collect_list(String uid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", uid);
        body = signForm(map);
        start(REQUEST_LOGIN);

    }


}
