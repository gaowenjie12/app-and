package com.zsoe.businesssharing.base.presenter;


import com.zsoe.businesssharing.BuildConfig;

import rx.functions.Action1;

/**
 * 异常处理，测试环境下正常崩溃，正式环境错误反馈到友盟
 * Created by myChaoFile on 2018/10/15.
 */

public class HandlerCrashError implements Action1<Throwable> {
    @Override
    public void call(Throwable throwable) {
        //测试环境
        if(BuildConfig.DEBUG) {
            throw new IllegalArgumentException(throwable);
        }
        else{
            //如果是正式环境
//            MobclickAgent.reportError(BaseApplication.getInstance(), throwable);
        }
        throwable.printStackTrace();
    }
}
