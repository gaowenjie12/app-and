package com.zsoe.businesssharing.utils;

import android.util.Log;

/**
 * @author 于长亮 & E-mail: yuchangliang@kuaiyoujia.com
 * @create_time 创建时间：2015-12-10 下午6:52:18
 * @类说明:Log统一管理类
 */
public class LogUtil {
    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "open";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, createLog(msg));
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, createLog(msg));
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, createLog(msg));
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, createLog(msg));
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, createLog(msg));
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, createLog(msg));
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, createLog(msg));
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, createLog(msg));
    }
    private static String createLog(String log) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(":");
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }
}