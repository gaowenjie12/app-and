package com.zsoe.businesssharing.business.me;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.StockBean;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreContainer;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreDefaultFooterRecyclerView;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreHandler;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;
import com.zsoe.businesssharing.utils.DialogManager;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

@RequiresPresenter(KuCunPresenter.class)
public class ProcurementManagementActivity extends BaseActivity<KuCunPresenter> {

    private RecyclerView mRvKuncunList;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_management);
        type = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        initView();
        if (type == 1) {
            initTitleText("库存管理");
        } else {
            initTitleText("采购");
        }

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) { //刷新
                getPresenter().loadMoreDefault.refresh(); //刷新
                getPresenter().stock_purchase_list(DApplication.getInstance().getLoginUser().getId() + "", type + "");
            }
        });

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();
    }

    private void initView() {
        mRvKuncunList = (RecyclerView) findViewById(R.id.rv_kuncun_list);

        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<StockBean>(R.layout.item_kuncun_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final StockBean item) {
                super.convert(holder, item);

                holder.setText(R.id.tv_title, item.getTitle());
                holder.setText(R.id.tv_time, item.getDes());

                holder.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogManager.getInstance().showNetLoadingView(mContext);
                        getPresenter().stockpurchase_del(DApplication.getInstance().getLoginUser().getId() + "", item.getId() + "");
                    }
                });

            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().stock_purchase_list(DApplication.getInstance().getLoginUser().getId() + "", type + "");
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault.getFooterView();
        noticeBeanOnionRecycleAdapter.setLoadMoreContainer(getPresenter().loadMoreDefault);


        mRvKuncunList.setFocusableInTouchMode(false);
        mRvKuncunList.setNestedScrollingEnabled(false);
        mRvKuncunList.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvKuncunList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvKuncunList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvKuncunList.setAdapter(noticeBeanOnionRecycleAdapter);

    }

    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<StockBean> noticeBeanList = new ArrayList<>();

    /**
     * 关闭刷新/更新数据
     */
    public void updateList() {
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter.notifyDataSetChanged();
    }

    public void delSuccess() {
        mPtrFrame.autoRefresh();
    }
}
