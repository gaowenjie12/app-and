package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ProductDetail;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class ProductInfoPresenter extends BasePresenter<ProductDetailActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<ProductDetail>>>() {
                    @Override
                    public Observable<RootResponse<ProductDetail>> call() {

                        return DApplication.getServerAPI().product_info(body);
                    }
                },

                new NetCallBack<ProductDetailActivity, ProductDetail>() {
                    @Override
                    public void callBack(ProductDetailActivity v, ProductDetail homeBean) {
                        v.setData(homeBean);
                    }
                }
                , new BaseToastNetError<ProductDetailActivity>() {
                    @Override
                    public void call(ProductDetailActivity v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void product_info(String id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        body = signForm(params);
        start(REQUEST_LOGIN);

    }


}
