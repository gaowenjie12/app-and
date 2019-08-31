package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.MasterBean;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class MasterListPresenter extends BasePresenter<MasterListActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<MasterBean> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<MasterBean>>>>() {
                    @Override
                    public Observable<RootResponse<List<MasterBean>>> call() {

                        return DApplication.getServerAPI().industry_person_list(body);
                    }
                },
                new NetCallBack<MasterListActivity, List<MasterBean>>() {
                    @Override
                    public void callBack(MasterListActivity v, List<MasterBean> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<MasterListActivity>());


    }

    public void industry_person_list(String type) {
        loadMoreDefault.pagerAble.put("type", type);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
