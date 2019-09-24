package com.zsoe.businesssharing.business.me;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ItemInsdustry;
import com.zsoe.businesssharing.business.login.LoginUser;

import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class BasicInformationPresenter extends BasePresenter<BasicInformationActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<List<ItemInsdustry>>>>() {
                    @Override
                    public Observable<RootResponse<List<ItemInsdustry>>> call() {

                        return DApplication.getServerAPI().service_station(body);
                    }
                },
                new NetCallBack<BasicInformationActivity, List<ItemInsdustry>>() {
                    @Override
                    public void callBack(BasicInformationActivity v, List<ItemInsdustry> noticeBeanList) {
                        v.serviceStation(noticeBeanList);
                    }
                },
                new BaseToastNetError<BasicInformationActivity>());


        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse<LoginUser>>>() {
                    @Override
                    public Observable<RootResponse<LoginUser>> call() {

                        return DApplication.getServerAPI().userProfile(body);
                    }
                },

                new NetCallBack<BasicInformationActivity, LoginUser>() {
                    @Override
                    public void callBack(BasicInformationActivity v, LoginUser loginUser) {
                        v.userProfileSuccess(loginUser);
                    }

                    @Override
                    public void callBackServerError(BasicInformationActivity v, RootResponse t) {
                        super.callBackServerError(v, t);

                    }
                },
                new BaseToastNetError<BasicInformationActivity>());


    }

    public void service_station() {
        HashMap<String, String> map = new HashMap<>();
        body = signForm(map);
        start(REQUEST_LOGIN);

    }

    public void userProfile(String realname, String avatar, String nickname, String gender, String birthday, String mobile, String email, String district
            , String serviceid, String companylocation, String industry_pcate, String industry_ccate) {
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        map.put("realname", realname);
        map.put("avatar", avatar);
        map.put("nickname", nickname);
        map.put("gender", gender);
        map.put("birthday", birthday);
        map.put("mobile", mobile);
        map.put("email", email);
        map.put("district", district);
        map.put("serviceid", serviceid);
        map.put("companylocation", companylocation);
        map.put("industry_pcate", industry_pcate);
        map.put("industry_ccate", industry_ccate);
        body = signForm(map);
        start(REQUEST_LOGIN2);

    }


}
