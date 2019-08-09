package com.zsoe.businesssharing.base.presenter;


import android.text.TextUtils;

import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.commonview.imagepicker.ImagePicker;
import com.zsoe.businesssharing.commonview.imagepicker.bean.ImageItem;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.ImageCompressUtils;
import com.zsoe.businesssharing.utils.LogUtil;
import com.zsoe.businesssharing.utils.PictureCompressUtils;
import com.zsoe.businesssharing.utils.android.schedulers.AndroidSchedulers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class SendImgPresenter<V> extends BasePresenter<V> {


    protected MultipartBody body;

    protected void setUploadBitmap(final String key, final MultipartBody.Builder builder, final int item) {

        ImagePicker imagePicker = ImagePicker.getInstance();
        Observable.from(imagePicker.getSelectedImages())
                .subscribeOn(Schedulers.io())
                .filter(new Func1<ImageItem, Boolean>() {
                    @Override
                    public Boolean call(ImageItem imageItem) {
                        if (!"net".equals(imageItem.mimeType) && !TextUtils.isEmpty(imageItem.path)) {
                            return true;
                        }
                        return false;
                    }
                })
                .map(new Func1<ImageItem, File>() {
                    @Override
                    public File call(ImageItem imageItem) {
//                        LogUtil.e("压缩之前图片大小==" + FileUtils.GetFileSize(imageItem.path));
//                        System.out.println("压缩之前图片大小==" + FileUtils.GetFileSize(imageItem.path));
                        String imagePath = ImageCompressUtils.getScaledImage(DApplication.getInstance().getApplicationContext(), imageItem.path, 820, 960);
                        return new File(imagePath);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onCompleted() {
                        body = builder.build();
                        start(item);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        MobclickAgent.reportError(BaseApplication.getInstance().getApplicationContext(),e);
                    }

                    @Override
                    public void onNext(File pictureFile) {
                        LogUtil.e("上传的图片路径==" + pictureFile.getAbsoluteFile());
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), pictureFile);
                        builder.addFormDataPart(key, pictureFile.getName(), requestBody);

//                        builder.addFormDataPart("key", pictureFile.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), pictureFile));
                    }
                });

    }

    public void compress(final CompressListener compressListener) {

        final List<File> fileList = new ArrayList<>();

        boolean havePic = !ImagePicker.getInstance().getSelectedImages().isEmpty();
        if (!havePic) {
            compressListener.compress(fileList);
            return;
        }

        ImagePicker imagePicker = ImagePicker.getInstance();
        Observable.from(imagePicker.getSelectedImages())
                .subscribeOn(Schedulers.io())
                .map(new Func1<ImageItem, File>() {
                    @Override
                    public File call(ImageItem imageItem) {

                        String imagePath = ImageCompressUtils.getScaledImage(DApplication.getInstance().getApplicationContext(), imageItem.path, 820, 960);
                        return new File(imagePath);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onCompleted() {
                        compressListener.compress(fileList);
                    }

                    @Override
                    public void onError(Throwable e) {
                       DialogManager.getInstance().dismissNetLoadingView();

                    }

                    @Override
                    public void onNext(File pictureFile) {
                        DialogManager.getInstance().dismissNetLoadingView();
                        LogUtil.e("压缩之后的图片=" + pictureFile.getAbsolutePath());
                        fileList.add(pictureFile);
                    }
                });

    }

    //清除图片
    protected void clearImg() {
        ImagePicker.getInstance().clear();
        PictureCompressUtils.clearCompressFilse();//清除压缩图片
    }

    public interface CompressListener {
        void compress(List<File> fileList);
    }

}
