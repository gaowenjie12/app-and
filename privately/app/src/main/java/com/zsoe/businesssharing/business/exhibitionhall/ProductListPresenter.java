package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class ProductListPresenter extends BasePresenter<NoticeActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<NoticeBean> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<NoticeBeanList>>>() {
                    @Override
                    public Observable<RootResponse<NoticeBeanList>> call() {

                        return DApplication.getServerAPI().messageList(body);
                    }
                },
                new NetCallBack<NoticeActivity, NoticeBeanList>() {
                    @Override
                    public void callBack(NoticeActivity v, NoticeBeanList noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList.getContent());
                        v.updateList();
                    }
                },
                new BaseToastNetError<NoticeActivity>());


    }

    public void messageList() {
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
