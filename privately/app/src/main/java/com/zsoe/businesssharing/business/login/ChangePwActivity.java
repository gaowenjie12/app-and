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

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.BuildConfig;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.BrowserActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.commonview.ClearEditText;
import com.zsoe.businesssharing.commonview.DrawableTextView;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.KeyboardWatcher;


@RequiresPresenter(ChangePwPresenter.class)
public class ChangePwActivity extends BaseActivity<ChangePwPresenter> implements View.OnClickListener, KeyboardWatcher.SoftKeyboardStateListener {


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();


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

                boolean checked = checkBox.isChecked();
                if (!checked) {
                    ToastUtils.showShort("请勾选用户服务协议");
                    return;
                }


                DialogManager.getInstance().showNetLoadingView(mContext);
                getPresenter().resetpwd(s, s3, pass);

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
        }

    }

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loginTimer != null) {
            loginTimer.cancel();
        }
        keyboardWatcher.removeSoftKeyboardStateListener(this);
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

        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度

    }
}
