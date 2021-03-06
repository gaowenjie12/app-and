package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.BrowserActivity;
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

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

@RequiresPresenter(BangZhuPresenter.class)
public class BangZhuActivity extends BaseActivity<BangZhuPresenter> {

    private RecyclerView mRvProductList;
    private int type, person_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_news);
        initView();
        initTitleText("帮助中心");

        type = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        person_id = getIntent().getIntExtra(Config.INTENT_PARAMS2, -1);

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();
                getPresenter().article_list(type + "", person_id + "");

            }
        });

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();
    }


    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<ItemWenZhangBean> noticeBeanList = new ArrayList<>();

    private void initView() {
        mRvProductList = (RecyclerView) findViewById(R.id.rv_product_list);
        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<ItemWenZhangBean>(R.layout.item_bangzhu_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemWenZhangBean item) {
                super.convert(holder, item);


                holder.setText(R.id.tv_title, item.getTitle());
                holder.setText(R.id.tv_time, item.getCreatetime());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, BrowserActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1, item.getLinkurl());
                        startActivity(intent);
                    }
                });

            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().article_list(type + "", person_id + "");
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
