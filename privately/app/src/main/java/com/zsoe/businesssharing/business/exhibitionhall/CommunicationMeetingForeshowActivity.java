package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.JiaoLiuBean;
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
 * 交流会预告
 */

@RequiresPresenter(JiaoLiuListPresenter.class)
public class CommunicationMeetingForeshowActivity extends BaseActivity<JiaoLiuListPresenter> {

    private RecyclerView mRvProductList;
    private int type = 0, iccate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_meeting_foreshow);
        initView();

        type = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        iccate = getIntent().getIntExtra(Config.INTENT_PARAMS2, -1);

        if (type == 1) {
            initTitleText("交流会预告");

        } else if (type == 2) {
            initTitleText("成果列表");

        } else {
            initTitleText("行业交流");
        }

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                //刷新
                getPresenter().loadMoreDefault.refresh();
                getPresenter().communication_list(type + "", iccate + "", DApplication.getInstance().getLoginUser().getId() + "");
            }
        });

        //左右滑动时刷新控件禁止掉
        mPtrFrame.disableWhenHorizontalMove(true);

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();

    }


    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<JiaoLiuBean> noticeBeanList = new ArrayList<>();

    private void initView() {
        mRvProductList = (RecyclerView) findViewById(R.id.rv_product_list);
        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<JiaoLiuBean>(R.layout.item_foreshow_list_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final JiaoLiuBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());

                holder.setText(R.id.tv_name, item.getTitle());
                holder.setText(R.id.tv_jianshao, "到场嘉宾：" + item.getGuester());
                holder.setText(R.id.tv_zhiwei, item.getActivitytime() + "  " + item.getActivityaddress());
                ImageView iv_shoucang = holder.getView(R.id.iv_shoucang);

                if (item.getIs_collect() == 1) {
                    iv_shoucang.setImageResource(R.mipmap.shoucang);
                } else {
                    iv_shoucang.setImageResource(R.mipmap.shoucang_pre);
                }

                iv_shoucang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.getIs_collect() == 1) {
                            item.setIs_collect(0);
                            iv_shoucang.setImageResource(R.mipmap.shoucang_pre);
                        } else {
                            item.setIs_collect(1);
                            iv_shoucang.setImageResource(R.mipmap.shoucang);
                        }
                    }
                });


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, EventDetailsActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1, item.getId());
                        startActivity(intent);
                    }
                });

            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().communication_list(type + "", iccate + "", DApplication.getInstance().getLoginUser().getId() + "");
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
