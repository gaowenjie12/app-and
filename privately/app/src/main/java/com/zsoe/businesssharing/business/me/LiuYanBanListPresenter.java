package com.zsoe.businesssharing.business.me;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.MessageReturnBean;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class LiuYanBanListPresenter extends BasePresenter<LiuYanBanActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<MessageReturnBean> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<MessageReturnBean>>>>() {
                    @Override
                    public Observable<RootResponse<List<MessageReturnBean>>> call() {

                        return DApplication.getServerAPI().user_companymsg_list(body);
                    }
                },
                new NetCallBack<LiuYanBanActivity, List<MessageReturnBean>>() {
                    @Override
                    public void callBack(LiuYanBanActivity v, List<MessageReturnBean> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<LiuYanBanActivity>());


    }

    public void user_companymsg_list(String uid) {
        loadMoreDefault.pagerAble.put("uid", uid);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
