package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;
import android.text.TextUtils;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ChanPinBeanItem;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class ProductListPresenter extends BasePresenter<ProductListActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<ChanPinBeanItem> loadMoreDefault;
    public OpenLoadMoreDefault<ChanPinBeanItem> loadMoreDefault2;

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
                new NetCallBack<ProductListActivity, List<ChanPinBeanItem>>() {
                    @Override
                    public void callBack(ProductListActivity v, List<ChanPinBeanItem> noticeBeanList) {

                        if (TextUtils.isEmpty(keyword)) {
                            loadMoreDefault.fixNumAndClear();
                            loadMoreDefault.loadMoreFinish(noticeBeanList);
                            v.updateList();
                        } else {
                            loadMoreDefault2.fixNumAndClear();
                            loadMoreDefault2.loadMoreFinish(noticeBeanList);
                            v.updateList2();
                        }
                    }
                },
                new BaseToastNetError<ProductListActivity>());


    }

    String keyword;

    public void product_list(String keyword, String type,String industryId,String shopid,String uid) {
        this.keyword = keyword;


        if (TextUtils.isEmpty(keyword)) {
            loadMoreDefault.pagerAble.put("type", type);
            loadMoreDefault.pagerAble.put("ccateid", industryId);
            loadMoreDefault.pagerAble.put("shopid", shopid);
            loadMoreDefault.pagerAble.put("uid", DApplication.getInstance().getLoginUser().getId()+"");
            body = signForm(loadMoreDefault.pagerAble);
        } else {
            loadMoreDefault2.pagerAble.put("type", type);
            loadMoreDefault2.pagerAble.put("ccateid", industryId);
            loadMoreDefault2.pagerAble.put("shopid", shopid);
            loadMoreDefault2.pagerAble.put("uid", DApplication.getInstance().getLoginUser().getId()+"");
            loadMoreDefault2.pagerAble.put("keyword", keyword);
            body = signForm(loadMoreDefault2.pagerAble);
        }

        start(REQUEST_LOGIN);

    }


}
