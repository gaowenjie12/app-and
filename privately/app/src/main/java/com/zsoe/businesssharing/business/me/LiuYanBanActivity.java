package com.zsoe.businesssharing.business.me;

import android.content.Intent;
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

@RequiresPresenter(LiuYanBanListPresenter.class)
public class LiuYanBanActivity extends BaseActivity<LiuYanBanListPresenter> {

    private RecyclerView mRvJoinList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liu_yan_ban);
        initView();

        initTitleText("留言板");

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();
                getPresenter().user_companymsg_list(DApplication.getInstance().getLoginUser().getId() + "");

            }
        });

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();

    }


    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<MessageReturnBean> noticeBeanList = new ArrayList<>();

    private void initView() {
        mRvJoinList = (RecyclerView) findViewById(R.id.rv_join_list);
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
                getPresenter().user_companymsg_list(DApplication.getInstance().getLoginUser().getId() + "");
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
