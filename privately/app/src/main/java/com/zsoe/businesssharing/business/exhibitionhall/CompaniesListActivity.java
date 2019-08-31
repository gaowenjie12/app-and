package com.zsoe.businesssharing.business.exhibitionhall;

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
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ItemCompany;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreContainer;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreDefaultFooterRecyclerView;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreHandler;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;


/**
 * 企业列表
 */

@RequiresPresenter(CompanyListPresenter.class)
public class CompaniesListActivity extends BaseActivity<CompanyListPresenter> {

    private RecyclerView mRvProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_list);
        initView();
        initTitleText("企业列表");

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();
                getPresenter().company_list("");
            }
        });

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();
    }

    private List<ItemCompany> noticeBeanList = new ArrayList<>();
    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;

    private void initView() {
        mRvProductList = (RecyclerView) findViewById(R.id.rv_product_list);

        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<ItemCompany>(R.layout.item_companies_list_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemCompany item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());

                holder.setText(R.id.tv_name, item.getName());
                holder.setText(R.id.tv_zhiwei, item.getMaincate());

                ImageView iv_shoucang = holder.getView(R.id.iv_shoucang);

                if (item.getIs_collect() == 1) {
                    iv_shoucang.setImageResource(R.mipmap.shoucang);
                } else {
                    iv_shoucang.setImageResource(R.mipmap.shoucang_pre);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, CompanyProfilesActivity.class));
                    }
                });

            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().company_list("");
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault.getFooterView();
        noticeBeanOnionRecycleAdapter.setLoadMoreContainer(getPresenter().loadMoreDefault);


        mRvProductList.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvProductList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvProductList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvProductList.setAdapter(noticeBeanOnionRecycleAdapter);
    }


    /**
     * 关闭刷新/更新数据
     */
    public void updateList() {
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter.notifyDataSetChanged();
    }

}
