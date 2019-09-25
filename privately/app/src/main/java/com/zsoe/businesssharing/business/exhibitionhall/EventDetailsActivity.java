package com.zsoe.businesssharing.business.exhibitionhall;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.blankj.utilcode.util.ToastUtils;
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

    private String type;
    /**
     * 到场嘉宾：
     */
    private TextView mTitle1;
    /**
     * 活动时间：
     */
    private TextView mTitle2;
    /**
     * 活动地点：
     */
    private TextView mTitle3;
    private LinearLayoutCompat mTitle4;
    private LinearLayoutCompat mTitle5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        initView();

        initTitleText("活动详情");

        int id = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        type = getIntent().getStringExtra(Config.INTENT_PARAMS2);
        if (null == type) {
            type = "";
        }

        DialogManager.getInstance().showNetLoadingView(mContext);
        getPresenter().communication_info(id + "", DApplication.getInstance().getLoginUser().getId() + "", type);
    }

    public void setData(DetailJiaoLiuBean detailJiaoLiuBean) {
        if (null == detailJiaoLiuBean) {
            ToastUtils.showShort("对象为空");
            finish();
            return;
        }
        mTvTitle.setText(detailJiaoLiuBean.getTitle());
        mTvTime.setText("发布时间：" + detailJiaoLiuBean.getPubdate());

        if (TextUtils.isEmpty(detailJiaoLiuBean.getActivityaddress())) {
            findViewById(R.id.title3).setVisibility(View.GONE);
            mTvEventAddress.setVisibility(View.GONE);
        } else {
            mTvEventAddress.setText(detailJiaoLiuBean.getActivityaddress());
        }


        if (TextUtils.isEmpty(detailJiaoLiuBean.getActivitytime())) {
            findViewById(R.id.title2).setVisibility(View.GONE);
            mTvEventTime.setVisibility(View.GONE);
        } else {
            mTvEventTime.setText(detailJiaoLiuBean.getActivitytime());
        }


        if (TextUtils.isEmpty(detailJiaoLiuBean.getGuester())) {
            findViewById(R.id.title1).setVisibility(View.GONE);
            mTvNameList.setVisibility(View.GONE);
        } else {
            mTvNameList.setText(detailJiaoLiuBean.getGuester());
        }


        if (TextUtils.isEmpty(detailJiaoLiuBean.getResultcontent())) {
            findViewById(R.id.title5).setVisibility(View.GONE);
        } else {
            mTvChenguo.setText(detailJiaoLiuBean.getResultcontent());
        }


        if (TextUtils.isEmpty(detailJiaoLiuBean.getContent())) {
            findViewById(R.id.title4).setVisibility(View.GONE);
        } else {
            mTvHuodong.setText(detailJiaoLiuBean.getContent());
        }


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
        mTitle1 = (TextView) findViewById(R.id.title1);
        mTitle2 = (TextView) findViewById(R.id.title2);
        mTitle3 = (TextView) findViewById(R.id.title3);
        mTitle4 = (LinearLayoutCompat) findViewById(R.id.title4);
        mTitle4 = (LinearLayoutCompat) findViewById(R.id.title4);
        mTitle5 = (LinearLayoutCompat) findViewById(R.id.title5);
    }
}
