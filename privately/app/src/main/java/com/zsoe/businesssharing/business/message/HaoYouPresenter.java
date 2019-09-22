package com.zsoe.businesssharing.business.message;


import android.os.Bundle;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.presenter.BasePresenter;
import com.zsoe.businesssharing.base.presenter.BaseToastNetError;
import com.zsoe.businesssharing.base.presenter.NetCallBack;
import com.zsoe.businesssharing.bean.HaoYouBean;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;

import java.util.List;

import okhttp3.FormBody;
import rx.Observable;
import rx.functions.Func0;


public class HaoYouPresenter extends BasePresenter<HaoYouActivity> {
    final public int REQUEST_LOGIN = 1;
    public OpenLoadMoreDefault<HaoYouBean> loadMoreDefault;

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
                        loadMoreDefault.fixNumAndClear();
                        loadMoreDefault.loadMoreFinish(haoYouBeanList);
                        v.updateList();
                    }
                },
                new BaseToastNetError<HaoYouActivity>());


    }

    public void myfriend_list() {
        loadMoreDefault.pagerAble.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        body = signForm(loadMoreDefault.pagerAble);
        start(REQUEST_LOGIN);

    }




}
