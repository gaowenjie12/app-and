package com.zsoe.businesssharing.business.attention;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ItemEventBean;
import com.zsoe.businesssharing.bean.SlideBean;
import com.zsoe.businesssharing.business.exhibitionhall.LatestNewsActivity;
import com.zsoe.businesssharing.commonview.banner.BannerView;
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
 * 事件
 */
@RequiresPresenter(EventPresenter.class)
public class TheEventFragment extends BaseFragment<EventPresenter> {

    private static final String TAG = "HomeFragment";

    public static TheEventFragment newInstance(String title) {
        TheEventFragment f = new TheEventFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        f.setArguments(args);
        return f;
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
    }

    @Override
    protected int createViewByLayoutId() {
        return R.layout.fragment_the_event;
    }


    private RecyclerView recyclerView;
    private BannerView bannerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);


        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<ItemEventBean>(R.layout.item_event_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemEventBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());

                holder.setText(R.id.tv_title, item.getTitle());
                holder.setText(R.id.tv_jianjie, item.getDes());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, LatestNewsActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1, 1);
                        startActivity(intent);
                    }
                });


            }
        };



        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().attentionEvent("");
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault.getFooterView();
        noticeBeanOnionRecycleAdapter.setLoadMoreContainer(getPresenter().loadMoreDefault);


        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        recyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        recyclerView.setAdapter(noticeBeanOnionRecycleAdapter);


        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();
                getPresenter().attentionEvent("1");

            }
        });


        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();
        //左右滑动时刷新控件禁止掉
        mPtrFrame.disableWhenHorizontalMove(true);


    }

    public void setSlide(List<SlideBean> slide) {
        if (null == bannerView) {
            bannerView = new BannerView(mContext);
            noticeBeanOnionRecycleAdapter.addHeaderView(bannerView);
        }
        bannerView.setDate(slide);
    }


    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<ItemEventBean> noticeBeanList = new ArrayList<>();


    /**
     * 关闭刷新/更新数据
     */
    public void updateList() {
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter.notifyDataSetChanged();
    }
}
