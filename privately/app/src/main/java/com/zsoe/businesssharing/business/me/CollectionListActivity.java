package com.zsoe.businesssharing.business.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.bean.BannerItemBean;
import com.zsoe.businesssharing.business.exhibitionhall.CompaniesListActivity;
import com.zsoe.businesssharing.business.exhibitionhall.CompanyProfilesActivity;
import com.zsoe.businesssharing.business.exhibitionhall.DynamicDetailsActivity;
import com.zsoe.businesssharing.business.exhibitionhall.LatestNewsActivity;
import com.zsoe.businesssharing.business.exhibitionhall.ProductDetailActivity;
import com.zsoe.businesssharing.business.exhibitionhall.ProductListActivity;
import com.zsoe.businesssharing.business.home.JoinInvestmentActivity;
import com.zsoe.businesssharing.business.home.JoinInvestmentDetailActivity;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.ArrayList;
import java.util.List;

public class CollectionListActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 查看全部
     */
    private TextView mTvQiyeMore;
    private RecyclerView mRvQiye;
    /**
     * 查看全部
     */
    private TextView mTvChanpinMore;
    private RecyclerView mRvChanpin;
    /**
     * 查看全部
     */
    private TextView mTvZixunMore;
    private RecyclerView mRvZixun;
    /**
     * 查看全部
     */
    private TextView mTvZhaoshangMore;
    private RecyclerView mRvZhaoshang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);
        initView();
        setDate();

        initTitleText("收藏列表");
    }

    private void initView() {
        mTvQiyeMore = (TextView) findViewById(R.id.tv_qiye_more);
        mTvQiyeMore.setOnClickListener(this);
        mRvQiye = (RecyclerView) findViewById(R.id.rv_qiye);
        mTvChanpinMore = (TextView) findViewById(R.id.tv_chanpin_more);
        mTvChanpinMore.setOnClickListener(this);
        mRvChanpin = (RecyclerView) findViewById(R.id.rv_chanpin);
        mTvZixunMore = (TextView) findViewById(R.id.tv_zixun_more);
        mTvZixunMore.setOnClickListener(this);
        mRvZixun = (RecyclerView) findViewById(R.id.rv_zixun);
        mTvZhaoshangMore = (TextView) findViewById(R.id.tv_zhaoshang_more);
        mTvZhaoshangMore.setOnClickListener(this);
        mRvZhaoshang = (RecyclerView) findViewById(R.id.rv_zhaoshang);
    }


    public void setDate() {
        List<BannerItemBean> bannerItemBeans = new ArrayList<>();

        BannerItemBean bannerItemBean = new BannerItemBean();
        bannerItemBean.setUrl_title("简介");
        bannerItemBean.setImg("http://hbimg.b0.upaiyun.com/3e14d836d89498b116834b2987dbaa1c8f2e85a418a9fc-nLVWsW_fw658");
        bannerItemBeans.add(bannerItemBean);

        BannerItemBean bannerItemBean2 = new BannerItemBean();
        bannerItemBean2.setUrl_title("简介");

        bannerItemBean2.setImg("http://hbimg.b0.upaiyun.com/9be8e0054e2ed5e02fa91c6c66267f9d51859e951b83e-qMhDYE_fw658");
        bannerItemBeans.add(bannerItemBean2);

        BannerItemBean bannerItemBean3 = new BannerItemBean();
        bannerItemBean3.setUrl_title("简介");

        bannerItemBean3.setImg("http://img694.ph.126.net/2CR9OPpnSjmHa_7BzGVE9Q==/2868511487659481204.jpg");
        bannerItemBeans.add(bannerItemBean3);

        BannerItemBean bannerItemBean4 = new BannerItemBean();
        bannerItemBean4.setUrl_title("简介");

        bannerItemBean4.setImg("http://i1.hdslb.com/bfs/archive/20b81aa9dcffd6db03dc14296ff3b84874f0c529.png");
        bannerItemBeans.add(bannerItemBean4);


        OnionRecycleAdapter hangyeAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_product_list_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getImg());

                holder.setText(R.id.tv_name, "北京字节跳动科技有限公司");
                holder.setText(R.id.tv_zhiwei, "主营业务：数码、平板销售数码、平板销售...");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, CompanyProfilesActivity.class));
                    }
                });

            }
        };

        mRvQiye.setFocusableInTouchMode(false);
        mRvQiye.setNestedScrollingEnabled(false);
        mRvQiye.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvQiye.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvQiye.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvQiye.setAdapter(hangyeAdapter);


        OnionRecycleAdapter chanpinAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_product_list_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getImg());

                holder.setText(R.id.tv_name, "北京字节跳动科技有限公司");
                holder.setText(R.id.tv_zhiwei, "主营业务：数码、平板销售数码、平板销售...");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, ProductDetailActivity.class));
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


        OnionRecycleAdapter zixunAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_product_list_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getImg());

                holder.setText(R.id.tv_name, "北京字节跳动科技有限公司");
                holder.setText(R.id.tv_zhiwei, "主营业务：数码、平板销售数码、平板销售...");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, DynamicDetailsActivity.class));
                    }
                });

            }
        };

        mRvZixun.setFocusableInTouchMode(false);
        mRvZixun.setNestedScrollingEnabled(false);
        mRvZixun.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvZixun.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvZixun.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvZixun.setAdapter(zixunAdapter);


        OnionRecycleAdapter zhaoshangAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_product_list_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getImg());

                holder.setText(R.id.tv_name, "北京字节跳动科技有限公司");
                holder.setText(R.id.tv_zhiwei, "主营业务：数码、平板销售数码、平板销售...");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, JoinInvestmentDetailActivity.class));
                    }
                });

            }
        };

        mRvZhaoshang.setFocusableInTouchMode(false);
        mRvZhaoshang.setNestedScrollingEnabled(false);
        mRvZhaoshang.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvZhaoshang.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvZhaoshang.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvZhaoshang.setAdapter(zhaoshangAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_qiye_more:
                startActivity(new Intent(mContext, CompaniesListActivity.class));

                break;
            case R.id.tv_chanpin_more:
                startActivity(new Intent(mContext, ProductListActivity.class));

                break;
            case R.id.tv_zixun_more:
                startActivity(new Intent(mContext, LatestNewsActivity.class));

                break;
            case R.id.tv_zhaoshang_more:
                startActivity(new Intent(mContext, JoinInvestmentActivity.class));

                break;
        }
    }
}
