package com.zsoe.businesssharing.business.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.MessageReturnBean;
import com.zsoe.businesssharing.commonview.ClearEditText;
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
public class MessageRemindActivity extends BaseActivity<MessageRemindListPresenter> implements View.OnClickListener {

    private RecyclerView mRvJoinList;
    /**
     * 请输入要搜索的内容
     */
    private ClearEditText mSearchInput;
    /**
     * 搜索
     */
    private TextView mTvSousuo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_remind);
        initView();

        int type = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        if (type == 1) {
            initTitleText("领导回信");
        } else {
            initTitleText("信箱");
        }

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                //刷新
                if (TextUtils.isEmpty(keyword)) {
                    getPresenter().loadMoreDefault.refresh();
                } else {
                    getPresenter().loadMoreDefault2.refresh();
                }

                getPresenter().mailbox_list(DApplication.getInstance().getLoginUser().getId() + "",keyword);

            }
        });

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();


        mSearchInput = (ClearEditText) findViewById(R.id.search_input);
        mSearchInput.setClearClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "";
                mRvJoinList.setAdapter(noticeBeanOnionRecycleAdapter);
            }
        });
    }

    private String keyword = "";


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
                        Intent intent = new Intent(mContext, MessageReturnActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1, item.getTo_uid() + "");
                        intent.putExtra(Config.INTENT_PARAMS2, item.getTitle());
                        startActivity(intent);
                    }
                });

            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().mailbox_list(DApplication.getInstance().getLoginUser().getId() + "",keyword);
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault.getFooterView();
        noticeBeanOnionRecycleAdapter.setLoadMoreContainer(getPresenter().loadMoreDefault);


        noticeBeanOnionRecycleAdapter2 = new OnionRecycleAdapter<MessageReturnBean>(R.layout.item_huixin_layout, noticeBeanList2) {
            @Override
            protected void convert(BaseViewHolder holder, final MessageReturnBean item) {
                super.convert(holder, item);


                holder.setText(R.id.tv_title, item.getTitle());
                holder.setText(R.id.tv_content, item.getMsg());
                holder.setText(R.id.tv_time, item.getTime());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, MessageReturnActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1, item.getTo_uid() + "");
                        intent.putExtra(Config.INTENT_PARAMS2, item.getTitle());
                        startActivity(intent);
                    }
                });

            }
        };


        getPresenter().loadMoreDefault2 = new OpenLoadMoreDefault(mContext, noticeBeanList2);
        getPresenter().loadMoreDefault2.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().mailbox_list(DApplication.getInstance().getLoginUser().getId() + "",keyword);
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView2 = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault2.getFooterView();
        noticeBeanOnionRecycleAdapter2.setLoadMoreContainer(getPresenter().loadMoreDefault2);


        mRvJoinList.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvJoinList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvJoinList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvJoinList.setAdapter(noticeBeanOnionRecycleAdapter);
        mSearchInput = (ClearEditText) findViewById(R.id.search_input);
        mTvSousuo = (TextView) findViewById(R.id.tv_sousuo);
        mTvSousuo.setOnClickListener(this);
    }


    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<MessageReturnBean> noticeBeanList = new ArrayList<>();


    OnionRecycleAdapter noticeBeanOnionRecycleAdapter2;
    private List<MessageReturnBean> noticeBeanList2 = new ArrayList<>();


    /**
     * 关闭刷新/更新数据
     */
    public void updateList() {
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter.notifyDataSetChanged();
    }


    boolean isFirstSearch = false;

    /**
     * 关闭刷新/更新数据
     */
    public void updateList2() {
        if (isFirstSearch) {
            mRvJoinList.setAdapter(noticeBeanOnionRecycleAdapter2);
        }
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter2.notifyDataSetChanged();
        isFirstSearch = false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_sousuo:
                keyword = mSearchInput.getText().toString();
                if (TextUtils.isEmpty(keyword)) {
                    ToastUtils.showShort("请输入关键字");
                    return;
                }

                mPtrFrame.autoRefresh();
                isFirstSearch = true;
                break;
        }
    }
}
