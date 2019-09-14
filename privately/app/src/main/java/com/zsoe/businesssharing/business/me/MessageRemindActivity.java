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

@RequiresPresenter(MessageRemindListPresenter.class)
public class MessageRemindActivity extends BaseActivity<MessageRemindListPresenter> {

    private RecyclerView mRvJoinList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_remind);
        initView();

        int type = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        if (type == 1) {
            initTitleText("领导回信");
        } else {
            initTitleText("信息");
        }

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();

                getPresenter().mailbox_list(DApplication.getInstance().getLoginUser().getId() + "");

            }
        });

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();
    }

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

                    }
                });

            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().mailbox_list(DApplication.getInstance().getLoginUser().getId() + "");
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault.getFooterView();
        noticeBeanOnionRecycleAdapter.setLoadMoreContainer(getPresenter().loadMoreDefault);


        mRvJoinList.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvJoinList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvJoinList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvJoinList.setAdapter(noticeBeanOnionRecycleAdapter);
    }


    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<MessageReturnBean> noticeBeanList = new ArrayList<>();


    /**
     * 关闭刷新/更新数据
     */
    public void updateList() {
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter.notifyDataSetChanged();
    }

}
