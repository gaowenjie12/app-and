package com.zsoe.businesssharing.business.me;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;
import com.zsoe.businesssharing.bean.ItemMailBox;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class MessageReturnListPresenter extends BasePresenter<MessageReturnActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<ItemMailBox> loadMoreDefault;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<ItemMailBox>>>>() {
                    @Override
                    public Observable<RootResponse<List<ItemMailBox>>> call() {

                        return DApplication.getServerAPI().mailbox_infolist(body);
                    }
                },
                new NetCallBack<MessageReturnActivity, List<ItemMailBox>>() {
                    @Override
                    public void callBack(MessageReturnActivity v, List<ItemMailBox> noticeBeanList) {

                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(noticeBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<MessageReturnActivity>());


        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().mailbox_inforeply(body);
                    }
                },
                new NetCompleteBack<MessageReturnActivity>() {
                    @Override
                    public void onComplete(MessageReturnActivity v, RootResponse t) {
                        v.setMsgSuccess(t);
                    }
                },
                new BaseToastNetError<MessageReturnActivity>());


    }

    public void mailbox_infolist(String uid, String to_uid) {
        loadMoreDefault.pagerAble.put("uid", uid);
        loadMoreDefault.pagerAble.put("to_uid", to_uid);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }

    public void mailbox_inforeply(String uid, String to_uid, String msg_id, String msg) {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("to_uid", to_uid);
        map.put("msg_id", msg_id);
        map.put("msg", msg);
        body = signForm(map);
        start(REQUEST_LOGIN2);
    }


}
