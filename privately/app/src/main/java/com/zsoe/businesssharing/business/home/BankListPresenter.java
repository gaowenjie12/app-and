package com.zsoe.businesssharing.business.home;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ItemBankBean;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class BankListPresenter extends BasePresenter<YinHangXinDaiFragment> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<ItemBankBean> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<ItemBankBean>>>>() {
                    @Override
                    public Observable<RootResponse<List<ItemBankBean>>> call() {

                        return DApplication.getServerAPI().loan_list(body);
                    }
                },
                new NetCallBack<YinHangXinDaiFragment, List<ItemBankBean>>() {
                    @Override
                    public void callBack(YinHangXinDaiFragment v, List<ItemBankBean> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<YinHangXinDaiFragment>());


    }

    public void loan_list(String shopId, String sourcepage) {
        loadMoreDefault.pagerAble.put("shopid", shopId);
        loadMoreDefault.pagerAble.put("sourcepage", sourcepage);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
