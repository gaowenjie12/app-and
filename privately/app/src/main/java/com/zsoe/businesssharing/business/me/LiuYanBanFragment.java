package com.zsoe.businesssharing.business.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.MessageReturnBean;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreContainer;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreDefaultFooterRecyclerView;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreHandler;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;
import com.zsoe.businesssharing.utils.DialogManager;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * 银行信贷
 */


@RequiresPresenter(LiuYanBanListPresenter.class)
public class LiuYanBanFragment extends BaseFragment<LiuYanBanListPresenter> {

    private static final String TAG = "HomeFragment";

    public static LiuYanBanFragment newInstance(String title) {
        LiuYanBanFragment f = new LiuYanBanFragment();
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
        return R.layout.activity_message_remind2;
    }

    private RecyclerView mRvJoinList;

    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<MessageReturnBean> noticeBeanList = new ArrayList<>();

    private String type;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = getArguments().getString("title");
        mRvJoinList = (RecyclerView) view.findViewById(R.id.rv_join_list);
        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<MessageReturnBean>(R.layout.item_huixin_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final MessageReturnBean item) {
                super.convert(holder, item);


                holder.setText(R.id.tv_title, item.getTitle());
                holder.setText(R.id.tv_content, item.getMsg());
                holder.setText(R.id.tv_time, item.getTime());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(mContext, LiuYanBanReturnActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1, item.getTo_uid() + "");
                        intent.putExtra(Config.INTENT_PARAMS2, type);
                        intent.putExtra(Config.INTENT_PARAMS3, item.getTitle());

                        startActivity(intent);
                    }
                });

            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().user_companymsg_list(DApplication.getInstance().getLoginUser().getId() + "", type);
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault.getFooterView();
        noticeBeanOnionRecycleAdapter.setLoadMoreContainer(getPresenter().loadMoreDefault);


        mRvJoinList.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvJoinList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvJoinList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvJoinList.setAdapter(noticeBeanOnionRecycleAdapter);


        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();

                getPresenter().user_companymsg_list(DApplication.getInstance().getLoginUser().getId() + "", type);

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
