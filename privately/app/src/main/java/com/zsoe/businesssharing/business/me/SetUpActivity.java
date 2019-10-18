package com.zsoe.businesssharing.business.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.hyphenate.EMCallBack;
import com.tencent.tauth.UiError;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.VersionBean;
import com.zsoe.businesssharing.business.login.ChangePwActivity;
import com.zsoe.businesssharing.business.login.QQLoginManager;
import com.zsoe.businesssharing.commonview.update.UpdateInfo;
import com.zsoe.businesssharing.commonview.update.UpdateManager;
import com.zsoe.businesssharing.easechat.DemoHelper;
import com.zsoe.businesssharing.utils.DialogManager;

import org.json.JSONObject;

import java.util.Map;

@RequiresPresenter(LoginOutPresenter.class)
public class SetUpActivity extends BaseActivity<LoginOutPresenter> implements View.OnClickListener, QQLoginManager.QQLoginListener {

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
                DialogManager.getInstance().showNetLoadingView(mContext);
                getPresenter().check_version();
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
//            Toast.makeText(mContext, "成功了", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            SocializeUtils.safeCloseDialog(dialog);
//            Toast.makeText(mContext, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
//            SocializeUtils.safeCloseDialog(dialog);
//            Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    public void loginOut() {

        if (DApplication.getInstance().getLoginUser().getPlatform().equals("weibo")) {
            UMShareAPI.get(mContext).deleteOauth(SetUpActivity.this, SHARE_MEDIA.SINA, authListener);
        } else if (DApplication.getInstance().getLoginUser().getPlatform().equals("wechat")) {
            UMShareAPI.get(mContext).deleteOauth(SetUpActivity.this, SHARE_MEDIA.WEIXIN, authListener);
        } else if (DApplication.getInstance().getLoginUser().getPlatform().equals("qq")) {

            new QQLoginManager("1109933226", this).logout();

        }

        DApplication.getInstance().exit();
        DApplication.getInstance().startLogin();

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
//                        Toast.makeText(mContext, "退出错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void setDate(VersionBean versionBean) {
        if (versionBean.getIs_upd() == 1) {
            check(111, versionBean);
        } else {
            ToastUtils.showShort("已是最新版本");
        }

    }


    void check(final int
                       notifyId, VersionBean versionBean) {

        UpdateInfo info = new UpdateInfo();
        info.hasUpdate = true;
        info.updateContent = versionBean.getContent();
//        info.versionCode = ;
        info.versionName = versionBean.getNewversion();
        info.url = versionBean.getDownloadurl();
        info.md5 = "56cf48f10e4cf6043fbf53bbbc4009e4";
//        info.size = 10149314;
        info.isForce = false;
        info.isIgnorable = false;
        info.isSilent = false;

        UpdateManager.create(mContext).setManual(true).setNotifyId(notifyId).setUpdateInfo(info).check();

    }

    @Override
    public void onQQLoginSuccess(JSONObject jsonObject, QQLoginManager.UserAuthInfo authInfo) {

    }

    @Override
    public void onQQLoginCancel() {

    }

    @Override
    public void onQQLoginError(UiError uiError) {

        int serviceVersionCode = 0;//服务器最新版本号
        int clientVersionCode = 0;//客户端当前本版号
        int is_upd = 0;//是否有新版本更新

        if (serviceVersionCode > clientVersionCode) {
            //如果服务器code大于客户端code，代表有新版本。客户端要更新
            is_upd = 1;
        }else{
            //否则，不需要更新
            is_upd = 0;
        }

        //把 is_upd 字段返回给客户端
    }
}
