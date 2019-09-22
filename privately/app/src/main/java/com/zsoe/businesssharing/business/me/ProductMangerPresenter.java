package com.zsoe.businesssharing.business.me;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ChanPinBeanItem;

import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class ProductMangerPresenter extends BasePresenter<ProductManagementActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<ChanPinBeanItem>>>>() {
                    @Override
                    public Observable<RootResponse<List<ChanPinBeanItem>>> call() {

                        return DApplication.getServerAPI().product_list(body);
                    }
                },
                new NetCallBack<ProductManagementActivity, List<ChanPinBeanItem>>() {
                    @Override
                    public void callBack(ProductManagementActivity v, List<ChanPinBeanItem> noticeBeanList) {
                        v.setDate(noticeBeanList);
                    }
                },
                new BaseToastNetError<ProductManagementActivity>());


    }

    public void product_list() {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        map.put("shopid", DApplication.getInstance().getLoginUser().getShopid() + "");
        map.put("sourcepage", "my");
        body = signForm(map);
        start(REQUEST_LOGIN);

    }


}
