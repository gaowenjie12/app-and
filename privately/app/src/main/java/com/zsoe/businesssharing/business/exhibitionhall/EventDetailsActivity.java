package com.zsoe.businesssharing.business.exhibitionhall;

import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.DetailJiaoLiuBean;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;

/**
 * 活动详情
 */

@RequiresPresenter(JiaoLiuPresenter.class)
public class EventDetailsActivity extends BaseActivity<JiaoLiuPresenter> {

    private TextView mTvTitle;
    private TextView mTvTime;
    private SimpleDraweeView mIvImage;
    private TextView mTvNameList;
    private TextView mTvEventTime;
    private TextView mTvEventAddress;
    private ExpandableTextView mTvHuodong;
    private ExpandableTextView mTvChenguo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        initView();

        initTitleText("活动详情");

        int id = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);

        DialogManager.getInstance().showNetLoadingView(mContext);
        getPresenter().communication_info(id + "", DApplication.getInstance().getLoginUser().getId() + "");
    }

    public void setData(DetailJiaoLiuBean detailJiaoLiuBean) {

        mTvTitle.setText(detailJiaoLiuBean.getTitle());
        mTvTime.setText("发布时间：" + detailJiaoLiuBean.getPubdate());
        mTvEventAddress.setText(detailJiaoLiuBean.getActivityaddress());
        mTvEventTime.setText(detailJiaoLiuBean.getActivitytime());
        mTvNameList.setText(detailJiaoLiuBean.getGuester());
        mTvChenguo.setText(detailJiaoLiuBean.getResultcontent());


        mTvHuodong.setText(detailJiaoLiuBean.getContent());


        FrecoFactory.getInstance().disPlay(mIvImage, detailJiaoLiuBean.getImage());

    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mIvImage = (SimpleDraweeView) findViewById(R.id.iv_image);
        mTvNameList = (TextView) findViewById(R.id.tv_name_list);
        mTvEventTime = (TextView) findViewById(R.id.tv_event_time);
        mTvEventAddress = (TextView) findViewById(R.id.tv_event_address);
        mTvHuodong = (ExpandableTextView) findViewById(R.id.tv_huodong);
        mTvChenguo = (ExpandableTextView) findViewById(R.id.tv_chenguo);
    }
}
