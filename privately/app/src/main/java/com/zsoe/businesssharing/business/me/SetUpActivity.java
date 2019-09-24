package com.zsoe.businesssharing.business.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.business.login.ChangePwActivity;
import com.zsoe.businesssharing.easechat.DemoHelper;
import com.zsoe.businesssharing.utils.DialogManager;

import java.util.Map;

@RequiresPresenter(LoginOutPresenter.class)
public class SetUpActivity extends BaseActivity<LoginOutPresenter> implements View.OnClickListener {

    private RelativeLayout mRlZhanghaoguanli;
    private RelativeLayout mRlQingchuhuanc;
    private RelativeLayout mRlGuanyuwomen;
    private RelativeLayout mRlJianchagengxin;
    /**
     * 退出登录
     */
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        initView();
        initTitleText("设置");
    }

    private void initView() {
        mRlZhanghaoguanli = (RelativeLayout) findViewById(R.id.rl_zhanghaoguanli);
        mRlZhanghaoguanli.setOnClickListener(this);
        mRlQingchuhuanc = (RelativeLayout) findViewById(R.id.rl_qingchuhuanc);
        mRlQingchuhuanc.setOnClickListener(this);
        mRlGuanyuwomen = (RelativeLayout) findViewById(R.id.rl_guanyuwomen);
        mRlGuanyuwomen.setOnClickListener(this);
        mRlJianchagengxin = (RelativeLayout) findViewById(R.id.rl_jianchagengxin);
        mRlJianchagengxin.setOnClickListener(this);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_zhanghaoguanli:
                startActivity(new Intent(mContext, ChangePwActivity.class));
                break;
            case R.id.rl_qingchuhuanc:
                break;
            case R.id.rl_guanyuwomen:
                startActivity(new Intent(mContext, AboutUsActivity.class));
                break;
            case R.id.rl_jianchagengxin:
                break;
            case R.id.btn_login:
                // 确认
                DialogManager.getInstance().showNetLoadingView(mContext);
                getPresenter().logout();
                break;
        }
    }


    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
//            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mContext, "成功了", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mContext, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    public void loginOut() {

        if (DApplication.getInstance().getLoginUser().getPlatform().equals("")) {

        }
        DApplication.getInstance().exit();
        DApplication.getInstance().startLogin();

        UMShareAPI.get(mContext).deleteOauth(SetUpActivity.this, SHARE_MEDIA.QQ, authListener);

        DemoHelper.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {

                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(mContext, "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
