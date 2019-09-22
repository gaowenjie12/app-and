package com.zsoe.businesssharing.business.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ItemJoinInvestmentBean;
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

@RequiresPresenter(JoinInvestmentPresenter.class)
public class JoinInvestmentActivity extends BaseActivity<JoinInvestmentPresenter> {

    private RecyclerView mRvJoinList;

    private String shopId,sourcepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_investment);
        initView();

        shopId = getIntent().getStringExtra(Config.INTENT_PARAMS1);
        if (null == shopId) {
            shopId = "";
        }

        sourcepage = getIntent().getStringExtra(Config.INTENT_PARAMS2);
        if (null == sourcepage) {
            sourcepage = "";
        }

        initTitleText("招商加盟列表");

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();
                getPresenter().join_merchant_list(shopId,sourcepage);
            }
        });

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();

    }


    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<ItemJoinInvestmentBean> noticeBeanList = new ArrayList<>();


    private void initView() {
        mRvJoinList = (RecyclerView) findViewById(R.id.rv_join_list);

        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<ItemJoinInvestmentBean>(R.layout.item_tuiguang_list_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemJoinInvestmentBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());

                holder.setText(R.id.tv_name, item.getTitle());
                holder.setText(R.id.tv_zhiwei, item.getJoindate());
                holder.setText(R.id.tv_p_c, item.getReadnum() + "人看过");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(mContext, JoinInvestmentDetailActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1, item.getId());
                        startActivity(intent);

                    }
                });

            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().join_merchant_list(shopId,sourcepage);
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault.getFooterView();
        noticeBeanOnionRecycleAdapter.setLoadMoreContainer(getPresenter().loadMoreDefault);


        mRvJoinList.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvJoinList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvJoinList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvJoinList.setAdapter(noticeBeanOnionRecycleAdapter);
    }


    /**
     * 关闭刷新/更新数据
     */
    public void updateList() {
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter.notifyDataSetChanged();
    }
}
