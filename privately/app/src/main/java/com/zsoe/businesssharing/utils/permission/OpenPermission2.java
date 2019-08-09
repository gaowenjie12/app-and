package com.zsoe.businesssharing.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.SpannableHelper;

import java.util.ArrayList;
import java.util.List;

import static com.yanzhenjie.permission.Permission.ACCESS_COARSE_LOCATION;
import static com.yanzhenjie.permission.Permission.ACCESS_FINE_LOCATION;
import static com.yanzhenjie.permission.Permission.BODY_SENSORS;
import static com.yanzhenjie.permission.Permission.CALL_PHONE;
import static com.yanzhenjie.permission.Permission.CAMERA;
import static com.yanzhenjie.permission.Permission.GET_ACCOUNTS;
import static com.yanzhenjie.permission.Permission.PROCESS_OUTGOING_CALLS;
import static com.yanzhenjie.permission.Permission.READ_CALENDAR;
import static com.yanzhenjie.permission.Permission.READ_CALL_LOG;
import static com.yanzhenjie.permission.Permission.READ_CONTACTS;
import static com.yanzhenjie.permission.Permission.READ_EXTERNAL_STORAGE;
import static com.yanzhenjie.permission.Permission.READ_PHONE_STATE;
import static com.yanzhenjie.permission.Permission.READ_SMS;
import static com.yanzhenjie.permission.Permission.RECEIVE_MMS;
import static com.yanzhenjie.permission.Permission.RECEIVE_SMS;
import static com.yanzhenjie.permission.Permission.RECEIVE_WAP_PUSH;
import static com.yanzhenjie.permission.Permission.RECORD_AUDIO;
import static com.yanzhenjie.permission.Permission.SEND_SMS;
import static com.yanzhenjie.permission.Permission.USE_SIP;
import static com.yanzhenjie.permission.Permission.WRITE_CALENDAR;
import static com.yanzhenjie.permission.Permission.WRITE_CALL_LOG;
import static com.yanzhenjie.permission.Permission.WRITE_CONTACTS;
import static com.yanzhenjie.permission.Permission.WRITE_EXTERNAL_STORAGE;

/**
 * @author 于长亮   & E-mail: yuchl@mail.open.com.cn
 * @create_time 创建时间：2018/11/5 11:23
 * @version:
 * @类说明: 针对targetSDK>=23的动态权限判断类
 */
public class OpenPermission2 {

