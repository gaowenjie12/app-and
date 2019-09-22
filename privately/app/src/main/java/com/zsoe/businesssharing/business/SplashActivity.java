package com.zsoe.businesssharing.business;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.FancyUtils;
import com.zsoe.businesssharing.business.login.LoginActivity;
import com.zsoe.businesssharing.business.main.MainActivity;
import com.zsoe.businesssharing.easechat.DemoHelper;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.permission.OpenPermission2;

public class SplashActivity extends BaseActivity {

    private TimeCount timeCount = new TimeCount(1000, 1000);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        //定位权限
        new OpenPermission2(mContext).requestPermission(new OpenPermission2.OnPermissionListener() {
            @Override
            public void onPermissionSuccess() {
                timeCount.start();
            }
        }, new OpenPermission2.OnPermissionCancleListener() {
            @Override
            public void onCancle() {
                ToastUtils.showShort("请允许使用权限");
            }
        }, new OpenPermission2.OnSettingListener() {
            @Override
            public void toSetting() {
                DialogManager.getInstance().dismissPermissionDialog();
                finish();
            }
        }, true, OpenPermission2.INIT_PERMISSION);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 一个倒计时的内部类
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发

            //如果加载过引导页，调整到首页
            startMainPage();

//            startActivity(new Intent(mContext, MainActivity.class));
//
//            boolean wel = PreferencesUtils.getInstance().isWel();
//            if (wel) {
//                //如果加载过引导页，调整到首页
//                startMainPage();
//            } else {
//                startActivity(new Intent(mContext, WelcomeActivity.class));
//            }
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
//            if (tv_time != null) {
//                tv_time.setText(millisUntilFinished / 1000 + "s  跳过");
//            }
        }
    }

    private void startMainPage() {
        //判断用户状态，选择跳转的activity
        if (null != FancyUtils.getLoginUser()) {
            startActivity(new Intent(mContext, MainActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            public void run() {
                if (DemoHelper.getInstance().isLoggedIn()) {
                    // auto login mode, make sure all group and conversation is loaed before enter the main screen
                    long start = System.currentTimeMillis();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    EMClient.getInstance().groupManager().loadAllGroups();
                }
            }
        }).start();

    }
}
