package com.zsoe.businesssharing.utils.downloadutil;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Description:
 */
public interface DownloadService {

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

}
