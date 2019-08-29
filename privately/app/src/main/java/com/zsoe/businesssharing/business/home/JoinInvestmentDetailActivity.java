package com.zsoe.businesssharing.business.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.DetailJoinInvestmentBean;
import com.zsoe.businesssharing.business.exhibitionhall.CompanyProfilesActivity;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;
import com.zsoe.businesssharing.utils.ScreenUtils;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

@RequiresPresenter(DetailJoinInvestmentPresenter.class)
public class JoinInvestmentDetailActivity extends BaseActivity<DetailJoinInvestmentPresenter> {

    private JzvdStd mJzVideo;
    private TextView mTvTitle;
    private TextView mTvCompanyName;
    private ExpandableTextView mTvJieshao;
    /**
     * 更多
     */
    private TextView mTvTuiguangMore;
    private SimpleDraweeView mProductImage;
    private ExpandableTextView mTvQiyeJieshao;
    private ExpandableTextView mTvZhengce;
    private FrameLayout mFlVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_investment_detail);
        initView();
        initTitleText("招商加盟详情");

        int id = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        DialogManager.getInstance().showNetLoadingView(mContext);
        getPresenter().join_merchant_info(id + "");
    }


    private void initView() {
        mJzVideo = (JzvdStd) findViewById(R.id.jz_video);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        mTvJieshao = (ExpandableTextView) findViewById(R.id.tv_jieshao);
        mTvTuiguangMore = (TextView) findViewById(R.id.tv_tuiguang_more);
        mProductImage = (SimpleDraweeView) findViewById(R.id.product_image);
        mTvQiyeJieshao = (ExpandableTextView) findViewById(R.id.tv_qiye_jieshao);
        mTvZhengce = (ExpandableTextView) findViewById(R.id.tv_zhengce);
        mFlVideo = (FrameLayout) findViewById(R.id.fl_video);
    }

    public void setDate(DetailJoinInvestmentBean detailJoinInvestmentBean) {
        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams params = mJzVideo.getLayoutParams();
        // 宽度为屏幕宽度
        params.width = getResources().getDisplayMetrics().widthPixels;
        // 高度为宽度的9/16
        params.height = (int) (params.width * 9f / 16f);
        mJzVideo.setLayoutParams(params);

        JZDataSource jzDataSource = new JZDataSource(detailJoinInvestmentBean.getVideourl());
        mJzVideo.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL);

        mJzVideo.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        Bitmap videoThumb = ScreenUtils.createVideoThumbnail(detailJoinInvestmentBean.getVideourl(), 200, 200);
        mJzVideo.thumbImageView.setImageBitmap(videoThumb);


        mTvTitle.setText(detailJoinInvestmentBean.getTitle());
        mTvCompanyName.setText(detailJoinInvestmentBean.getCompanyname());
        mTvJieshao.setText(detailJoinInvestmentBean.getContent());

        mTvQiyeJieshao.setText(detailJoinInvestmentBean.getDes());
        mTvZhengce.setText(detailJoinInvestmentBean.getPolicytext());

        FrecoFactory.getInstance().disPlay(mProductImage, detailJoinInvestmentBean.getThumb());


        mTvTuiguangMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CompanyProfilesActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
