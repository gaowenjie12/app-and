package com.zsoe.businesssharing.business.exhibitionhall;

import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.utils.FrecoFactory;

public class DynamicDetailsActivity extends BaseActivity {

    private TextView mTvTitle;
    private TextView mTvTime;
    private SimpleDraweeView mIvImage;
    private TextView mTvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_details);
        initView();
        initTitleText("动态详情");
        setDate();
    }

    public void setDate() {

        FrecoFactory.getInstance().disPlay(mIvImage, "http://img.9553.com/uploadfile/2018/0821/20180821105949771.jpg");

        mTvTitle.setText("为何马云仅有7%的股份就控制了阿里,孙正义是最大股东却说了不算");
        mTvTime.setText("发布时间：2019-07-07");
        mTvDetail.setText("孙正义旗下的软银是阿里巴巴的股东，马云也仅仅拥有阿里巴巴7%左右的股份，蔡崇信持股比例3.6%，软银曾经拥有超过了35%的股份，经过几次套现之后，软银拥有29.6%的股份，依然是最大的股东。然而作为阿里巴巴董事会成员的孙正义却从来没有发言权，这又是为何呢？\n" +
                "许多人就说孙正义早在2004年就应该成为世界首富了。不过如今4年过去了，孙正义还没有成为世界首富，反而是软银负债累累，不得不数次抛掉阿里巴巴的股份来拯救公司。\n" +
                "其实孙正义在阿里巴巴还没有达到巅峰的时候，就卖掉了阿里巴巴的大多股票，阿里巴巴上市不久后创造了全球最大的上市融资案例，之后阿里巴巴市值一路上涨，成为了全球市值前十的公司。但是凭借这个回报率已经足够了。在他带领软银投资了阿里巴巴之后，后面这些年好像没有神马太大的投资亮点。\n" +
                "                        ");
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mIvImage = (SimpleDraweeView) findViewById(R.id.iv_image);
        mTvDetail = (TextView) findViewById(R.id.tv_detail);
    }
}
