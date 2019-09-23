package com.zsoe.businesssharing.business.message;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;
import com.zsoe.businesssharing.bean.HaoYouBean;

import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class HaoYouPresenter extends BasePresenter<HaoYouActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;

    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<HaoYouBean>>>>() {
                    @Override
                    public Observable<RootResponse<List<HaoYouBean>>> call() {

                        return DApplication.getServerAPI().myfriend_list(body);
                    }
                },
                new NetCallBack<HaoYouActivity, List<HaoYouBean>>() {
                    @Override
                    public void callBack(HaoYouActivity v, List<HaoYouBean> haoYouBeanList) {
                        v.getSuccess(haoYouBeanList);
                    }
                },
                new BaseToastNetError<HaoYouActivity>());


        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {
                        return DApplication.getServerAPI().remove_friend(body);
                    }
                },
                new NetCompleteBack<HaoYouActivity>() {
                    @Override
                    public void onComplete(HaoYouActivity v, RootResponse t) {
                        ToastUtils.showShort(t.getMsg());
                        v.deleteSuccess();
                    }
                },
                new BaseToastNetError<HaoYouActivity>());


    }

    public void myfriend_list() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        body = signForm(hashMap);
        start(REQUEST_LOGIN);
    }

    public void remove_friend(String uid, String friend_username) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("friend_username", friend_username);
        body = signForm(hashMap);
        start(REQUEST_LOGIN2);
    }

}
