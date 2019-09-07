package com.zsoe.businesssharing.business.exhibitionhall;

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
import com.zsoe.businesssharing.bean.ItemWenZhangBean;
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

@RequiresPresenter(LatestNewsPresenter.class)
public class LatestNewsActivity extends BaseActivity<LatestNewsPresenter> {

    private RecyclerView mRvProductList;
    private int type, daliang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_news);
        initView();
        initTitleText("近期动态");

        type = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        daliang = getIntent().getIntExtra(Config.INTENT_PARAMS2, -1);

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();
                getPresenter().article_list(type + "",daliang+"");

            }
        });

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();
    }


    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<ItemWenZhangBean> noticeBeanList = new ArrayList<>();

    private void initView() {
        mRvProductList = (RecyclerView) findViewById(R.id.rv_product_list);

        OnionRecycleAdapter noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<ItemWenZhangBean>(R.layout.item_latestnew_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemWenZhangBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.iv_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());

                holder.setText(R.id.tv_title, item.getTitle());
                holder.setText(R.id.tv_time, "发布时间：" + item.getCreatetime());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().article_list(type + "",daliang+"");
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
