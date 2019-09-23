package com.zsoe.businesssharing.business.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.BrowserActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.SearchBean;
import com.zsoe.businesssharing.business.exhibitionhall.CompanyProfilesActivity;
import com.zsoe.businesssharing.business.exhibitionhall.EventDetailsActivity;
import com.zsoe.businesssharing.business.exhibitionhall.MasterDetailActivity;
import com.zsoe.businesssharing.business.exhibitionhall.ProductDetailActivity;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.List;

@RequiresPresenter(SearchListPresenter.class)
public class SearchListActivity extends BaseActivity<SearchListPresenter> {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        initView();
        initTitleText("搜索结果");

        String type = getIntent().getStringExtra(Config.INTENT_PARAMS1);
        String keywords = getIntent().getStringExtra(Config.INTENT_PARAMS2);
        DialogManager.getInstance().showNetLoadingView(this);
        getPresenter().search(type, keywords);

    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }


    public void searchSuccess(List<SearchBean> searchBeanList) {

        OnionRecycleAdapter noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<SearchBean>(R.layout.item_companies_list_layout, searchBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final SearchBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());

                holder.setText(R.id.tv_name, item.getTitle());
                holder.setText(R.id.tv_zhiwei, item.getContenttypedes());

                ImageView iv_shoucang = holder.getView(R.id.iv_shoucang);
                iv_shoucang.setVisibility(View.GONE);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (item.getContenttype() == 1) {
                            //推广活动
                            Intent intent = new Intent(mContext, EventDetailsActivity.class);
                            intent.putExtra(Config.INTENT_PARAMS1, item.getId());
                            startActivity(intent);
                        } else if (item.getContenttype() == 2) {
                            //采购
                            Intent intent = new Intent(mContext, ProcurementAndInventoryDetailActivity.class);
                            intent.putExtra(Config.INTENT_PARAMS1, item.getId());
                            startActivity(intent);

                        } else if (item.getContenttype() == 3) {
                            //库存
                            Intent intent = new Intent(mContext, ProcurementAndInventoryDetailActivity.class);
                            intent.putExtra(Config.INTENT_PARAMS1, item.getId());
                            startActivity(intent);

                        } else if (item.getContenttype() == 4) {
                            //产品
                            Intent intent = new Intent(mContext, ProductDetailActivity.class);
                            intent.putExtra(Config.INTENT_PARAMS1, item.getId());
                            startActivity(intent);
                        } else if (item.getContenttype() == 5) {
                            //招商加盟
                            Intent intent = new Intent(mContext, JoinInvestmentDetailActivity.class);
                            intent.putExtra(Config.INTENT_PARAMS1, item.getId());
                            startActivity(intent);

                        } else if (item.getContenttype() == 6) {
                            //贷款项目

                            Intent intent = new Intent(mContext, BrowserActivity.class);
                            intent.putExtra(Config.INTENT_PARAMS1, item.getLinkurl());
                            startActivity(intent);

                        } else if (item.getContenttype() == 7) {
                            //融资项目
                            Intent intent = new Intent(mContext, FinancingLoansDetailActivity.class);
                            intent.putExtra(Config.INTENT_PARAMS1, item.getId());
                            startActivity(intent);
                        } else if (item.getContenttype() == 9 || item.getContenttype() == 8) {
                            //大咖，大梁
                            Intent intent = new Intent(mContext, MasterDetailActivity.class);
                            intent.putExtra(Config.INTENT_PARAMS1, item.getId() + "");
                            startActivity(intent);

                        } else if (item.getContenttype() == 10) {
                            //企业
                            Intent intent = new Intent(mContext, CompanyProfilesActivity.class);
                            intent.putExtra(Config.INTENT_PARAMS1, item.getId());
                            startActivity(intent);
                        }
                    }
                });


            }
        };


        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRecyclerView.setAdapter(noticeBeanOnionRecycleAdapter);
    }
}
