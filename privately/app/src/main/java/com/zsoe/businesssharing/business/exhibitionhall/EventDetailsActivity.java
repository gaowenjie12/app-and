package com.zsoe.businesssharing.business.exhibitionhall;

import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.utils.FrecoFactory;

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

        mTvTitle.setText("为何马云仅有7%的股份就控制了阿里,孙正义是最大股东却说了不算");
        mTvTime.setText("发布时间：2019-07-07");
        mTvEventAddress.setText("北京市丰台区丰台体育馆中心");
        mTvEventTime.setText("2019-07-07  16:00");
        mTvNameList.setText("杨东、玉红、刘辉、沈大海、徐继哲、吴波等");
        mTvChenguo.setText("在终端入口方面，华为拥有竞争对手无法比拟的优势，此前华为提出的“1+8+N” 全场景智慧服务战略口号是一个非常明显的例证。" +
                "1是手机终端为主要入口;8是包括PC、平板、智慧大屏、车机在内容的4个大屏入口，加上耳机、音箱、手表，" +
                "眼镜在内4个非大屏入口;N是指泛IoT硬件构成的华为HiLink生态。" +
                "颇有远见的产品布局，让华为在近几年以肉眼可见的速度形成了自己的生态壁垒。近两年，华为一直不断对外发出发展全场景生态战略的信号，" +
                "生态系统的建立又显然不是一朝一夕可以完成，需要更多合作伙伴的加入。从近来华为的开发姿态和此次开发者大会释放的诸多信号，" +
                "华为正在朝着既定的目标努力迈进");


        mTvHuodong.setText("在终端入口方面，华为拥有竞争对手无法比拟的优势，此前华为提出的“1+8+N” 全场景智慧服务战略口号是一个非常明显的例证。" +
                "1是手机终端为主要入口;8是包括PC、平板、智慧大屏、车机在内容的4个大屏入口，加上耳机、音箱、手表，" +
                "眼镜在内4个非大屏入口;N是指泛IoT硬件构成的华为HiLink生态。" +
                "颇有远见的产品布局，让华为在近几年以肉眼可见的速度形成了自己的生态壁垒。近两年，华为一直不断对外发出发展全场景生态战略的信号，" +
                "生态系统的建立又显然不是一朝一夕可以完成，需要更多合作伙伴的加入。从近来华为的开发姿态和此次开发者大会释放的诸多信号，" +
                "华为正在朝着既定的目标努力迈进");


        FrecoFactory.getInstance().disPlay(mIvImage, "http://www.wzrynyx.cn/wp-content/uploads/2018/11/20181116101101_78760.jpg");
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
