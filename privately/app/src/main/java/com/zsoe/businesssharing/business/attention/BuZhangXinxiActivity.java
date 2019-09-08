package com.zsoe.businesssharing.business.attention;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ItemBuZhangXinxBean;
import com.zsoe.businesssharing.commonview.CustomEditTextBottomPopup;
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

@RequiresPresenter(BuZhangXInxiPresenter.class)
public class BuZhangXinxiActivity extends BaseActivity<BuZhangXInxiPresenter> {

    private RecyclerView mRvProductList;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bu_zhang_xinxi);
        initView();
        type = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);


        if (type == 3) {
            initTitleText("部长信箱");
        } else {
            initTitleText("大会长信箱");
        }


        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();
                getPresenter().minister_list(type + "");
            }
        });

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();
    }

    private void initView() {
        mRvProductList = (RecyclerView) findViewById(R.id.rv_product_list);

        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<ItemBuZhangXinxBean>(R.layout.item_buzhangxinxiang_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemBuZhangXinxBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getAvatar());

                holder.setText(R.id.tv_title, item.getStaffname());
                holder.setText(R.id.tv_real_name, item.getRealname());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        bottomPopupView = new CustomEditTextBottomPopup(mContext).setCommentListener(new CustomEditTextBottomPopup.CommentListener() {
                            @Override
                            public void onFinish(String str) {
                                DialogManager.getInstance().showNetLoadingView(mContext);
                                getPresenter().save_mailbox_msg(item.getId()+"", DApplication.getInstance().getLoginUser().getId() + "", str);
                            }
                        });

                        new XPopup.Builder(mContext)
                                .autoOpenSoftInput(true)
                                .asCustom(bottomPopupView)
                                .show();
                    }
                });
            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().minister_list(type + "");

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


    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<ItemBuZhangXinxBean> noticeBeanList = new ArrayList<>();

    BottomPopupView bottomPopupView;

    public void setMsgSuccess(RootResponse t) {
        ToastUtils.showShort(t.getMsg());
        bottomPopupView.dismiss();
    }
}
