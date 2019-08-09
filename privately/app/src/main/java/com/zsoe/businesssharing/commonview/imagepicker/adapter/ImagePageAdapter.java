package com.zsoe.businesssharing.commonview.imagepicker.adapter;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.zsoe.businesssharing.commonview.frescophotowall.wall.photodraweeview.OnPhotoTapListener;
import com.zsoe.businesssharing.commonview.frescophotowall.wall.photodraweeview.PhotoDraweeView;
import com.zsoe.businesssharing.commonview.imagepicker.ImagePicker;
import com.zsoe.businesssharing.commonview.imagepicker.Utils;
import com.zsoe.businesssharing.commonview.imagepicker.bean.ImageItem;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.io.File;
import java.util.ArrayList;


/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ImagePageAdapter extends PagerAdapter {

    private ResizeOptions resizeOptions;
    private ImagePicker imagePicker;
    private ArrayList<ImageItem> images = new ArrayList<>();
    private Activity mActivity;
    public PhotoViewClickListener listener;

    public ImagePageAdapter(Activity activity, ArrayList<ImageItem> images) {
        this.mActivity = activity;
        this.images = images;

        DisplayMetrics dm = Utils.getScreenPix(activity);
        resizeOptions = new ResizeOptions(dm.widthPixels,dm.heightPixels);
        imagePicker = ImagePicker.getInstance();
    }

    public void setData(ArrayList<ImageItem> images) {
        this.images = images;
    }

    public void setPhotoViewClickListener(PhotoViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final PhotoDraweeView photoView = new PhotoDraweeView(mActivity);
        ImageItem imageItem = images.get(position);
        String loadUrl = handlerUrl(imageItem);
        FrecoFactory.getInstance().disPlay(photoView,getUriFromStr(loadUrl), null,resizeOptions, new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                photoView.update(imageInfo.getWidth(),imageInfo.getHeight());
            }
        });
        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if(listener != null) {
                    listener.OnPhotoTapListener(view, x, y);
                }
            }
        });
        container.addView(photoView);
        return photoView;
    }

    public Uri getUriFromStr(String url) {
        if (TextUtils.isEmpty(url)) {
            return Uri.parse("");
        }
        return Uri.parse(url);
    }

    private String handlerUrl(ImageItem imageItem) {
        String imgUrl = imageItem.url;
        if(TextUtils.isEmpty(imgUrl)) {
            imgUrl = imageItem.path;
            if(!imgUrl.startsWith("http")) {
                imgUrl = Uri.fromFile(new File(imgUrl)).toString();
            }
        }
        return imgUrl;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface PhotoViewClickListener {
        void OnPhotoTapListener(View view, float v, float v1);
    }
}
