package com.zsoe.businesssharing.business.attention;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ItemBuZhangXinxBean;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class BuZhangXInxiPresenter extends BasePresenter<BuZhangXinxiActivity> {
    final public int REQUEST_LOGIN = 1;
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


    }

    public void minister_list() {
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
