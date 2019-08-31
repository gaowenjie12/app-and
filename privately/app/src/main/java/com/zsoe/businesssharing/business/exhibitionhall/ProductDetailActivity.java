package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ProductDetail;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;
import com.zsoe.businesssharing.utils.GlideUtils;

import java.util.List;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import rx.functions.Action1;

@RequiresPresenter(ProductInfoPresenter.class)
public class ProductDetailActivity extends BaseActivity<ProductInfoPresenter> {

    private JzvdStd mJzVideo;
    private FrameLayout mFlVideo;
    private TextView mTvTitle;
    private TextView mTvCompanyName;
    private ExpandableTextView mTvJieshao;
    private RelativeLayout mRlChakan;
    private RecyclerView mRvPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initTitleText("产品介绍");

        setTitleRightSecondIcon(R.mipmap.liebiao, new Action1<View>() {
            @Override
            public void call(View view) {
                startActivity(new Intent(mContext, ProductListActivity.class));
            }
        });

        setTitleRigthIcon(R.mipmap.shoucang, new Action1<View>() {
            @Override
            public void call(View view) {

            }
        });

        initView();

        int id = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);

        DialogManager.getInstance().showNetLoadingView(this);
        getPresenter().product_info(id + "");
    }

    private void initView() {
        mRvPic = (RecyclerView) findViewById(R.id.rv_pic);

        mJzVideo = (JzvdStd) findViewById(R.id.jz_video);
        mFlVideo = (FrameLayout) findViewById(R.id.fl_video);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        mTvJieshao = (ExpandableTextView) findViewById(R.id.tv_jieshao);
        mRlChakan = (RelativeLayout) findViewById(R.id.rl_chakan);
    }

    public void setData(ProductDetail productDetail) {
        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams params = mJzVideo.getLayoutParams();
        // 宽度为屏幕宽度
        params.width = getResources().getDisplayMetrics().widthPixels;
        // 高度为宽度的9/16
        params.height = (int) (params.width * 9f / 16f);
        mJzVideo.setLayoutParams(params);

        JZDataSource jzDataSource = new JZDataSource(productDetail.getVideourl());
        mJzVideo.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL);

        mJzVideo.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        GlideUtils.loadImage(mContext, productDetail.getVideocoverurl(), mJzVideo.thumbImageView);


        mTvTitle.setText(productDetail.getProductname());
        mTvCompanyName.setText(productDetail.getCompanyname());
        mTvJieshao.setText(productDetail.getContent());


        mRlChakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CompanyProfilesActivity.class));
            }
        });

        List<String> photos = productDetail.getPhotos();

        OnionRecycleAdapter jiazhiAdapter = new OnionRecycleAdapter<String>(R.layout.item_xuanchuanpian_layout, photos) {
            @Override
            protected void convert(BaseViewHolder holder, final String item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item);
            }
        };

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        mRvPic.setFocusableInTouchMode(false);
        mRvPic.setNestedScrollingEnabled(false);
        mRvPic.setLayoutManager(linearLayoutManager2);// 布局管理器。
        mRvPic.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvPic.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvPic.setAdapter(jiazhiAdapter);

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
