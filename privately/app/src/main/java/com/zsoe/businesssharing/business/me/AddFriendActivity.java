package com.zsoe.businesssharing.business.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.utils.DialogManager;

@RequiresPresenter(AddFriendPresenter.class)
public class AddFriendActivity extends BaseActivity<AddFriendPresenter> implements View.OnClickListener {

    /**
     * 请输入姓名
     */
    private EditText mEtName;
    /**
     * 请输入号码
     */
    private EditText mEtPhone;
    /**
     * 添加为我的好友
     */
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initView();
        initTitleText("添加好友");
    }

    private void initView() {
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_login:

                String account = mEtName.getText().toString();
                if (TextUtils.isEmpty(account)) {
                    ToastUtils.showShort("请填写姓名");
                    return;
                }


                String pass = mEtPhone.getText().toString();
                if (TextUtils.isEmpty(pass)) {
                    ToastUtils.showShort("请填写手机号");
                    return;
                }

                DialogManager.getInstance().showNetLoadingView(mContext);
                getPresenter().add_friend(account, pass);
                break;
        }
    }

    public void addSuccess() {
        finish();
    }
}
