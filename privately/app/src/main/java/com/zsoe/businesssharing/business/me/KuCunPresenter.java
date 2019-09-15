package com.zsoe.businesssharing.business.me;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;
import com.zsoe.businesssharing.bean.StockBean;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class KuCunPresenter extends BasePresenter<ProcurementManagementActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<StockBean> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<StockBean>>>>() {
                    @Override
                    public Observable<RootResponse<List<StockBean>>> call() {

                        return DApplication.getServerAPI().stock_purchase_list(body);
                    }
                },
                new NetCallBack<ProcurementManagementActivity, List<StockBean>>() {
                    @Override
                    public void callBack(ProcurementManagementActivity v, List<StockBean> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<ProcurementManagementActivity>());

        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().stockpurchase_del(body);
                    }
                },
                new NetCompleteBack<ProcurementManagementActivity>() {
                    @Override
                    public void onComplete(ProcurementManagementActivity v, RootResponse t) {
                        v.delSuccess();
                    }
                },
                new BaseToastNetError<ProcurementManagementActivity>());


    }

    public void stock_purchase_list(String uid, String type) {

        loadMoreDefault.pagerAble.put("uid", uid);
        loadMoreDefault.pagerAble.put("type", type);

        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


    public void stockpurchase_del(String uid, String id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("id", id);
        body = signForm(map);
        start(REQUEST_LOGIN2);
    }
}
