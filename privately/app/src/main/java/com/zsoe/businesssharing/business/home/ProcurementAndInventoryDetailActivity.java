package com.zsoe.businesssharing.business.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.CaiGouDetail;
import com.zsoe.businesssharing.business.exhibitionhall.CompanyProfilesActivity;
import com.zsoe.businesssharing.business.exhibitionhall.ProductDetailActivity;

@RequiresPresenter(CaiGouPresenter.class)
public class ProcurementAndInventoryDetailActivity extends BaseActivity<CaiGouPresenter> {

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

        int id = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        getPresenter().stock_purchase_info(id + "");
        initTitleText("采购需求/库存情况");
    }

    private void initView() {
        mTvChakanChanpin = (TextView) findViewById(R.id.tv_chakan_chanpin);
        mTvChanpinName = (TextView) findViewById(R.id.tv_chanpin_name);
        mTvChakanQiye = (TextView) findViewById(R.id.tv_chakan_qiye);
        mTvGongsiName = (TextView) findViewById(R.id.tv_gongsi_name);
        mTvContent = (TextView) findViewById(R.id.tv_content);
    }

    public void setDate(CaiGouDetail caiGouDetail) {
        mTvChanpinName.setText(caiGouDetail.getKeyword());
        mTvGongsiName.setText(caiGouDetail.getCompanyname());
        mTvContent.setText(caiGouDetail.getContent());


        mTvChakanChanpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra(Config.INTENT_PARAMS1, caiGouDetail.getProductid());
                startActivity(intent);
            }
        });
        mTvChakanQiye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, CompanyProfilesActivity.class);
                intent.putExtra(Config.INTENT_PARAMS1, caiGouDetail.getShopid());
                startActivity(intent);
            }
        });
    }
}
