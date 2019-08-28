package com.zsoe.businesssharing.base;


import com.zsoe.businesssharing.BuildConfig;
import com.zsoe.businesssharing.bean.HomeBean;
import com.zsoe.businesssharing.business.login.LoginUser;

import okhttp3.FormBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface ServerAPI {
    public static final String ENDPOINT = BuildConfig.ENDPOINT;


    /**
     * 获取验证码
     */
    @POST("sms/send")
    Observable<RootResponse> getSms(@Body FormBody body);

    /**
     * 注册会员
     */
    @POST("user/register")
    Observable<RootResponse> register(@Body FormBody body);

    /**
     * 注册会员
     */
    @POST("user/login")
    Observable<RootResponse<LoginUser>> login(@Body FormBody body);

    /**
     * 首页
     */
    @POST("index/index")
    Observable<RootResponse<HomeBean>> index(@Body FormBody body);

    /**
     * 展厅
     */
    @POST("hall/index")
    Observable<RootResponse<HomeBean>> hall(@Body FormBody body);

}
