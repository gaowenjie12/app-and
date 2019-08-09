package com.zsoe.businesssharing.base.presenter;

import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.base.CacheAble;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.utils.ACache;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.LogUtil;
import com.zsoe.businesssharing.utils.StrUtils;

import org.json.JSONObject;

/**
 * 返回RootResponse
 * Created by Administrator on 2016/5/24.
 */
public abstract class NetCompleteBack<View> extends CacheAble<View, RootResponse> {

    @Override
    public void call(View v, RootResponse tRootResponse) {
        DialogManager.getInstance().dismissNetLoadingView();
        LogUtil.e(tRootResponse.toString());


        switch (tRootResponse.getErrCode()) {
            case 0:
                onComplete(v, tRootResponse);
                if (getCacheType() != CacheType.None) {//需要缓存
                    ACache.get(DApplication.getInstance()).put(StrUtils.string2md5(cacheKey), DApplication.gson.toJson(tRootResponse));
                }
                break;

            case 401:

                ToastUtils.showShort(tRootResponse.getMessage());
                DApplication.getInstance().exit();
                DApplication.getInstance().startLogin();
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

                Toast.makeText(DApplication.getInstance(), tRootResponse.getMessage(), Toast.LENGTH_SHORT).show();
                callBackServerError(v, tRootResponse);
                break;
        }

    }

    public abstract void onComplete(View v, RootResponse t);

    public void callBackServerError(View v, RootResponse t) {

    }

}
