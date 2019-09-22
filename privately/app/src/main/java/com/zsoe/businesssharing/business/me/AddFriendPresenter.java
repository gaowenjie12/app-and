package com.zsoe.businesssharing.business.me;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class AddFriendPresenter extends BasePresenter<AddFriendActivity> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().add_friend(body);
                    }
                },
                new NetCompleteBack<AddFriendActivity>() {
                    @Override
                    public void onComplete(AddFriendActivity v, RootResponse t) {
                        ToastUtils.showShort(t.getMsg());
                        if (t.getCode() == 1) {
                            v.addSuccess();
                        }
                    }
                },
                new BaseToastNetError<AddFriendActivity>());


    }

    public void add_friend(String friend_name, String friend_username) {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        map.put("friend_name", friend_name);
        map.put("friend_username", friend_username);
        body = signForm(map);
        start(REQUEST_LOGIN);

    }


}
