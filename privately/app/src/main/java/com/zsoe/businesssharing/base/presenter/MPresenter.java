package com.zsoe.businesssharing.base.presenter;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zsoe.businesssharing.base.CacheAble;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.utils.ACache;
import com.zsoe.businesssharing.utils.StrUtils;
import com.zsoe.businesssharing.utils.android.schedulers.AndroidSchedulers;

import org.json.JSONObject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * Created by onion on 2016/5/10.
 * for cache
 * <View>  type View's className and restartableId for cache key
 * <T>  T's Gsonformate is cache value
 */
public class MPresenter<View> extends RxPresenter<View> {
    private String mPresenterName = getClass().getName();

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    @Override
    protected void onTakeView(View view) {
        super.onTakeView(view);

    }

    /**
     * 联网在后台，回调在UI   添加缓存
     *
     * @param restartableId     an id of the restartable.
     * @param observableFactory a factory that should return an Observable when the restartable should run.
     * @param onNext            a callback that will be called when received data should be delivered to view.
     * @param onError           a callback that will be called if the source observable emits onError.
     * @param <T>
     */
    public <T> void restartableLatestCache(final int restartableId, final Func0<Observable<T>> observableFactory, final CacheAble<View, T> onNext, @Nullable final CacheAble<View, Throwable> onError, CacheAble.CacheType type, Action2<View, RootResponse> onCache) {
        initCache(restartableId, onNext, onError, type, onCache);
        restartable(restartableId, new Func0<Subscription>() {
            @Override
            public Subscription call() {
                return observableFactory.call()
                        .compose(MPresenter.this.<T>deliverLatestCache()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(split(onNext, onError), new HandlerCrashError());
            }
        });

    }

    /**
     * 不带缓存的 联网在后台 展示在UI线程
     *
     * @param restartableId     an id of the restartable.
     * @param observableFactory a factory that should return an Observable when the restartable should run.
     * @param onNext            a callback that will be called when received data should be delivered to view.
     * @param onError           a callback that will be called if the source observable emits onError.
     * @param <T>
     */
    @Override
    public <T> void restartableFirst(int restartableId, final Func0<Observable<T>> observableFactory,
                                     final Action2<View, T> onNext, @Nullable final Action2<View, Throwable> onError) {
        restartable(restartableId, new Func0<Subscription>() {
            @Override
            public Subscription call() {
                return observableFactory.call()
                        .compose(MPresenter.this.<T>deliverFirst()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(split(onNext, onError), new HandlerCrashError());
            }
        });
    }

    /**
     * @param restartableId
     * @param observableFactory
     * @param onNext
     * @param onError
     * @param type
     * @param onCache
     * @param <T>
     */
    public <T> void restartableFirst(int restartableId, final Func0<Observable<T>> observableFactory,
                                     final CacheAble<View, T> onNext, @Nullable final CacheAble<View, Throwable> onError, CacheAble.CacheType type, Action2<View, RootResponse> onCache) {
        initCache(restartableId, onNext, onError, type, onCache);

        restartable(restartableId, new Func0<Subscription>() {
            @Override
            public Subscription call() {
                return observableFactory.call()
                        .compose(MPresenter.this.<T>deliverFirst())
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(split(onNext, onError), new HandlerCrashError());
            }
        });
    }

    /**
     * 缓存模块
     */

    private <T> void initCache(int restartableId, final CacheAble<View, T> onNext, @Nullable final CacheAble<View, Throwable> onError, CacheAble.CacheType type, @Nullable final Action2<View, RootResponse> onCache) {
        if (onCache == null || type == CacheAble.CacheType.None) {
            return;
        }
        final String key = mPresenterName + restartableId;
        if (type == CacheAble.CacheType.Before) {
            doInView(new Action1<View>() {
                @Override
                public void call(View view) {
                    RootResponse response = getCache(key);
                    if (response != null) {
                        onCache.call(view, response);
                    }

                }
            });

        }
        onNext.setTypeAndKey(type, key, onCache);
        onError.setTypeAndKey(type, key, onCache);
    }

    /**
     * 获取cache的时候
     *
     * @param cacheKey
     */
    public RootResponse getCache(String cacheKey) {
        //get cache
        JSONObject cache = ACache.get(DApplication.getInstance()).getAsJSONObject(StrUtils.string2md5(cacheKey));
        if (cache == null) return null;
        return DApplication.gson.fromJson(cache.toString(), RootResponse.class);
    }

    protected void doInView(Action1<View> action1) {
        view().take(1).subscribe(action1);
    }

    //帮builder加签名  时间 userId
//    protected MultipartBody.Builder getBuilder(HashMap<String, String> params) {
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        if (params != null) {
//            for (String strs : params.keySet()) {
//                builder.addFormDataPart(strs, params.get(strs));
//            }
//        }
//
//        if (PreferencesUtils.getInstance().getUserId() != 0) {
//            builder.addFormDataPart("uid", String.valueOf(PreferencesUtils.getInstance().getUserId()));
//        }
//        if (PreferencesUtils.getInstance().getToken() != null) {
//            builder.addFormDataPart("token", PreferencesUtils.getInstance().getToken());
//        }
//
//        return builder;
//    }

    //帮builder加签名  时间 userId
//    public MultipartBody.Builder getBuilder() {
//        return getBuilder(null);
//    }


//    protected FormBody signForm(HashMap<String, String> params) {
//        if (params == null)
//            params = new HashMap<>();
//        FormBody.Builder builder = new FormBody.Builder();
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            builder.add(entry.getKey(), entry.getValue());
//        }
//        if (PreferencesUtils.getInstance().getUserId() != 0) {
//            builder.add("uid", String.valueOf(PreferencesUtils.getInstance().getUserId()));
//        }
//        if (PreferencesUtils.getInstance().getToken() != null) {
//            builder.add("token", PreferencesUtils.getInstance().getToken());
//        }
//        return builder.build();
//    }
}
