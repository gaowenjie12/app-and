package com.zsoe.businesssharing.base.baseadapter;


import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.AppImageUtils;

import java.lang.reflect.Field;
import java.util.List;

import activeandroid.bean.ImageField;
import activeandroid.bean.TextField;
import rx.functions.Action2;

/**
 * Created by Administrator on 2016/7/21.
 */
public class OnionRecycleAdapter<T> extends BaseQuickAdapter<T> {
    public OnionRecycleAdapter(int layoutId, List<T> list) {
        super(layoutId, list);
    }

    Action2<BaseViewHolder, T> callBack = null;


    public Action2<BaseViewHolder, T> getCallBack() {
        return callBack;
    }

    public void setCallBack(Action2<BaseViewHolder, T> callBack) {
        this.callBack = callBack;
    }


    @Override
    protected void convert(BaseViewHolder helper, T item) {
        Field[] fields = item.getClass().getDeclaredFields();
        for (Field f : fields) {
            //认为是文本
            TextField textField = f.getAnnotation(TextField.class);
            try {
                if (textField != null && helper.getView(textField.value()) != null) {
                    helper.setText(textField.value(), f.get(item) + "");
                } else {
                    ImageField imageField = f.getAnnotation(ImageField.class);
                    if (imageField != null && helper.getView(imageField.value()) != null) {
                        SimpleDraweeView imageView = helper.getView(imageField.value());
                        disPlayImg(f.get(item) + "", imageView);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (callBack != null) {
            callBack.call(helper, item);
        }
    }


    private void disPlayImg(String url, SimpleDraweeView imageView){
        boolean picisNative=false;
        if (url.contains("http")) {
            picisNative = false;
        } else {
            picisNative = true;
        }
        if (picisNative) {//加载本地图片
            if (url.contains("."))//去掉本地图片后缀
                url = url.substring(0, url.lastIndexOf("."));
            int id = mContext.getResources().getIdentifier(url, "drawable",
                    "com.open.face2face");
            if (id != 0) {//本地图片存在
                imageView.setImageResource(id);
            }
            else {
                AppImageUtils.displayImage(mContext, imageView,"");
            }
        }
        else {
            AppImageUtils.displayImage(mContext, imageView,url);
        }
    }
}
