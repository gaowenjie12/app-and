package com.zsoe.businesssharing.business.attention;


import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.ItemEventBean;
import com.zsoe.businesssharing.bean.RootEventBean;
import com.zsoe.businesssharing.bean.SlideBean;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;
import com.zsoe.businesssharing.utils.EmptyUtil;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class EventPresenter extends BasePresenter<TheEventFragment> {
    final public int REQUEST_LOGIN = 1;
    FormBody body;

    //用来维持页码
    public OpenLoadMoreDefault<ItemEventBean> loadMoreDefault;


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);


        restartableFirst(REQUEST_LOGIN,
                new Func0<Observable<RootResponse<RootEventBean>>>() {
                    @Override
                    public Observable<RootResponse<RootEventBean>> call() {

                        return DApplication.getServerAPI().attentionEvent(body);
                    }
                },

                new NetCallBack<TheEventFragment, RootEventBean>() {
                    @Override
                    public void callBack(TheEventFragment v, RootEventBean rootEventBean) {

                        List<SlideBean> slide = rootEventBean.getSlide();
                        if (!EmptyUtil.isEmpty(slide)) {
                            v.setSlide(slide);
                        }
                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(rootEventBean.getList());
                        v.updateList();
                    }
                }
                , new BaseToastNetError<TheEventFragment>() {
                    @Override
                    public void call(TheEventFragment v, Throwable throwable) {
                        super.call(v, throwable);
                        ToastUtils.showShort(throwable.getMessage());
                    }
                });


    }

    public void attentionEvent(String is_hasslide) {
        loadMoreDefault.pagerAble.put("is_hasslide", is_hasslide);
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }


}
