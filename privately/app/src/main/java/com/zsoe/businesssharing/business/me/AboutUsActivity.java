package com.zsoe.businesssharing.business.me;

import android.os.Bundle;
import android.widget.TextView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;

@RequiresPresenter(AboutUsPresenter.class)
public class AboutUsActivity extends BaseActivity<AboutUsPresenter> {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initView();
        initTitleText("关于我们");
        getPresenter().about_us();
    }

    public void setDate(String str) {
        mTextView.setText(str);
    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.textView);
    }
}
