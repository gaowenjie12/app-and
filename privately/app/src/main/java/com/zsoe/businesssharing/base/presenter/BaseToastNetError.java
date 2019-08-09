package com.zsoe.businesssharing.base.presenter;


import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.base.CacheAble;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.ExceptionToastUtil;

/**
 * 联网错误回调
 * 如果网络异常不需要其他操作，只Toast可以
 * Created by Administrator on 2016/5/24.
 */
public class BaseToastNetError<View> extends CacheAble<View, Throwable> {

    @Override
    public void call(View v, Throwable throwable) {
        DialogManager.getInstance().dismissNetLoadingView();
        if (v instanceof BaseFragment) {
            //失败后关闭下拉刷新
            if (((BaseFragment) v).mPtrFrame != null)
//                ((BaseFragment) v).mPtrFrame.autoRefresh();
                ((BaseFragment) v).mPtrFrame.refreshComplete();
        } else if (v instanceof BaseActivity) {
            if (((BaseActivity) v).mPtrFrame != null)
//                ((BaseActivity) v).mPtrFrame.autoRefresh();
                ((BaseActivity) v).mPtrFrame.refreshComplete();
        }
        ExceptionToastUtil.checkHttpException(throwable);

    }

}
