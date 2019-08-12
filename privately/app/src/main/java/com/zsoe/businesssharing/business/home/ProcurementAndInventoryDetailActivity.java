package com.zsoe.businesssharing.business.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.business.exhibitionhall.CompanyProfilesActivity;
import com.zsoe.businesssharing.business.exhibitionhall.ProductDetailActivity;


public class ProcurementAndInventoryDetailActivity extends BaseActivity {

    /**
     * 查看产品
     */
    private TextView mTvChakanChanpin;
    private TextView mTvChanpinName;
    /**
     * 查看产品
     */
    private TextView mTvChakanQiye;
    private TextView mTvGongsiName;
    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_and_inventory_detail);
        initView();
        initTitleText("采购需求/库存情况");
        setDate();
    }

    private void initView() {
        mTvChakanChanpin = (TextView) findViewById(R.id.tv_chakan_chanpin);
        mTvChanpinName = (TextView) findViewById(R.id.tv_chanpin_name);
        mTvChakanQiye = (TextView) findViewById(R.id.tv_chakan_qiye);
        mTvGongsiName = (TextView) findViewById(R.id.tv_gongsi_name);
        mTvContent = (TextView) findViewById(R.id.tv_content);
    }

    public void setDate() {
        mTvChanpinName.setText("水杯");
        mTvGongsiName.setText("阿尔法科技公司");
        mTvContent.setText("青岛市黄岛区气象局预报员张星说，这是因为今年第9号台风“利奇马”已经从强台风级减弱为热带风暴级，" +
                "它的台风结构已经被严重破坏，台风中心范围很大。众所周知，台风中心的气压非常平均，一般风雨很小，或者无风无雨，" +
                "因此大家可能没有感觉到“利奇马”中心登陆，它就已经过去了");


        mTvChakanChanpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ProductDetailActivity.class));
            }
        });
        mTvChakanQiye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CompanyProfilesActivity.class));

            }
        });
    }
}
