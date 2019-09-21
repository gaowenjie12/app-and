package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.JiaoLiuBean;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class TuiGuangListPresenter extends BasePresenter<TuiGuangActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<JiaoLiuBean> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<JiaoLiuBean>>>>() {
                    @Override
                    public Observable<RootResponse<List<JiaoLiuBean>>> call() {

                        return DApplication.getServerAPI().extenactivity_list(body);
                    }
                },
                new NetCallBack<TuiGuangActivity, List<JiaoLiuBean>>() {
                    @Override
                    public void callBack(TuiGuangActivity v, List<JiaoLiuBean> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<TuiGuangActivity>());


    }

    public void extenactivity_list(String shopid) {
        loadMoreDefault.pagerAble.put("shopid",shopid);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
