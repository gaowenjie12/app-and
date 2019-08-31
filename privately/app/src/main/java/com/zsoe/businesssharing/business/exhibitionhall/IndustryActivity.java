package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.IndustryRoot;
import com.zsoe.businesssharing.bean.ItemCompany;
import com.zsoe.businesssharing.bean.ItemProduct;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.List;

import rx.functions.Action1;

/***
 * 行业
 * */

@RequiresPresenter(IndustryPresenter.class)
public class IndustryActivity extends BaseActivity<IndustryPresenter> implements View.OnClickListener {

    private ExpandableTextView mTvXiangqing;
    /**
     * 更多
     */
    private TextView mTvHangyeMore;
    private RecyclerView mRvHangye;
    /**
     * 更多
     */
    private TextView mTvChanpinMore;
    private RecyclerView mRvChanpin;
    private NestedScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry);
        int id = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        initView();

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().industry_info("" + id);
            }
        });


        DialogManager.getInstance().showNetLoadingView(mContext);
        getPresenter().industry_info("" + id);
    }

    private void initView() {
        mTvXiangqing = (ExpandableTextView) findViewById(R.id.tv_xiangqing);
        mTvHangyeMore = (TextView) findViewById(R.id.tv_hangye_more);
        mTvHangyeMore.setOnClickListener(this);
        mRvHangye = (RecyclerView) findViewById(R.id.rv_hangye);
        mTvChanpinMore = (TextView) findViewById(R.id.tv_chanpin_more);
        mTvChanpinMore.setOnClickListener(this);
        mRvChanpin = (RecyclerView) findViewById(R.id.rv_chanpin);
        mScrollView = (NestedScrollView) findViewById(R.id.scrollView);
    }

    public void setData(IndustryRoot industryRoot) {
        initTitleText(industryRoot.getIndustry_info().getName());
        mPtrFrame.refreshComplete();

        mTvHangyeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CompaniesListActivity.class));
            }
        });

        mTvChanpinMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ProductListActivity.class));
            }
        });

        mTvXiangqing.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpandStateChanged(TextView textView, boolean isExpanded) {
                if (!isExpanded) {
                    mScrollView.smoothScrollTo(0, 0);
                }
            }
        });

        mTvXiangqing.setText(industryRoot.getIndustry_info().getDescription());

        List<ItemCompany> companylist = industryRoot.getCompanylist();

        OnionRecycleAdapter hangyeAdapter = new OnionRecycleAdapter<ItemCompany>(R.layout.item_hangye_list_layout, companylist) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemCompany item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());
                holder.setText(R.id.tv_name, item.getName());
                holder.setText(R.id.tv_jianjie, item.getCompanydes());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, CompanyProfilesActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1,item.getId());
                        startActivity(intent);
                    }
                });

            }
        };


        mRvHangye.setFocusableInTouchMode(false);
        mRvHangye.setNestedScrollingEnabled(false);
        mRvHangye.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvHangye.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvHangye.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvHangye.setAdapter(hangyeAdapter);


        List<ItemProduct> productlist = industryRoot.getProductlist();

        OnionRecycleAdapter chanpinAdapter = new OnionRecycleAdapter<ItemProduct>(R.layout.item_hangye_list_layout, productlist) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemProduct item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());
                holder.setText(R.id.tv_name, item.getName());
                holder.setText(R.id.tv_jianjie, item.getContent());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, ProductDetailActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1,item.getId());
                        startActivity(intent);
                    }
                });

            }
        };


        mRvChanpin.setFocusableInTouchMode(false);
        mRvChanpin.setNestedScrollingEnabled(false);
        mRvChanpin.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvChanpin.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvChanpin.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvChanpin.setAdapter(chanpinAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_hangye_more:
                break;
            case R.id.tv_chanpin_more:
                break;
        }
    }
}
