package com.zsoe.businesssharing.base;


import com.zsoe.businesssharing.BuildConfig;

import okhttp3.FormBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface ServerAPI {
    public static final String ENDPOINT = BuildConfig.ENDPOINT;


    /**
     * 获取验证码
     */
    @POST("api/v1/hot/sms")
    Observable<RootResponse> getSms(@Body FormBody body);

}
