package com.zsoe.businesssharing.business.attention;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.FenHuiBean;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class ArchitecturaCulturePresenter extends BasePresenter<ArchitecturalCultureFragment> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<FenHuiBean>>>() {
                    @Override
                    public Observable<RootResponse<FenHuiBean>> call() {

                        return DApplication.getServerAPI().attentionFrame(body);
                    }
                },

                new NetCallBack<ArchitecturalCultureFragment, FenHuiBean>() {
                    @Override
                    public void callBack(ArchitecturalCultureFragment v, FenHuiBean FenHuiBean) {
                        v.setDate(FenHuiBean);
                    }
                }
                , new BaseToastNetError<ArchitecturalCultureFragment>(){
                    @Override
                    public void call(ArchitecturalCultureFragment v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void attentionFrame() {
        HashMap<String, String> params = new HashMap<>();
        body = signForm(params);
        start(REQUEST_LOGIN);

    }


}
