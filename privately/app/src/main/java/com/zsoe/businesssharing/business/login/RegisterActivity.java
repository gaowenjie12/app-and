package com.zsoe.businesssharing.business.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.orhanobut.logger.Logger;
import com.tencent.tauth.UiError;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zsoe.businesssharing.BuildConfig;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.BrowserActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.FancyUtils;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.business.main.MainActivity;
import com.zsoe.businesssharing.commonview.ClearEditText;
import com.zsoe.businesssharing.commonview.DrawableTextView;
import com.zsoe.businesssharing.easechat.DemoHelper;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.KeyboardWatcher;
import com.zsoe.businesssharing.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


@RequiresPresenter(RegisterPresenter.class)
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements View.OnClickListener, KeyboardWatcher.SoftKeyboardStateListener, QQLoginManager.QQLoginListener {


    private int screenHeight = 0;//屏幕高度
    private float scale = 0.8f; //logo缩放比例

    private KeyboardWatcher keyboardWatcher;
    private ImageView mClose;
    /**
     * 注册
     */
    private DrawableTextView mLogo;
    /**
     *
     */
    private ClearEditText mEtMobile;
    /**
     *
     */
    private ClearEditText mEtCode;
    /**
     * 获取验证码
     */
    private TextView mTvGetcode;
    /**
     *
     */
    private ClearEditText mEtPassword;
    /**
     * 立即注册
     */
    private Button mBtnRegister;
    private LinearLayout mBody;

    private CountDownTimer loginTimer;


    private ImageView mLoginQq;
    private ImageView mLoginWeixin;
    private ImageView mLoginWeibo;

    private QQLoginManager qqLoginManager;
    /**
     * 《隐私政策》
     */
    private TextView mTvYinsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        qqLoginManager = new QQLoginManager("1109933226", this);
        umShareAPI = UMShareAPI.get(this);

        loginTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                mTvGetcode.setText("重新发送（" + (millisUntilFinished / 1000) + "s）");
                mTvGetcode.setTextColor(getResources().getColor(R.color.text_9));

            }

            public void onFinish() {
                mTvGetcode.setEnabled(true);
                mTvGetcode.setTextColor(getResources().getColor(R.color.dark_green));
                mTvGetcode.setText("发送验证码");
            }
        };

        keyboardWatcher = new KeyboardWatcher(findViewById(Window.ID_ANDROID_CONTENT));
        keyboardWatcher.addSoftKeyboardStateListener(this);

        findViewById(R.id.tv_xieyi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BrowserActivity.class);
                intent.putExtra(Config.INTENT_PARAMS1, BuildConfig.ENDPOINT + "article/agreement");
                startActivity(intent);
            }
        });
    }

    boolean isQQLoading = false;

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.close:
                finish();
                break;
            case R.id.btn_register:

                String s = mEtMobile.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    ToastUtils.showShort("请填写手机号");
                    return;
                }

                String s3 = mEtCode.getText().toString();
                if (TextUtils.isEmpty(s3)) {
                    ToastUtils.showShort("请填写验证码");
                    return;
                }

                String pass = mEtPassword.getText().toString();
                if (TextUtils.isEmpty(pass)) {
                    ToastUtils.showShort("请填写密码");
                    return;
                }

                if (!checkBox.isChecked()) {
                    ToastUtils.showShort("请勾选《用户服务协议》和《隐私政策》");

                    return;
                }


                DialogManager.getInstance().showNetLoadingView(mContext);
                getPresenter().register(s, s3, pass);

                break;
            case R.id.tv_getcode:

                String s4 = mEtMobile.getText().toString();
                if (TextUtils.isEmpty(s4)) {
                    ToastUtils.showShort("请填写手机号");
                    return;
                }


                DialogManager.getInstance().showNetLoadingView(mContext);
                getPresenter().getSms(s4, "register");
                break;
            case R.id.login_qq:
                if (!checkBox.isChecked()) {
                    ToastUtils.showShort("请勾选《用户服务协议》和《隐私政策》");

                    return;
                }
                isQQLoading = true;
                DialogManager.getInstance().showNetLoadingView(mContext, "正在等待授权");
                qqLoginManager.launchQQLogin();
                break;
            case R.id.login_weixin:

                if (!checkBox.isChecked()) {
                    ToastUtils.showShort("请勾选《用户服务协议》和《隐私政策》");

                    return;
                }

                boolean install2 = umShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN);
                if (!install2) {
                    ToastUtils.showShort("请安装微信客户端");
                    return;
                }
                umShareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.login_weibo:

                if (!checkBox.isChecked()) {
                    ToastUtils.showShort("请勾选《用户服务协议》和《隐私政策》");
                    return;
                }

                boolean install3 = umShareAPI.isInstall(this, SHARE_MEDIA.SINA);
                if (!install3) {
                    ToastUtils.showShort("请安装微博客户端");
                    return;
                }
                umShareAPI.getPlatformInfo(this, SHARE_MEDIA.SINA, authListener);
                break;
            case R.id.tv_yinsi:
                Intent intent = new Intent(mContext, BrowserActivity.class);
                intent.putExtra(Config.INTENT_PARAMS1, BuildConfig.ENDPOINT + "article/agreement");
                startActivity(intent);
                break;
        }

    }


    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            DialogManager.getInstance().showNetLoadingView(mContext, "正在等待授权");
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            String s = platform.toString();


            String uid = data.get("uid");
            String iconurl = data.get("iconurl");
            String name = data.get("name");


            String platformStr = "";
            if (s.equals("WEIXIN")) {
                platformStr = "wechat";
            } else if (s.equals("SINA")) {
                platformStr = "weibo";
            }

            DialogManager.getInstance().dismissNetLoadingView();
            getPresenter().third(platformStr, uid, name, iconurl);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            DialogManager.getInstance().dismissNetLoadingView();
            Toast.makeText(mContext, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
            LogUtil.e("授权失败===" + t.getMessage());
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            DialogManager.getInstance().dismissNetLoadingView();
            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };


    public void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void chenggong() {
        // 发生成功
        loginTimer.start();
        mTvGetcode.setEnabled(false);
    }


    /**
     * 缩小
     *
     * @param view
     */
    public void zoomIn(final View view, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX).with(mAnimatorScaleY);

        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();

    }

    /**
     * f放大
     *
     * @param view
     */
    public void zoomOut(final View view) {
        if (view.getTranslationY() == 0) {
            return;
        }
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();

    }

    UMShareAPI umShareAPI;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginTimer != null) {
            loginTimer.cancel();
        }
        keyboardWatcher.removeSoftKeyboardStateListener(this);
        UMShareAPI.get(this).release();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (isQQLoading == true) {
            qqLoginManager.onActivityResultData(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardSize) {
        int[] location = new int[2];
        mBody.getLocationOnScreen(location); //获取body在屏幕中的坐标,控件左上角
        int x = location[0];
        int y = location[1];
        int bottom = screenHeight - (y + mBody.getHeight());
        if (keyboardSize > bottom) {
            ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mBody, "translationY", 0.0f, -(keyboardSize - bottom));
            mAnimatorTranslateY.setDuration(300);
            mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimatorTranslateY.start();
            zoomIn(mLogo, keyboardSize - bottom);

        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mBody, "translationY", mBody.getTranslationY(), 0);
        mAnimatorTranslateY.setDuration(300);
        mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorTranslateY.start();
        zoomOut(mLogo);
    }

    CheckBox checkBox;

    private void initView() {
        mClose = (ImageView) findViewById(R.id.close);
        mClose.setOnClickListener(this);
        mLogo = (DrawableTextView) findViewById(R.id.logo);
        mEtMobile = (ClearEditText) findViewById(R.id.et_mobile);
        mEtCode = (ClearEditText) findViewById(R.id.et_code);
        mTvGetcode = (TextView) findViewById(R.id.tv_getcode);
        mTvGetcode.setOnClickListener(this);
        mEtPassword = (ClearEditText) findViewById(R.id.et_password);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(this);
        mBody = (LinearLayout) findViewById(R.id.body);
        checkBox = (CheckBox) findViewById(R.id.checkbox);

        mLoginQq = (ImageView) findViewById(R.id.login_qq);
        mLoginQq.setOnClickListener(this);
        mLoginWeixin = (ImageView) findViewById(R.id.login_weixin);
        mLoginWeixin.setOnClickListener(this);
        mLoginWeibo = (ImageView) findViewById(R.id.login_weibo);
        mLoginWeibo.setOnClickListener(this);

        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度

        mTvYinsi = (TextView) findViewById(R.id.tv_yinsi);
        mTvYinsi.setOnClickListener(this);
    }


    @Override
    public void onQQLoginSuccess(JSONObject jsonObject, QQLoginManager.UserAuthInfo authInfo) {
        DialogManager.getInstance().dismissNetLoadingView();
        isQQLoading = false;
        String openid = "", nickname = "", figureurl_2 = "";
        try {
            openid = jsonObject.getString("open_id");
            nickname = jsonObject.getString("nickname");
            figureurl_2 = jsonObject.getString("figureurl_2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getPresenter().third("qq", openid, nickname, figureurl_2);
        Logger.e(jsonObject.toString());
    }

    @Override
    public void onQQLoginCancel() {
        isQQLoading = false;
        DialogManager.getInstance().dismissNetLoadingView();
        ToastUtils.showShort("登录取消");

    }

    @Override
    public void onQQLoginError(UiError uiError) {
        isQQLoading = false;
        ToastUtils.showShort("登录出错！" + uiError.toString());
        Logger.e(uiError.toString());
        DialogManager.getInstance().dismissNetLoadingView();
    }

    public void loginSuccess(LoginUser loginUser) {

        Logger.e(loginUser.toString());

        if (DemoHelper.getInstance().isLoggedIn()) {

            // 登陆成功，保存用户昵称与头像URL
            SPUtils.getInstance().put("name", loginUser.getNickname());
            SPUtils.getInstance().put("logoUrl", loginUser.getAvatar());

            DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(loginUser.getNickname());
            DemoHelper.getInstance().getUserProfileManager().uploadUserAvatar(loginUser.getAvatar());
            DemoHelper.getInstance().setCurrentUserName(loginUser.getUsername()); // 环信Id
            uploadUserAvatar(loginUser.getAvatar());

            // ** manually load all local groups and conversation
            EMClient.getInstance().groupManager().loadAllGroups();
            EMClient.getInstance().chatManager().loadAllConversations();


            // get user's info (this should be get from App's server or 3rd party service)
//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
            startActivity(new Intent(mContext, MainActivity.class));
            FancyUtils.setLoginUser(loginUser);
            FancyUtils.setUserPhone(loginUser.getUsername());
            finish();
            return;
        }

        String username, mima;

        username = loginUser.getUsername();
        mima = "123456";

        EMClient.getInstance().login(username, mima, new EMCallBack() {

            @Override
            public void onSuccess() {

                // 登陆成功，保存用户昵称与头像URL
                SPUtils.getInstance().put("name", loginUser.getNickname());
                SPUtils.getInstance().put("logoUrl", loginUser.getAvatar());

                DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(loginUser.getNickname());
                DemoHelper.getInstance().getUserProfileManager().uploadUserAvatar(loginUser.getAvatar());
                DemoHelper.getInstance().setCurrentUserName(loginUser.getUsername()); // 环信Id

                uploadUserAvatar(loginUser.getAvatar());

                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();


                // get user's info (this should be get from App's server or 3rd party service)
//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                startActivity(new Intent(mContext, MainActivity.class));
                FancyUtils.setLoginUser(loginUser);
                FancyUtils.setUserPhone(loginUser.getUsername());
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(final int code, final String message) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), message,
                                Toast.LENGTH_SHORT).show();
//
//                                Toast.makeText(getApplicationContext(), "登录失败请重试",
//                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void uploadUserAvatar(String data) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                final String avatarUrl = DemoHelper.getInstance().getUserProfileManager().uploadUserAvatar(data);
            }
        }).start();
    }
}
