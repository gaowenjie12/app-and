package com.zsoe.businesssharing.business.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ItemFinancBean;
import com.zsoe.businesssharing.commonview.HeaderGridSpacingItemDecoration;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreContainer;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreDefaultFooterRecyclerView;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreHandler;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;
import com.zsoe.businesssharing.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * 融资项目
 */


@RequiresPresenter(FanancListPresenter.class)
public class HuiFuFragment extends BaseFragment<FanancListPresenter> {

    private static final String TAG = "HomeFragment";

    public static HuiFuFragment newInstance(String title) {
        HuiFuFragment f = new HuiFuFragment();
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
        return R.layout.fragment_daikuanrongzi;
    }


    private RecyclerView mRvProductList;
    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<ItemFinancBean> noticeBeanList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRvProductList = view.findViewById(R.id.rv_product_list);


        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<ItemFinancBean>(R.layout.item_daikuan_list_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemFinancBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.imgTop);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());

                holder.setText(R.id.tvBottom, item.getTitle());
                holder.setText(R.id.tv_name, item.getCompanyname());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, FinancingLoansDetailActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1,item.getId());
                        startActivity(intent);
                    }
                });
            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
//                getPresenter().finance_list();

            }
        });

        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault.getFooterView();

        noticeBeanOnionRecycleAdapter.setLoadMoreContainer(getPresenter().loadMoreDefault);

        //设置布局的方式
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        int spacing = ScreenUtils.dip2px(mContext, 10);//每一个矩形的间距
        //设置每个item间距
        mRvProductList.addItemDecoration(new HeaderGridSpacingItemDecoration(2, spacing, true, 0));
        mRvProductList.setNestedScrollingEnabled(false);
        mRvProductList.setLayoutManager(layoutManager);// 布局管理器。
        mRvProductList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvProductList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvProductList.setAdapter(noticeBeanOnionRecycleAdapter);


        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();
//                getPresenter().finance_list();

            }
        });

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();
    }


    /**
     * 关闭刷新/更新数据
     */
    public void updateList() {
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter.notifyDataSetChanged();
    }
}
