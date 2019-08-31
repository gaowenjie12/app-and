package com.zsoe.businesssharing.business.exhibitionhall;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.GalleryRoomBean;
import com.zsoe.businesssharing.business.main.GalleryRoomFragment;

import java.util.HashMap;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class GalleryRoomPresenter extends BasePresenter<GalleryRoomFragment> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<GalleryRoomBean>>>() {
                    @Override
                    public Observable<RootResponse<GalleryRoomBean>> call() {

                        return DApplication.getServerAPI().hallIndex(body);
                    }
                },

                new NetCallBack<GalleryRoomFragment, GalleryRoomBean>() {
                    @Override
                    public void callBack(GalleryRoomFragment v, GalleryRoomBean homeBean) {
                        v.setData(homeBean);
                    }
                }
                , new BaseToastNetError<GalleryRoomFragment>(){
                    @Override
                    public void call(GalleryRoomFragment v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void hallIndex() {
        HashMap<String, String> params = new HashMap<>();
        body = signForm(params);
        start(REQUEST_LOGIN);

    }


}
