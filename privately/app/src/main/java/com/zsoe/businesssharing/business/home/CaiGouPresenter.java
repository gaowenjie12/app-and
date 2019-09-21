package com.zsoe.businesssharing.business.home;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.CaiGouDetail;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class CaiGouPresenter extends BasePresenter<ProcurementAndInventoryDetailActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<CaiGouDetail>>>() {
                    @Override
                    public Observable<RootResponse<CaiGouDetail>> call() {

                        return DApplication.getServerAPI().stock_purchase_info(body);
                    }
                },

                new NetCallBack<ProcurementAndInventoryDetailActivity, CaiGouDetail>() {
                    @Override
                    public void callBack(ProcurementAndInventoryDetailActivity v, CaiGouDetail homeBean) {
                        v.setDate(homeBean);
                    }
                }
                , new BaseToastNetError<ProcurementAndInventoryDetailActivity>() {
                    @Override
                    public void call(ProcurementAndInventoryDetailActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void stock_purchase_info(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        body = signForm(params);
        start(REQUEST_LOGIN);

    }


}
