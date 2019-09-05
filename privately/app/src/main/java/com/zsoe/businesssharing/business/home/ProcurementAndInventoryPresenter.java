package com.zsoe.businesssharing.business.home;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.StockBean;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class ProcurementAndInventoryPresenter extends BasePresenter<ProcurementAndInventoryActivity> {
    final public int REQUEST_LOGIN = 1;
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
                new NetCallBack<ProcurementAndInventoryActivity, List<StockBean>>() {
                    @Override
                    public void callBack(ProcurementAndInventoryActivity v, List<StockBean> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<ProcurementAndInventoryActivity>());


    }

    public void stock_purchase_list(String icate,String pcate,String rtype,String ptype) {

        loadMoreDefault.pagerAble.put("icate",icate);
        loadMoreDefault.pagerAble.put("pcate",pcate);
        loadMoreDefault.pagerAble.put("rtype",rtype);
        loadMoreDefault.pagerAble.put("ptype",ptype);

        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
