package com.zsoe.businesssharing.business.me;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.base.presenter.NetCompleteBack;
import com.zsoe.businesssharing.bean.RenCompanyBean;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class RenzhengPresenter extends BasePresenter<CertificationCompanyActivity> {
    final public int REQUEST_LOGIN = 1;
    final public int REQUEST_LOGIN2 = 2;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<RenCompanyBean>>>() {
                    @Override
                    public Observable<RootResponse<RenCompanyBean>> call() {

                        return DApplication.getServerAPI().auth_companyinfo(body);
                    }
                },

                new NetCallBack<CertificationCompanyActivity, RenCompanyBean>() {
                    @Override
                    public void callBack(CertificationCompanyActivity v, RenCompanyBean renCompanyBean) {
                        v.getSuccess(renCompanyBean);
                    }
                },
                new BaseToastNetError<CertificationCompanyActivity>());


        restartableFirst(REQUEST_LOGIN2,
                new Func0<Observable<RootResponse>>() {
                    @Override
                    public Observable<RootResponse> call() {

                        return DApplication.getServerAPI().auth_company(body);
                    }
                },

                new NetCompleteBack<CertificationCompanyActivity>() {
                    @Override
                    public void onComplete(CertificationCompanyActivity v, RootResponse t) {
                        ToastUtils.showShort(t.getMsg());
                        v.finish();
                    }
                }
                ,
                new BaseToastNetError<CertificationCompanyActivity>());
    }


    public void auth_companyinfo(String uid) {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", uid);
        body = signForm(params);
        start(REQUEST_LOGIN);

    }

    public void auth_company(String uid, String companyname, String maincate, String idcardnum, String owner, String mobile, String email, String license, String marrlicense) {
        HashMap<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("companyname", companyname);
        params.put("maincate", maincate);
        params.put("idcardnum", idcardnum);
        params.put("owner", owner);
        params.put("mobile", mobile);
        params.put("email", email);
        params.put("license", license);
        params.put("marrlicense", marrlicense);
        body = signForm(params);
        start(REQUEST_LOGIN2);

    }
}
