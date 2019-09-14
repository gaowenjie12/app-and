package com.zsoe.businesssharing.business.attention;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;
import com.zsoe.businesssharing.bean.XinXiBena;

import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class MailPresenter extends BasePresenter<MailFragment> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<XinXiBena>>>>() {
                    @Override
                    public Observable<RootResponse<List<XinXiBena>>> call() {

                        return DApplication.getServerAPI().attentionMailbox(body);
                    }
                },

                new NetCallBack<MailFragment, List<XinXiBena>>() {
                    @Override
                    public void callBack(MailFragment v, List<XinXiBena> xinXiBenas) {
                        v.setData(xinXiBenas);
                    }
                }
                , new BaseToastNetError<MailFragment>() {
                    @Override
                    public void call(MailFragment v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().save_mailbox_msg(body);
                    }
                },
                new NetCompleteBack<MailFragment>() {
                    @Override
                    public void onComplete(MailFragment v, RootResponse t) {
                        v.setMsgSuccess(t);
                    }
                }
                , new BaseToastNetError<MailFragment>() {
                    @Override
                    public void call(MailFragment v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void attentionMailbox() {
        HashMap<String, String> params = new HashMap<>();
        body = signForm(params);
        start(REQUEST_LOGIN);

    }

    public void save_mailbox_msg(String mid, String uid, String msg) {

        HashMap<String, String> params = new HashMap<>();

        params.put("mid", mid);
        params.put("uid", uid);
        params.put("msg", msg);
        params.put("type", "2");

        body = signForm(params);
        start(REQUEST_LOGIN2);

    }


}
