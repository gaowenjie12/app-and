package com.zsoe.businesssharing.business.exhibitionhall;

import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.MasterBean;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;

@RequiresPresenter(MasterDetailPresenter.class)
public class MasterDetailActivity extends BaseActivity<MasterDetailPresenter> {

    private SimpleDraweeView mUserImage;
    private TextView mTvMasterName;
    private TextView mTvMasterZhiwei;
    private TextView mTvJieshao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);
        initView();
        initTitleText("人物介绍");

        String id = getIntent().getStringExtra(Config.INTENT_PARAMS1);
        DialogManager.getInstance().showNetLoadingView(mContext);
        getPresenter().industry_person_info(id);

    }


    public void setData(MasterBean masterBean) {
        FrecoFactory.getInstance().disPlay(mUserImage, masterBean.getThumb());
        mTvMasterName.setText(masterBean.getName());
        mTvMasterZhiwei.setText(masterBean.getLable());
        mTvJieshao.setText(masterBean.getDes());
    }

    private void initView() {
        mUserImage = (SimpleDraweeView) findViewById(R.id.user_image);
        mTvMasterName = (TextView) findViewById(R.id.tv_master_name);
        mTvMasterZhiwei = (TextView) findViewById(R.id.tv_master_zhiwei);
        mTvJieshao = (TextView) findViewById(R.id.tv_jieshao);
    }
}
