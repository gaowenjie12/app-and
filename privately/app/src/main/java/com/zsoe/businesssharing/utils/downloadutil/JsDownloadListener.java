package com.zsoe.businesssharing.utils.downloadutil;

/**
 * Description: 下载进度回调
 */
public interface JsDownloadListener {

    void onStartDownload();

    void onProgress(int progress);

    void onFinishDownload(String filepath);

    void onFail(String errorInfo);

}
