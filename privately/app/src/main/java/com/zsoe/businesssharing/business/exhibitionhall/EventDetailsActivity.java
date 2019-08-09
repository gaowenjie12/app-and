package com.zsoe.businesssharing.business.exhibitionhall;

import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.commonview.ExpandableTextView;

/**
 * 活动详情
 */
public class EventDetailsActivity extends BaseActivity {

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
