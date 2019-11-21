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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.orhanobut.logger.Logger;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.FancyUtils;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.business.main.MainActivity;
import com.zsoe.businesssharing.commonview.ClearEditText;
import com.zsoe.businesssharing.commonview.DrawableTextView;
import com.zsoe.businesssharing.easechat.DemoHelper;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.KeyboardWatcher;

import rx.functions.Action1;


/**
 * Created by WZH on 2017/3/25.
 */

@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener, KeyboardWatcher.SoftKeyboardStateListener{
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


    /**
     * 忘记密码？
     */
    private TextView mForgetPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitleRightText("首页", new Action1<View>() {
            @Override
            public void call(View view) {
                startActivity(new Intent(mContext, MainActivity.class));
            }
        });
        initView();
        initListener();
        keyboardWatcher = new KeyboardWatcher(findViewById(Window.ID_ANDROID_CONTENT));
        keyboardWatcher.addSoftKeyboardStateListener(this);

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

        mForgetPassword = (TextView) findViewById(R.id.forget_password);
        mForgetPassword.setOnClickListener(this);
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
            case R.id.forget_password:
                Intent intent = new Intent(mContext, ChangePwActivity.class);
                intent.putExtra(Config.INTENT_PARAMS1, 1);
                startActivity(intent);
                break;
        }
    }



    String account;
    String pass;

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
            FancyUtils.setUserPhone(account);
            finish();
            return;
        }

        String username, mima;

        if (TextUtils.isEmpty(account) && TextUtils.isEmpty(pass)) {
            username = loginUser.getUsername();
            mima = "123456";
        } else {
            username = account;
            mima = pass;
        }

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardWatcher.removeSoftKeyboardStateListener(this);
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

    private void uploadUserAvatar(String data) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                final String avatarUrl = DemoHelper.getInstance().getUserProfileManager().uploadUserAvatar(data);
            }
        }).start();
    }
}
