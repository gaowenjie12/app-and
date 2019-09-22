package com.zsoe.businesssharing.business.home;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ItemFinancBean;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class FanancListPresenter extends BasePresenter<RongZiXiangMuFragment> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<ItemFinancBean> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<ItemFinancBean>>>>() {
                    @Override
                    public Observable<RootResponse<List<ItemFinancBean>>> call() {

                        return DApplication.getServerAPI().finance_list(body);
                    }
                },
                new NetCallBack<RongZiXiangMuFragment, List<ItemFinancBean>>() {
                    @Override
                    public void callBack(RongZiXiangMuFragment v, List<ItemFinancBean> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<RongZiXiangMuFragment>());


    }

    public void finance_list(String shopid,String sourcepage) {
        loadMoreDefault.pagerAble.put("shopid", shopid);
        loadMoreDefault.pagerAble.put("sourcepage", sourcepage);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
