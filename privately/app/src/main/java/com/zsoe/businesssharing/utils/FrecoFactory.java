package com.zsoe.businesssharing.utils;

import android.content.Context;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.zsoe.businesssharing.R;

/**
 * 所有facebook freco加载顶图片都应用该类
 * 方便以后统一管理
 *
 * @author myChaoFile
 */
public class FrecoFactory {
    private static FrecoFactory instance;

    private FrecoFactory() {
    }

    public static FrecoFactory getInstance() {
        if (instance == null) {
            synchronized (FrecoFactory.class) {
                if (instance == null) {
                    instance = new FrecoFactory();
                }
            }
        }
        return instance;
    }

    public Uri getUriFromStr(String url) {
        if (TextUtils.isEmpty(url)) {
            return Uri.parse("");
        }
        return Uri.parse(url);
    }

    /**
     * 加载本地res文件
     *
     * @param draweeView
     * @param resourceId
     */
    public void disPlay(SimpleDraweeView draweeView, int resourceId) {
        disPlay(draweeView, (new Uri.Builder()).scheme("res").path(String.valueOf(resourceId)).build());
    }

    /**
     * 加载本地file文件
     *
     * @param draweeView
     * @param imagePath
     */
    public void disPlayfile(SimpleDraweeView draweeView, String imagePath) {
        disPlay(draweeView, (new Uri.Builder()).scheme("file").path(String.valueOf(imagePath)).build());
    }


    /**
     * SimpleDrawableView调用改方法显示图片
     *
     * @param draweeView
     * @param uri
     * @param resizeOptions 指定缩放大小
     */
    public void disPlay(SimpleDraweeView draweeView, String uri, ResizeOptions resizeOptions) {
        disPlay(draweeView, getUriFromStr(uri), resizeOptions);
    }

    public void disPlay(SimpleDraweeView draweeView, Uri uri, ResizeOptions resizeOptions) {
        disPlay(draweeView, uri, null, resizeOptions, null);
    }

    /**
     * 加载图片，以默认大小显示
     *
     * @param draweeView
     * @param imgUrl
     */
    public void disPlay(SimpleDraweeView draweeView, String imgUrl) {
        disPlay(draweeView, getUriFromStr(imgUrl), null, null, null);
    }

    public void disPlay(SimpleDraweeView draweeView, Uri uri) {
        disPlay(draweeView, uri, null, null, null);
    }

    /**
     * 下载网络图片并
     * 优先显示lowResUri低质量图片地址
     *
     * @param draweeView
     * @param imgUrl
     * @param lowResUri
     */
    public void disPlay(SimpleDraweeView draweeView, String imgUrl, String lowResUri) {
        disPlay(draweeView, getUriFromStr(imgUrl), lowResUri, null, null);
    }

    public void disPlay(SimpleDraweeView draweeView, String imgUrl, String lowResUri, ControllerListener controllerListener) {
        disPlay(draweeView, getUriFromStr(imgUrl), lowResUri, null, controllerListener);
    }

    public void disPlay(SimpleDraweeView draweeView, String imgUrl, String lowResUri, ResizeOptions resizeOptions, ControllerListener controllerListener) {
        disPlay(draweeView, getUriFromStr(imgUrl), lowResUri, resizeOptions, controllerListener);
    }

