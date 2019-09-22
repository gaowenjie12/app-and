package com.zsoe.businesssharing.business.me;


import android.os.Bundle;
import android.text.TextUtils;

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


public class MessageRemindListPresenter extends BasePresenter<MessageRemindActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<MessageReturnBean> loadMoreDefault;
    public OpenLoadMoreDefault<MessageReturnBean> loadMoreDefault2;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<MessageReturnBean>>>>() {
                    @Override
                    public Observable<RootResponse<List<MessageReturnBean>>> call() {

                        return DApplication.getServerAPI().mailbox_list(body);
                    }
                },
                new NetCallBack<MessageRemindActivity, List<MessageReturnBean>>() {
                    @Override
                    public void callBack(MessageRemindActivity v, List<MessageReturnBean> noticeBeanList) {

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
                new BaseToastNetError<MessageRemindActivity>());


    }


    String keyword;


    public void mailbox_list(String uid, String keyword) {

        this.keyword = keyword;


        if (TextUtils.isEmpty(keyword)) {
            loadMoreDefault.pagerAble.put("uid", uid);
            body = signForm(loadMoreDefault.pagerAble);
        } else {
            loadMoreDefault2.pagerAble.put("uid", uid);
            loadMoreDefault2.pagerAble.put("keywords", keyword);
            body = signForm(loadMoreDefault2.pagerAble);
        }

        start(REQUEST_LOGIN);

    }


}
