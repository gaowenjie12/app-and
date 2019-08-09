package com.zsoe.businesssharing.utils.downloadutil;


import androidx.annotation.NonNull;

import com.zsoe.businesssharing.utils.android.schedulers.AndroidSchedulers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description: 修改下载类
 */
public class DownloadUtils {
    private final int DEFAULT_TIMEOUT = 30;

    private Retrofit retrofit;

    private JsDownloadListener listener;

    public DownloadUtils(String baseUrl, JsDownloadListener listener) {

        this.listener = listener;
        JsDownloadInterceptor mInterceptor = new JsDownloadInterceptor(listener);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(mInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 开始下载
     *
     * @param url
     * @param filePath
     */
    public void download(@NonNull String url, final String filePath) {
        listener.onStartDownload();
        // subscribeOn()改变调用它之前代码的线程
        // observeOn()改变调用它之后代码的线程
        retrofit.create(DownloadService.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {

                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                // 用于计算任务
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        writeFile(inputStream, filePath);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFail(e.toString());
                    }

                    @Override
                    public void onNext(Object o) {
                        listener.onFinishDownload(filePath);
                    }
                });

    }

    /**
     * 将输入流写入文件
     *
     * @param inputString
     * @param filePath
     */
    private void writeFile(InputStream inputString, String filePath) {
        File descfile = new File(filePath);
        if (descfile.exists()) {
            descfile.delete();
        }
        if(!descfile.getParentFile().exists()) {
            descfile.getParentFile().mkdirs();
        }
        BufferedSource bufferedSource = null;
        BufferedSink bufferedSink = null;
        try {
            bufferedSource = Okio.buffer(Okio.source(inputString));
            bufferedSink = Okio.buffer(Okio.sink(descfile));
            bufferedSink.writeAll(bufferedSource);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            listener.onFail(e.getMessage());
        }
        catch (IOException ex){
            ex.printStackTrace();
            listener.onFail(ex.getMessage());
        }
        finally {
            if(bufferedSource != null) {
                try {
                    bufferedSource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bufferedSink != null) {
                try {
                    bufferedSink.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
