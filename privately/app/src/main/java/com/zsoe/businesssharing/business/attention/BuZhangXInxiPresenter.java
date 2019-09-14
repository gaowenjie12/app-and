package com.zsoe.businesssharing.business.attention;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;
import com.zsoe.businesssharing.bean.ItemBuZhangXinxBean;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class BuZhangXInxiPresenter extends BasePresenter<BuZhangXinxiActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;

    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<ItemBuZhangXinxBean> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<ItemBuZhangXinxBean>>>>() {
                    @Override
                    public Observable<RootResponse<List<ItemBuZhangXinxBean>>> call() {

                        return DApplication.getServerAPI().minister_list(body);
                    }
                },
                new NetCallBack<BuZhangXinxiActivity, List<ItemBuZhangXinxBean>>() {
                    @Override
                    public void callBack(BuZhangXinxiActivity v, List<ItemBuZhangXinxBean> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<BuZhangXinxiActivity>());


        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().save_mailbox_msg(body);
                    }
                },
                new NetCompleteBack<BuZhangXinxiActivity>() {
                    @Override
                    public void onComplete(BuZhangXinxiActivity v, RootResponse t) {
                        v.setMsgSuccess(t);
                    }
                }
                , new BaseToastNetError<BuZhangXinxiActivity>() {
                    @Override
                    public void call(BuZhangXinxiActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void minister_list(String type) {
        loadMoreDefault.pagerAble.put("type", type);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }

    public void save_mailbox_msg(String mid, String uid, String msg) {

        HashMap<String, String> params = new HashMap<>();

        params.put("mid", mid);
        params.put("uid", uid);
        params.put("msg", msg);
        params.put("type", "2");

        body = signForm(params);
        start(REQUEST_LOGIN2);

    }
}