    /**
     * 自定义控件调用该方法显示
     *
     * @param draweeView
     * @param uri
     * @param resizeOptions
     */
    public void disPlay(SimpleDraweeView draweeView, Uri uri, String lowResUri, ResizeOptions resizeOptions, ControllerListener controllerListener) {
        ImageRequestBuilder imageRequestBuilder =
                ImageRequestBuilder.newBuilderWithSource(uri);
        if (UriUtil.isNetworkUri(uri)) {
            imageRequestBuilder.setProgressiveRenderingEnabled(true);
        } else {
            imageRequestBuilder.setLocalThumbnailPreviewsEnabled(true);
            imageRequestBuilder.setProgressiveRenderingEnabled(false);
            if (resizeOptions != null) {
                imageRequestBuilder.setResizeOptions(resizeOptions);
            }
        }
//        imageRequestBuilder.setAutoRotateEnabled(true);
//        imageRequestBuilder.setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH);

        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequestBuilder.build())
                .setOldController(draweeView.getController())
                .setLowResImageRequest(ImageRequest.fromUri(lowResUri))
                .setControllerListener(controllerListener)
                .build();
        draweeView.setController(draweeController);
    }


    public void disPlayAvatar(Context mContext, SimpleDraweeView draweeView, String imageUrl) {
        disPlayAvatar(mContext, draweeView, getUriFromStr(imageUrl));
    }

    public void disPlayAvatarRounding(Context mContext, int resId, SimpleDraweeView draweeView, String imageUrl, int rountSize) {
        disPlayAvatarRounding(resId, mContext, draweeView, getUriFromStr(imageUrl), rountSize);
    }

    /**
     * 根据角色动态设置默认图片
     */
    private void disPlayAvatar(Context mContext, SimpleDraweeView draweeView, Uri imageUrl) {

        int resId = R.mipmap.default_icon;

//        RoundingParams roundingParams = new RoundingParams();
//        roundingParams.setRoundAsCircle(true);//是否为圆形
//        roundingParams.setBorder(mContext.getResources().getColor(R.color.main_shadow),3);//设置边框宽度和颜色

//        Drawable rotateDrawable = new AutoRotateDrawable(ContextCompat.getDrawable(mContext, resId), 5000);

        //获取GenericDraweeHierarchy对象
        GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();
        //设置淡入淡出动画持续时间
//                .setFadeDuration(5000)
        //设置占位图，它默认的缩放类型是CENTER_INSIDE
        if (hierarchy != null) {
            hierarchy.setPlaceholderImage(ContextCompat.getDrawable(mContext, resId), ScalingUtils.ScaleType.CENTER_CROP);
            //设置失败图及其缩放类型
            hierarchy.setFailureImage(ContextCompat.getDrawable(mContext, resId), ScalingUtils.ScaleType.CENTER_CROP);
            hierarchy.setProgressBarImage(resId, ScalingUtils.ScaleType.CENTER_CROP);
        }
//        hierarchy.setRoundingParams(roundingParams);

        //设置正在加载图，它默认的缩放类型是CENTER_INSIDE
//                .setProgressBarImage(rotateDrawable,ScalingUtils.ScaleType.CENTER_CROP)
        //设置重试图，它默认的缩放类型是CENTER_INSIDE
//                .setRetryImage(ContextCompat.getDrawable(mContext,resId))

        //构建
//                .build();

        //设置GenericDraweeHierarchy
//        draweeView.setHierarchy(hierarchy);
        //开始下载
        draweeView.setImageURI(imageUrl);

        //构建Controller
        //        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                //设置点击重试是否开启
//                .setTapToRetryEnabled(true)
//                //构建
//                .build();
//
//        //设置Controller
//        draweeView.setController(controller);
    }


    public void disPlayAvatar(Context mContext, int resId, SimpleDraweeView draweeView, String imageUrl) {
        disPlayAvatar(mContext, resId, draweeView, getUriFromStr(imageUrl));
    }

    /**
     * 根据角色动态设置默认图片
     */
    private void disPlayAvatar(Context mContext, int resId, SimpleDraweeView draweeView, Uri imageUrl) {

        int res;
        if (resId == 0) {
            res = R.mipmap.default_icon;
        } else {
            res = resId;
        }

        GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();
        //设置淡入淡出动画持续时间
//                .setFadeDuration(5000)
        //设置占位图，它默认的缩放类型是CENTER_INSIDE
        if (hierarchy != null) {
            hierarchy.setPlaceholderImage(ContextCompat.getDrawable(mContext, res), ScalingUtils.ScaleType.CENTER_CROP);
            //设置失败图及其缩放类型
            hierarchy.setFailureImage(ContextCompat.getDrawable(mContext, res), ScalingUtils.ScaleType.CENTER_CROP);
            hierarchy.setProgressBarImage(resId, ScalingUtils.ScaleType.CENTER_CROP);
        }
        //开始下载
        draweeView.setImageURI(imageUrl);
    }


    /**
     * 根据角色动态设置默认图片
     */
    private void disPlayAvatarRounding(int resid, Context mContext, SimpleDraweeView draweeView, Uri imageUrl, int rountSize) {
        int res;
        if (resid == 0) {
            res = R.mipmap.icon_default;
        } else {
            res = resid;
        }


//        RoundingParams roundingParams = new RoundingParams();
//        roundingParams.setRoundAsCircle(true);//是否为圆形
//        roundingParams.setBorder(mContext.getResources().getColor(R.color.main_shadow),3);//设置边框宽度和颜色

//        Drawable rotateDrawable = new AutoRotateDrawable(ContextCompat.getDrawable(mContext, resId), 5000);

        //获取GenericDraweeHierarchy对象
        GenericDraweeHierarchy hierarchy = draweeView.getHierarchy();

        //设置淡入淡出动画持续时间
//                .setFadeDuration(5000)
        //设置占位图，它默认的缩放类型是CENTER_INSIDE
        if (hierarchy != null) {

            if (rountSize > 0) {
                //设置圆角半径
                hierarchy.setRoundingParams(RoundingParams.fromCornersRadius(rountSize));
            }

            hierarchy.setPlaceholderImage(ContextCompat.getDrawable(mContext, res), ScalingUtils.ScaleType.CENTER_CROP);
            //设置失败图及其缩放类型
            hierarchy.setFailureImage(ContextCompat.getDrawable(mContext, res), ScalingUtils.ScaleType.CENTER_CROP);
            hierarchy.setProgressBarImage(res, ScalingUtils.ScaleType.CENTER_CROP);
        }
//        hierarchy.setRoundingParams(roundingParams);

        //设置正在加载图，它默认的缩放类型是CENTER_INSIDE
//                .setProgressBarImage(rotateDrawable,ScalingUtils.ScaleType.CENTER_CROP)
        //设置重试图，它默认的缩放类型是CENTER_INSIDE
//                .setRetryImage(ContextCompat.getDrawable(mContext,resId))

        //构建
//                .build();

        //设置GenericDraweeHierarchy
//        draweeView.setHierarchy(hierarchy);
        //开始下载
        draweeView.setImageURI(imageUrl);

        //构建Controller
        //        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                //设置点击重试是否开启
//                .setTapToRetryEnabled(true)
//                //构建
//                .build();
//
//        //设置Controller
//        draweeView.setController(controller);
    }
}
