package com.zsoe.businesssharing.business.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.orhanobut.logger.Logger;
import com.tencent.tauth.UiError;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
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


/**
 * Created by WZH on 2017/3/25.
 */

@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener, KeyboardWatcher.SoftKeyboardStateListener, QQLoginManager.QQLoginListener {
    private DrawableTextView logo;
    private EditText et_mobile;
    private EditText et_password;

    private Button btn_login;
    private TextView forget_password;

    private int screenHeight = 0;//屏幕高度
    private float scale = 0.8f; //logo缩放比例
    private View body;
    private KeyboardWatcher keyboardWatcher;
    /**
     * 登录
     */
    private Button mBtnLogin;
    /**
     *
     */
    private ClearEditText mEtMobile;
    /**
     *
     */
    private ClearEditText mEtPassword;
    private ImageView mLoginQq;
    private ImageView mLoginWeixin;
    private ImageView mLoginWeibo;

    private QQLoginManager qqLoginManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        qqLoginManager = new QQLoginManager("1109739836", this);
        initView();
        initListener();
        umShareAPI = UMShareAPI.get(this);
        keyboardWatcher = new KeyboardWatcher(findViewById(Window.ID_ANDROID_CONTENT));
        keyboardWatcher.addSoftKeyboardStateListener(this);

    }

    private void initView() {
        logo = (DrawableTextView) findViewById(R.id.logo);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        forget_password = (TextView) findViewById(R.id.forget_password);
        body = findViewById(R.id.body);
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度

        findViewById(R.id.close).setOnClickListener(this);
        findViewById(R.id.regist).setOnClickListener(this);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        mEtMobile = (ClearEditText) findViewById(R.id.et_mobile);
        mEtPassword = (ClearEditText) findViewById(R.id.et_password);

        mEtMobile.setText(FancyUtils.getUserPhone());
        mLoginQq = (ImageView) findViewById(R.id.login_qq);
        mLoginQq.setOnClickListener(this);
        mLoginWeixin = (ImageView) findViewById(R.id.login_weixin);
        mLoginWeixin.setOnClickListener(this);
        mLoginWeibo = (ImageView) findViewById(R.id.login_weibo);
        mLoginWeibo.setOnClickListener(this);
    }

    private void initListener() {
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(LoginActivity.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    et_password.setSelection(s.length());
                }
            }
        });
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
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.close:
                finish();
                break;

            case R.id.regist:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
            case R.id.btn_login:

                account = mEtMobile.getText().toString();
                if (TextUtils.isEmpty(account)) {
                    ToastUtils.showShort("请填写手机号");
                    return;
                }


                pass = mEtPassword.getText().toString();
                if (TextUtils.isEmpty(pass)) {
                    ToastUtils.showShort("请填写密码");
                    return;
                }

                DialogManager.getInstance().showNetLoadingView(this);
                getPresenter().login(account, pass);

                break;
            case R.id.login_qq:
                DialogManager.getInstance().showNetLoadingView(mContext, "正在等待授权");
                qqLoginManager.launchQQLogin();
                break;
            case R.id.login_weixin:
                boolean install2 = umShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN);
                if (!install2) {
                    ToastUtils.showShort("请安装微信客户端");
                    return;
                }
                umShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
                break;
            case R.id.login_weibo:
                boolean install3 = umShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN);
                if (!install3) {
                    ToastUtils.showShort("请安装微博客户端");
                    return;
                }
                umShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, authListener);
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
            if (s.equals(SHARE_MEDIA.WEIXIN)) {
                platformStr = "wechat";
            } else if (s.equals(SHARE_MEDIA.SINA)) {
                platformStr = "weibo";
            }

            DialogManager.getInstance().dismissNetLoadingView();
            getPresenter().third(platformStr, uid, name, iconurl);
            Logger.e(data.toString());

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

    String account;
    String pass;

    public void loginSuccess(LoginUser loginUser) {

        if (DemoHelper.getInstance().isLoggedIn()) {

            // 登陆成功，保存用户昵称与头像URL
            SPUtils.getInstance().put("name", loginUser.getNickname());
            SPUtils.getInstance().put("logoUrl", loginUser.getAvatar());

            DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(loginUser.getNickname());
            DemoHelper.getInstance().getUserProfileManager().uploadUserAvatar(loginUser.getAvatar());
            DemoHelper.getInstance().setCurrentUserName(loginUser.getUsername()); // 环信Id

            // ** manually load all local groups and conversation
            EMClient.getInstance().groupManager().loadAllGroups();
            EMClient.getInstance().chatManager().loadAllConversations();


            // get user's info (this should be get from App's server or 3rd party service)
//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
            startActivity(new Intent(mContext, MainActivity.class));
            FancyUtils.setLoginUser(loginUser);
            FancyUtils.setUserPhone(account);
            finish();
            return;
        }

        EMClient.getInstance().login(account, pass, new EMCallBack() {

            @Override
            public void onSuccess() {

                // 登陆成功，保存用户昵称与头像URL
                SPUtils.getInstance().put("name", loginUser.getNickname());
                SPUtils.getInstance().put("logoUrl", loginUser.getAvatar());

                DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(loginUser.getNickname());
                DemoHelper.getInstance().getUserProfileManager().uploadUserAvatar(loginUser.getAvatar());
                DemoHelper.getInstance().setCurrentUserName(loginUser.getUsername()); // 环信Id


                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();


                // get user's info (this should be get from App's server or 3rd party service)
//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                startActivity(new Intent(mContext, MainActivity.class));
                FancyUtils.setLoginUser(loginUser);
                FancyUtils.setUserPhone(account);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(final int code, final String message) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        keyboardWatcher.removeSoftKeyboardStateListener(this);
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
        qqLoginManager.onActivityResultData(requestCode, resultCode, data);
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardSize) {
        int[] location = new int[2];
        body.getLocationOnScreen(location); //获取body在屏幕中的坐标,控件左上角
        int x = location[0];
        int y = location[1];
        int bottom = screenHeight - (y + body.getHeight());
        if (keyboardSize > bottom) {
            ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(body, "translationY", 0.0f, -(keyboardSize - bottom));
            mAnimatorTranslateY.setDuration(300);
            mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimatorTranslateY.start();
            zoomIn(logo, keyboardSize - bottom);

        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(body, "translationY", body.getTranslationY(), 0);
        mAnimatorTranslateY.setDuration(300);
        mAnimatorTranslateY.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorTranslateY.start();
        zoomOut(logo);
    }

    @Override
    public void onQQLoginSuccess(JSONObject jsonObject, QQLoginManager.UserAuthInfo authInfo) {
        DialogManager.getInstance().dismissNetLoadingView();

        String openid = "", nickname = "", figureurl_2 = "";
        try {
            openid = jsonObject.getString("openid");
            nickname = jsonObject.getString("nickname");
            figureurl_2 = jsonObject.getString("figureurl_2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getPresenter().third("qq", openid, nickname, figureurl_2);


        ToastUtils.showShort(jsonObject.toString());
        Logger.e(jsonObject.toString());
    }

    @Override
    public void onQQLoginCancel() {
        DialogManager.getInstance().dismissNetLoadingView();
        ToastUtils.showShort("登录取消");

    }

    @Override
    public void onQQLoginError(UiError uiError) {
        ToastUtils.showShort("登录出错！" + uiError.toString());

        DialogManager.getInstance().dismissNetLoadingView();
    }
}