    private Context mContext;
    //在SplashActivity调用的存储权限和电话状态权限
    public static final String[] INIT_PERMISSION = new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE};

    public OpenPermission2(Context mContext) {
        this.mContext = mContext;
    }


    public void requestPermission(final OnPermissionListener permissionListener, final boolean isFinishActivity, String... permissions) {
        requestPermission(permissionListener, null, null, isFinishActivity, permissions);
    }


    public void requestPermission(final OnPermissionListener permissionListener, final OnPermissionCancleListener permissionCancleListener, final boolean isFinishActivity, String... permissions) {
        requestPermission(permissionListener, permissionCancleListener, null, isFinishActivity, permissions);
    }

    public void requestPermission(final OnPermissionListener permissionListener, final OnSettingListener settingListener, final boolean isFinishActivity, String... permissions) {
        requestPermission(permissionListener, null, settingListener, isFinishActivity, permissions);
    }

    /**
     * 申请权限
     * <p>
     * permissionListener 申请权限被允许之后的回调方法
     * isFinishActivity 当你拒绝权限之后，是否关闭当前的Activity
     * permissions需要申请的权限，可以是多个值
     */
    public void requestPermission(final OnPermissionListener permissionListener, final OnPermissionCancleListener permissionCancleListener, final OnSettingListener onSettingListener, final boolean isFinishActivity, String... permissions) {

        AndPermission.with(mContext)
                .runtime()
                .permission(permissions)
//                .rationale(new RuntimeRationale((BaseActivity) mContext, isFinishActivity))
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (null != permissionListener)
                            permissionListener.onPermissionSuccess();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {

                        if (AndPermission.hasAlwaysDeniedPermission(mContext, permissions)) {
                            showSettingDialog(mContext, onSettingListener, permissionCancleListener, isFinishActivity, permissions);
                        } else {

                            if (null != permissionCancleListener) {
                                permissionCancleListener.onCancle();
                            }

                            if (isFinishActivity) {
                                ((Activity) mContext).finish();
                            }
//                            List<String> permissionNames = Permission.transformText(mContext, permissions);
//                            final String join = TextUtils.join("\n", permissionNames);
//                            ToastUtils.showShort("您拒绝了" + join + "使用权限");
                        }
                    }
                })
                .start();
    }


    /**
     * 展示设置提示框
     */
    private void showSettingDialog(Context context, final OnSettingListener onSettingListener, final OnPermissionCancleListener permissionCancleListener, final boolean isFinishActivity, final List<String> permissions) {
        List<String> permissionNames = transformText(context, permissions);
        final String join = TextUtils.join("\n", permissionNames);

        String message = context.getString(R.string.message_permission_always_failed, join);

        if (context != null && context instanceof Activity) {
            final SpannableHelper helper = new SpannableHelper(message);

            helper.partLastTextViewColor(join, mContext.getResources().getColor(R.color.dark_green));

            DialogManager.getInstance().showPermissionDialog(context, context.getResources().getString(R.string.title_dialog), helper, context.getResources().getString(R.string.setting),
                    context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (null != onSettingListener) {
                                onSettingListener.toSetting();
                            }
                            setPermission();
                        }
                    }, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            if (null != permissionCancleListener) {
                                permissionCancleListener.onCancle();
                            }

                            if (isFinishActivity) {
                                ((Activity) mContext).finish();
                            }

//                        ToastUtils.showShort("您拒绝了" + join + "使用权限");
                        }
                    });
        } else {
            ToastUtils.showShort(message);
        }
    }


    /**
     * 跳转的应用的系统设置页面
     */
    private void setPermission() {
        AndPermission.with((Activity) mContext)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {
                        //从设置界面返回监听，再次检查权限
                        //这个回调在某些机型中不起作用，因此此处不使用  2018.11.6号 于长亮添加
//                        requestPermission(permissionListener, isFinishActivity, permissions);
//                        Toast.makeText(mContext, R.string.message_setting_comeback, Toast.LENGTH_SHORT).show();

                    }
                })
                .start();
    }


    public interface OnPermissionListener {
        void onPermissionSuccess();
    }

    public interface OnSettingListener {
        void toSetting();
    }

    public interface OnPermissionCancleListener {
        void onCancle();
    }


    /**
     * Turn permissions into text.
     */
    private List<String> transformText(Context context, List<String> permissions) {
        List<String> textList = new ArrayList<>();
        for (String permission : permissions) {
            switch (permission) {
                case READ_CALENDAR:
                case WRITE_CALENDAR: {
                    String message = context.getString(com.yanzhenjie.permission.R.string.permission_name_calendar);
                    if (!textList.contains(message)) {
                        textList.add(message);
                    }
                    break;
                }

                case CAMERA: {
                    String message = context.getString(com.yanzhenjie.permission.R.string.permission_name_camera);
                    if (!textList.contains(message)) {
                        textList.add(message);
                    }
                    break;
                }
                case READ_CONTACTS:
                case WRITE_CONTACTS:
                case GET_ACCOUNTS: {
                    String message = context.getString(com.yanzhenjie.permission.R.string.permission_name_contacts);
                    if (!textList.contains(message)) {
                        textList.add(message);
                    }
                    break;
                }
                case ACCESS_FINE_LOCATION:
                case ACCESS_COARSE_LOCATION: {
                    String message = context.getString(com.yanzhenjie.permission.R.string.permission_name_location);
                    if (!textList.contains(message)) {
                        textList.add(message);
                    }
                    break;
                }
                case RECORD_AUDIO: {
                    String message = context.getString(com.yanzhenjie.permission.R.string.permission_name_microphone);
                    if (!textList.contains(message)) {
                        textList.add(message);
                    }
                    break;
                }
                case READ_PHONE_STATE: {
                    String message = "手机识别码";
                    if (!textList.contains(message)) {
                        textList.add(message);
                    }
                    break;
                }
                case CALL_PHONE:
                case READ_CALL_LOG:
                case WRITE_CALL_LOG:
                case USE_SIP:
                case PROCESS_OUTGOING_CALLS: {
                    String message = context.getString(com.yanzhenjie.permission.R.string.permission_name_phone);
                    if (!textList.contains(message)) {
                        textList.add(message);
                    }
                    break;
                }
                case BODY_SENSORS: {
                    String message = context.getString(com.yanzhenjie.permission.R.string.permission_name_sensors);
                    if (!textList.contains(message)) {
                        textList.add(message);
                    }
                    break;
                }
                case SEND_SMS:
                case RECEIVE_SMS:
                case READ_SMS:
                case RECEIVE_WAP_PUSH:
                case RECEIVE_MMS: {
                    String message = context.getString(com.yanzhenjie.permission.R.string.permission_name_sms);
                    if (!textList.contains(message)) {
                        textList.add(message);
                    }
                    break;
                }
                case READ_EXTERNAL_STORAGE:
                case WRITE_EXTERNAL_STORAGE: {
                    String message = context.getString(com.yanzhenjie.permission.R.string.permission_name_storage);
                    if (!textList.contains(message)) {
                        textList.add(message);
                    }
                    break;
                }
            }
        }
        return textList;
    }
}
