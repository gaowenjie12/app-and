package com.zsoe.businesssharing.business.me;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;

public class SetUpActivity extends BaseActivity implements View.OnClickListener {

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
                break;
            case R.id.rl_qingchuhuanc:
                break;
            case R.id.rl_guanyuwomen:
                break;
            case R.id.rl_jianchagengxin:
                break;
            case R.id.btn_login:
                break;
        }
    }
}
