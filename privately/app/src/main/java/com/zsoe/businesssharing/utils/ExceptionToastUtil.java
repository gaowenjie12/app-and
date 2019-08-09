package com.zsoe.businesssharing.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonSyntaxException;

import java.io.EOFException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 统一调用该类做异常处理提示
 */
public class ExceptionToastUtil {

    public static void checkHttpException(Throwable mThrowable) {
        checkHttpException(mThrowable, null, null, null, false);
    }

    public static void checkHttpException(Throwable mThrowable, String internet) {
        checkHttpException(mThrowable, internet, null, null, false);
    }

    public static void checkHttpException(Throwable mThrowable, String internet, String message, String key) {
        checkHttpException(mThrowable, internet, message, key, false);
    }

    /**
     * 异常处理
     *
     * @param mThrowable
     * @param internet
     * @param message
     * @param key
     * @param reportError 有特殊需求将错误上报友盟，默认关
     */
    public static void checkHttpException(Throwable mThrowable, String internet, String message, String key, boolean reportError) {
        checkHttpException(mThrowable, internet, message, key, reportError, null, false);
    }

    /**
     * 异常处理,上传图片失败弹提示框
     *
     * @param mThrowable
     * @param internet
     * @param message
     * @param key
     * @param activity
     * @param flag  是否提示弹唱:true提示;false不提示
     */
    public static void checkHttpException(Throwable mThrowable, String internet, String message, String key, boolean reportError, Activity activity, boolean flag) {
        if (TextUtils.isEmpty(internet)) {
            internet = "";
        }
        if (mThrowable instanceof UnknownHostException) {
            //未知的主机异常，域名找不到
            ToastUtils.showShort("网络异常");
        } else if (mThrowable instanceof JsonSyntaxException) {
            //json解析异常
            ToastUtils.showShort("数据解析异常");
        } else if (mThrowable instanceof SocketTimeoutException) {
            //客户端没有在限定的时间内将数据发送给服务器，服务器为了保证服务性能，认定那个连接已经失效，所以出现上述异常。
            ToastUtils.showShort("网络不给力，换个地方试下吧 " + internet);
            if (flag && activity != null && !activity.isFinishing()) {
//                DialogManager.showNormalDialog(activity, "温馨提示", "检测到当前网速过低,建议您不要上传图片", "知道了", "", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
            }
            if (reportError) {
//                MobclickAgent.reportError(BaseApplication.getInstance(), mThrowable);
//                saveException2File(mThrowable, internet, key);
            }
        } else if (mThrowable instanceof ConnectException) {
            // 抛出此类异常，表示无法连接，也就是说当前主机不存在
            ToastUtils.showShort("连接服务失败,请检查网络 " + internet);
        } else if (mThrowable instanceof EOFException) {
            // 抛出此类异常，表示连接丢失，也就是说网络连接的另一端非正常关闭连接（可能是主机断电、网线出现故障等导致
            ToastUtils.showShort("连接丢失");
        } else if (mThrowable instanceof BindException) {
            //抛出此类异常,表示端口已经被占用
            ToastUtils.showShort("端口被占用");
        } else if (mThrowable instanceof RuntimeException) {
            ToastUtils.showShort("网络不给力，换个网络试下吧 " + internet);
            if (flag) {
//                DialogManager.showNormalDialog(activity, "温馨提示", "检测到当前网速过低,建议您不要上传图片", "知道了", "", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
            }
            if (reportError) {
//                MobclickAgent.reportError(BaseApplication.getInstance(), mThrowable);
//                saveException2File(mThrowable, internet, key);
            }
        } else if (mThrowable instanceof SocketException) {
            ToastUtils.showShort("网络中断,请检查网络是否良好");
            if (reportError) {
//                MobclickAgent.reportError(BaseApplication.getInstance(), mThrowable);
//                saveException2File(mThrowable, internet, key);
            }
        } else {
            if (!TextUtils.isEmpty(message)) {
                ToastUtils.showShort(message);
            }
            if (reportError) {
//                MobclickAgent.reportError(BaseApplication.getInstance(), mThrowable);
//                saveException2File(mThrowable, internet, key);
            }
        }
        mThrowable.printStackTrace();
    }

    /**
     * 保存文件
     *
     * @param internet 网速
     * @param key      保存key
     */
//    private static void saveException2File(Throwable mThrowable, String internet, String key) {
//        if (!TextUtils.isEmpty(internet) && !TextUtils.isEmpty(key)) {
//            AppBean appBean = BaseApplication.getInstance().getAppBean(internet, key, mThrowable.toString());
//            if (appBean != null) {
//                PreferencesUtils.getInstance().saveAppBean(appBean);
//            }
//        }
//    }
}
