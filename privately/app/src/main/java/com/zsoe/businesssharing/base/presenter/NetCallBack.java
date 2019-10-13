package com.zsoe.businesssharing.base.presenter;

import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.zsoe.businesssharing.base.CacheAble;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.utils.ACache;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.LogUtil;
import com.zsoe.businesssharing.utils.StrUtils;

import org.json.JSONObject;

/**
 * 直接返回bean
 * Created by Administrator on 2016/5/24.
 */
public abstract class NetCallBack<View, T> extends CacheAble<View, RootResponse<T>> {

    @Override
    public void call(View v, RootResponse<T> tRootResponse) {
        DialogManager.getInstance().dismissNetLoadingView();
        LogUtil.e(tRootResponse.toString());
        Logger.e(tRootResponse.toString());


        switch (tRootResponse.getCode()) {
            case 1:
                callBackResponse(v, tRootResponse);
                callBack(v, tRootResponse.getData());


                if (getCacheType() != CacheType.None) {//需要缓存
                    ACache.get(DApplication.getInstance()).put(StrUtils.string2md5(cacheKey), DApplication.gson.toJson(tRootResponse));
                }
                break;
            case 401:
                //登录失效，被踢出
                DApplication.getInstance().exit();
                DApplication.getInstance().startLogin();
                Toast.makeText(DApplication.getInstance(), tRootResponse.getMsg(), Toast.LENGTH_SHORT).show();

                break;
            default:
            case 800:
                if (getCacheType() == CacheType.After) {//异常后缓存
                    JSONObject object = ACache.get(DApplication.getInstance()).getAsJSONObject(StrUtils.string2md5(cacheKey));
                    if (object != null) {
                        RootResponse o = DApplication.gson.fromJson(object.toString(), RootResponse.class);
                        onCache.call(v, o);
                    }
                }
                Toast.makeText(DApplication.getInstance(), tRootResponse.getMsg(), Toast.LENGTH_SHORT).show();
                callBackServerError(v, tRootResponse);
                break;
        }

    }

    public abstract void callBack(View v, T t);

    public void callBackResponse(View v, RootResponse<T> response) {
    }

    public void callBackServerError(View v, RootResponse t) {

    }


}
