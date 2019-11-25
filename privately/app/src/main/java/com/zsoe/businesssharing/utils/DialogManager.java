package com.zsoe.businesssharing.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zsoe.businesssharing.BuildConfig;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BrowserActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.commonview.update.UpdateInfo;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019/4/25 11:01
 * @version:
 * @类说明:
 */
public class DialogManager {


    private static AnimationSet mModalInAnim;

    private static DialogManager dialogManager;

    public static DialogManager getInstance() {
        if (dialogManager == null) {
            synchronized (DialogManager.class) {
                if (dialogManager == null) {
                    dialogManager = new DialogManager();
                    mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(DApplication.getInstance().getApplicationContext(), R.anim.modal_in);
                }
            }
        }
        return dialogManager;
    }

    private IProgressDialog mProgressDialog;


    /**
     * 显示一个加载对话框，
     *
     * @param context
     * @param hint    提示文字
     */
    public void showNetLoadingView(Context context, String hint) {
        mProgressDialog = new IProgressDialog(context);
        mProgressDialog.setMessage(hint);
        mProgressDialog.show();
    }

    /**
     * 关闭对话框
     */
    public void dismissNetLoadingView() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }


    private Dialog permissionDialog;


    /**
     * 关闭权限对话框
     */
    public void dismissPermissionDialog() {
        if (permissionDialog != null && permissionDialog.isShowing()) {
            permissionDialog.dismiss();
        }
        permissionDialog = null;
    }


    public void showPermissionDialog(Context context, String title, CharSequence info, String btnOkStr, String btnDissmissStr, final DialogInterface.OnClickListener listener, final DialogInterface.OnCancelListener onCancelListener) {

        if (null != permissionDialog && permissionDialog.isShowing()) {
            return;
        }

        permissionDialog = new Dialog(context, R.style.MyDialog2);
        permissionDialog.setContentView(R.layout.dialog_permission_layout);

        View mDialogView = permissionDialog.getWindow().getDecorView().findViewById(android.R.id.content);
        mDialogView.startAnimation(mModalInAnim);

//        LinearLayout ll_main_layout = (LinearLayout) dialog.findViewById(R.id.ll_main_layout);
        TextView tv_dialog_title = (TextView) permissionDialog.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_text = (TextView) permissionDialog.findViewById(R.id.tv_dialog_text);
        TextView btn_ok = (TextView) permissionDialog.findViewById(R.id.btn_ok);
        TextView btn_dismiss = (TextView) permissionDialog.findViewById(R.id.btn_dismiss);
//        dialog.findViewById(R.id.dialogCloseBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        tv_dialog_title.setText(title);
        tv_dialog_text.setText(info);

        if (!EmptyUtil.isEmpty(btnOkStr)) {
            btn_ok.setText(btnOkStr);
        } else {
            btn_ok.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(btnDissmissStr)) {
            btn_dismiss.setText(btnDissmissStr);
        } else {
            btn_dismiss.setVisibility(View.GONE);
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(permissionDialog, DialogInterface.BUTTON_POSITIVE);
            }
        });

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCancelListener.onCancel(permissionDialog);
                permissionDialog.dismiss();
            }
        });
        // 设置全屏大小
        int[] screenRect = getScreenRect((Activity) context);

        permissionDialog.getWindow().setLayout(screenRect[0], screenRect[1]);
        permissionDialog.setCancelable(false);

        if (context != null && (!((Activity) context).isFinishing())) {
            //show dialog
            permissionDialog.show();

        }
    }

    /**
     * 获取当前屏幕的全屏大小
     *
     * @param activity
     * @return
     */
    public static int[] getScreenRect(Activity activity) {

        WindowManager manager = activity.getWindowManager();
        int sreenW = manager.getDefaultDisplay().getWidth();
        int sreenH = manager.getDefaultDisplay().getHeight();
        return new int[]{sreenW, sreenH};
    }


    public void showNetLoadingView(Context context) {
        showNetLoadingView(context, "正在加载,请稍后...");
    }


    public static void showNormalDialog(Context context, String title, String info, String btnOkStr, String btnDissmissStr, final DialogInterface.OnClickListener listener) {
        showNormalDialog(context, title, info, btnOkStr, btnDissmissStr, listener, null);
    }

    public static void showNormalDialog(Context context, String title, String info, String btnOkStr, String btnDissmissStr, final DialogInterface.OnClickListener listener, final DialogInterface.OnDismissListener dismissListener) {
        final Dialog dialog = new Dialog(context, R.style.MyDialog2);
        dialog.setContentView(R.layout.dialog_normal_layout);

        View mDialogView = dialog.getWindow().getDecorView().findViewById(android.R.id.content);
        mDialogView.startAnimation(mModalInAnim);

//        LinearLayout ll_main_layout = (LinearLayout) dialog.findViewById(R.id.ll_main_layout);
        TextView tv_dialog_title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_text = (TextView) dialog.findViewById(R.id.tv_dialog_text);
        TextView btn_ok = (TextView) dialog.findViewById(R.id.btn_ok);
        TextView btn_dismiss = (TextView) dialog.findViewById(R.id.btn_dismiss);
        dialog.findViewById(R.id.vline).setVisibility((EmptyUtil.isEmpty(btnOkStr) || EmptyUtil.isEmpty(btnDissmissStr)) ? View.GONE : View.VISIBLE);
//        dialog.findViewById(R.id.dialogCloseBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        tv_dialog_title.setText(title);
        tv_dialog_text.setText(info);

        if (!EmptyUtil.isEmpty(btnOkStr)) {
            btn_ok.setText(btnOkStr);
        } else {
            btn_ok.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(btnDissmissStr)) {
            btn_dismiss.setText(btnDissmissStr);
        } else {
            btn_dismiss.setVisibility(View.GONE);
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
            }
        });

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // 设置全屏大小
        int[] screenRect = getScreenRect((Activity) context);

        dialog.getWindow().setLayout(screenRect[0], screenRect[1]);
        if (dismissListener != null) {
            dialog.setOnDismissListener(dismissListener);
        }
        if (context != null && (!((Activity) context).isFinishing())) {
            //show dialog
            dialog.show();

        }
    }


    public static void showSMDialog(Context context, String title, String btnOkStr, String btnDissmissStr, final DialogInterface.OnClickListener listener, final View.OnClickListener onClickListener) {
        final Dialog dialog = new Dialog(context, R.style.MyDialog2);
        dialog.setContentView(R.layout.dialog_shuoming_layout);

        View mDialogView = dialog.getWindow().getDecorView().findViewById(android.R.id.content);
        mDialogView.startAnimation(mModalInAnim);

//        LinearLayout ll_main_layout = (LinearLayout) dialog.findViewById(R.id.ll_main_layout);
        TextView tv_dialog_title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_text = (TextView) dialog.findViewById(R.id.tv_dialog_text);
        TextView btn_ok = (TextView) dialog.findViewById(R.id.btn_ok);
        TextView btn_dismiss = (TextView) dialog.findViewById(R.id.btn_dismiss);
        dialog.findViewById(R.id.vline).setVisibility((EmptyUtil.isEmpty(btnOkStr) || EmptyUtil.isEmpty(btnDissmissStr)) ? View.GONE : View.VISIBLE);
        tv_dialog_title.setText(title);


        String target = "请你务必审慎阅读，充分理解 ‘服务协议’和‘隐私政策’各条款，为了向您提供使用服务，我们需要收集您的注册信息，您可以在设置中查看相应条款。\n" +
                "您可阅读《用户服务协议》和《隐私政策》了解详细信息。如您同意，请点击“同意”开始接受我们的服务。";
        String xieyiStr = "《用户服务协议》";
        String yinsiStr = "《隐私政策》";
        SpannableString spannableString = new SpannableString(target);

        // 起始位置
        int startIndex = target.indexOf(xieyiStr);
        // 终止位置
        int endIndex = startIndex + xieyiStr.length();

        // 起始位置
        int startIndex2 = target.indexOf(yinsiStr);
        // 终止位置
        int endIndex2 = startIndex2 + yinsiStr.length();

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(context, BrowserActivity.class);
                intent.putExtra(Config.INTENT_PARAMS1, BuildConfig.ENDPOINT + "article/agreement");
                context.startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(context.getResources().getColor(R.color.text_expand_color));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(context, BrowserActivity.class);
                intent.putExtra(Config.INTENT_PARAMS1, BuildConfig.ENDPOINT + "article/agreement");
                context.startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(context.getResources().getColor(R.color.text_expand_color));
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };


        spannableString.setSpan(clickableSpan,startIndex,endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan2,startIndex2,endIndex2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_dialog_text.setText(spannableString);
        tv_dialog_text.setMovementMethod(LinkMovementMethod.getInstance());

        if (!EmptyUtil.isEmpty(btnOkStr)) {
            btn_ok.setText(btnOkStr);
        } else {
            btn_ok.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(btnDissmissStr)) {
            btn_dismiss.setText(btnDissmissStr);
        } else {
            btn_dismiss.setVisibility(View.GONE);
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
            }
        });

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
                dialog.dismiss();
            }
        });
        // 设置全屏大小
        int[] screenRect = getScreenRect((Activity) context);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(screenRect[0], screenRect[1]);

        if (context != null && (!((Activity) context).isFinishing())) {
            //show dialog
            dialog.show();

        }
    }


    public static void showNewVersionDialog(Context context, UpdateInfo updateInfo, final DialogInterface.OnClickListener listener) {
        final Dialog dialog = new Dialog(context, R.style.MyDialog2);
        dialog.setContentView(R.layout.dialog_normal_layout);

        View mDialogView = dialog.getWindow().getDecorView().findViewById(android.R.id.content);
        mDialogView.startAnimation(mModalInAnim);

//        LinearLayout ll_main_layout = (LinearLayout) dialog.findViewById(R.id.ll_main_layout);
        TextView tv_dialog_title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
        TextView tv_dialog_text = (TextView) dialog.findViewById(R.id.tv_dialog_text);
        TextView btn_ok = (TextView) dialog.findViewById(R.id.btn_ok);
        TextView btn_dismiss = (TextView) dialog.findViewById(R.id.btn_dismiss);

        tv_dialog_title.setText("新版本：" + updateInfo.versionName);
        tv_dialog_text.setText(updateInfo.updateContent);

        btn_ok.setText("立即更新");

        if (updateInfo.isForce) {
            dialog.findViewById(R.id.vline).setVisibility(View.GONE);
            btn_dismiss.setVisibility(View.GONE);
        } else {
            btn_dismiss.setText("以后再说");
        }

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
            }
        });

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // 设置全屏大小
        int[] screenRect = getScreenRect((Activity) context);

        dialog.getWindow().setLayout(screenRect[0], screenRect[1]);

        if (context != null && (!((Activity) context).isFinishing())) {
            //show dialog
            dialog.show();

        }
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }


    public static void showMakeSureOrderDialog(Context context, String title, String warning, String content, String button_text, final DialogInterface.OnClickListener listener) {
        final Dialog dialog = new Dialog(context, R.style.MyDialog2);
        dialog.setContentView(R.layout.dialog_makesureorder_layout);

        View mDialogView = dialog.getWindow().getDecorView().findViewById(android.R.id.content);
        mDialogView.startAnimation(mModalInAnim);

        TextView tv_title = (TextView) dialog.findViewById(R.id.title);
        TextView tv_warning = (TextView) dialog.findViewById(R.id.tv_warning);
        TextView tv_content = (TextView) dialog.findViewById(R.id.tv_content);
        Button bt_ok = (Button) dialog.findViewById(R.id.bt_ok);
        ImageView iv_close = (ImageView) dialog.findViewById(R.id.iv_close);


        tv_title.setText(title);

        if (!TextUtils.isEmpty(warning)) {
            tv_warning.setText(warning);
            tv_warning.setVisibility(View.VISIBLE);
        } else {
            tv_warning.setVisibility(View.GONE);
        }


        tv_content.setText(content);

        bt_ok.setText(button_text);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // 设置全屏大小
        int[] screenRect = getScreenRect((Activity) context);

        dialog.getWindow().setLayout(screenRect[0], screenRect[1]);
        if (context != null && (!((Activity) context).isFinishing())) {
            //show dialog
            dialog.show();

        }
    }


    public static void showCanelDialog(Context context, String info, final DialogInterface.OnClickListener listener) {
        final Dialog dialog = new Dialog(context, R.style.MyDialog2);
        dialog.setContentView(R.layout.dialog_canel_layout);

        View mDialogView = dialog.getWindow().getDecorView().findViewById(android.R.id.content);
        mDialogView.startAnimation(mModalInAnim);

        TextView tv_dialog_text = (TextView) dialog.findViewById(R.id.tv_dialog_text);
        TextView btn_ok = (TextView) dialog.findViewById(R.id.btn_ok);
        TextView btn_dismiss = (TextView) dialog.findViewById(R.id.btn_dismiss);

        tv_dialog_text.setText(info);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
            }
        });

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // 设置全屏大小
        int[] screenRect = getScreenRect((Activity) context);

        dialog.getWindow().setLayout(screenRect[0], screenRect[1]);

        if (context != null && (!((Activity) context).isFinishing())) {
            //show dialog
            dialog.show();

        }
    }

}
