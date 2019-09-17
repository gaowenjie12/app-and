package com.zsoe.businesssharing.business.me;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ItemMailBox;
import com.zsoe.businesssharing.commonview.CustomEditTextBottomPopup;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreContainer;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreDefaultFooterRecyclerView;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreHandler;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;
import com.zsoe.businesssharing.utils.DialogManager;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

@RequiresPresenter(LiuYanBanReturnListPresenter.class)
public class LiuYanBanReturnActivity extends BaseActivity<LiuYanBanReturnListPresenter> {

    private RecyclerView mRvJoinList;
    private String toId, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_return);
        initView();

        toId = getIntent().getStringExtra(Config.INTENT_PARAMS1);
        type = getIntent().getStringExtra(Config.INTENT_PARAMS2);
        String title = getIntent().getStringExtra(Config.INTENT_PARAMS3);
        initTitleText(title);

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();

                getPresenter().user_companymsg_infolist(DApplication.getInstance().getLoginUser().getId() + "", type, toId);

            }
        });

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();
    }

    private void initView() {
        mRvJoinList = (RecyclerView) findViewById(R.id.rv_join_list);
        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<ItemMailBox>(R.layout.item_from_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemMailBox item) {
                super.convert(holder, item);


                TextView tv_xiaox = holder.getView(R.id.tv_xiaox);
                TextView tv_time = holder.getView(R.id.tv_time);
                TextView tv_huifu = holder.getView(R.id.tv_huifu);
                TextView tv_myhuifu = holder.getView(R.id.tv_myhuifu);
                TextView tv_huifu_time = holder.getView(R.id.tv_huifu_time);
                RelativeLayout rl_huifu = holder.getView(R.id.rl_huifu);
                LinearLayout ll_geiwo = holder.getView(R.id.ll_geiwo);

                //目标id == uid，代表是别人发给我的
                if (!item.isIs_me()) {

                    tv_xiaox.setText(item.getMsg());
                    tv_time.setText(item.getCreatetime());
//is_showbut 是是否显示按钮的意思 1 是显示 0 不显示
                    if (item.getIs_showbut() == 1) {
                        tv_huifu.setVisibility(View.VISIBLE);
                        tv_huifu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                bottomPopupView = new CustomEditTextBottomPopup(mContext).setCommentListener(new CustomEditTextBottomPopup.CommentListener() {
                                    @Override
                                    public void onFinish(String str) {
                                        DialogManager.getInstance().showNetLoadingView(mContext);
                                        getPresenter().user_companymsg_inforeply(DApplication.getInstance().getLoginUser().getId() + "", type, toId, item.getMsg_id() + "", str);
                                    }
                                });

                                new XPopup.Builder(mContext)
                                        .autoOpenSoftInput(true)
                                        .asCustom(bottomPopupView)
                                        .show();
                            }
                        });
                    } else {
                        tv_huifu.setVisibility(View.GONE);

                    }

                    ll_geiwo.setVisibility(View.VISIBLE);
                    rl_huifu.setVisibility(View.GONE);

                } else {
                    //from_uid 代表发信息的用户,如果等于当前用户，代表是我发的
                    ll_geiwo.setVisibility(View.GONE);
                    rl_huifu.setVisibility(View.VISIBLE);
                    tv_myhuifu.setText(item.getMsg());
                    tv_huifu_time.setText(item.getCreatetime());


                }


            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().user_companymsg_infolist(DApplication.getInstance().getLoginUser().getId() + "", type, toId);

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
    private List<ItemMailBox> noticeBeanList = new ArrayList<>();


    /**
     * 关闭刷新/更新数据
     */
    public void updateList() {
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter.notifyDataSetChanged();
    }

    BottomPopupView bottomPopupView;

    public void setMsgSuccess(RootResponse t) {
        ToastUtils.showShort(t.getMsg());
        bottomPopupView.dismiss();
    }
}
