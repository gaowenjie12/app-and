package com.zsoe.businesssharing.utils;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-05-08 15:36
 * @version:
 * @类说明:
 */
public class UpLoadFileUtils {

    //多个文件上传,Filelist
    public static Request getFilesRequest(String fileKey, String url, List<File> files, Map<String, String> maps) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (maps == null) {
            for (int i = 0; i < files.size(); i++) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"file.jpg\""),
                        RequestBody.create(MediaType.parse("image/png"), files.get(i))
                ).build();
            }
        } else {
            for (String key : maps.keySet()) {
                String str = maps.get(key);
                builder.addFormDataPart(key, str);
            }
            for (int j = 0; j < files.size(); j++) {
                long fileSize = files.get(j).length();
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=" + fileKey + ";filename=\"file.jpg\";filesize=" + fileSize),
                        RequestBody.create(MediaType.parse("image/png"), files.get(j))
                );
            }
        }

        RequestBody body = builder.build();
        return new Request.Builder().url(url).post(body).build();
    }
}
