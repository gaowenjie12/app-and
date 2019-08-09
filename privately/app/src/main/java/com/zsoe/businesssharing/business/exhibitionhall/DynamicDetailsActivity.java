package com.zsoe.businesssharing.business.exhibitionhall;

import android.os.Bundle;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;

public class DynamicDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_details);
        initTitleText("动态详情");

    }
}
