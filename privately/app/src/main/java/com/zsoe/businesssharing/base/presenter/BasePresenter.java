package com.zsoe.businesssharing.base.presenter;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MultipartBody;

/**
 * Created by onion on 2016/5/10.
 * for cache
 * <View>  type View's className and restartableId for cache key
 * <T>  T's Gsonformate is cache value
 */
public class BasePresenter<View> extends MPresenter<View> {
    //帮builder加签名  时间 userId
    protected MultipartBody.Builder getBuilder(HashMap<String, String> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (params != null) {
            for (String strs : params.keySet()) {
                builder.addFormDataPart(strs, params.get(strs));
            }
        }
//        if (TApplication.getInstance().getUserId() != 0) {
//            builder.addFormDataPart("uid", String.valueOf(TApplication.getInstance().getUserId()));
//        }
//        if (TApplication.getInstance().getToken() != null) {
//            builder.addFormDataPart("token", TApplication.getInstance().getToken());
//        }
//        if (TApplication.getInstance().getClazzId() != 0) {
//            builder.addFormDataPart("clazzId", String.valueOf(TApplication.getInstance().getClazzId()));
//        }
        return builder;
    }

    //帮builder加签名  时间 userId
    public MultipartBody.Builder getBuilder() {
        return getBuilder(null);
    }


    protected FormBody signForm(HashMap<String, String> params) {
        if (params == null)
            params = new HashMap<>();
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
//        if (TApplication.getInstance().getUserId() != 0) {
//            builder.add("uid", String.valueOf(TApplication.getInstance().getUserId()));
//        }
//        if (TApplication.getInstance().getToken() != null) {
//            builder.add("token", TApplication.getInstance().getToken());
//        }
//        if (TApplication.getInstance().getClazzId() != 0) {
//            builder.add("clazzId", String.valueOf(TApplication.getInstance().getClazzId()));
//        }
        return builder.build();
    }
}
