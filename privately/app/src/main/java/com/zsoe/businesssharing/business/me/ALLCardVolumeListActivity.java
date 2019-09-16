package com.zsoe.businesssharing.business.me;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ItemInsdustry;
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

@RequiresPresenter(ALLCardVolumePresenter.class)
public class ALLCardVolumeListActivity extends BaseActivity<ALLCardVolumePresenter> {

    private RecyclerView mRvNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_volume_list);
        initView();
        initTitleText("卡卷列表");

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();
                getPresenter().cardticket_list(DApplication.getInstance().getLoginUser().getId() + "");
            }
        });

        setTitleRightText("卡卷列表", new Action1<View>() {
            @Override
            public void call(View view) {

            }
        });

        mPtrFrame.autoRefresh();
    }

    int currentPosition;

    private void initView() {

        mRvNotice = (RecyclerView) findViewById(R.id.rv_notice);

        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<ItemInsdustry>(R.layout.item_card_volume_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemInsdustry item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);

                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getAvatar());

                holder.setText(R.id.tv_title, item.getName());
                holder.setText(R.id.tv_company_name, item.getCompanyname());
                holder.setText(R.id.tv_note, item.getDes());


                TextView tv_delete = holder.getView(R.id.tv_delete);
                if (item.getIs_has() == 1) {
                    tv_delete.setText("已领取");
                } else {
                    tv_delete.setText("领取");
                    holder.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            currentPosition = holder.getAdapterPosition();
                            DialogManager.getInstance().showNetLoadingView(mContext);
                            getPresenter().getcardticket(DApplication.getInstance().getLoginUser().getId() + "", item.getId() + "");
                        }
                    });
                }
            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().cardticket_list(DApplication.getInstance().getLoginUser().getId() + "");
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault.getFooterView();
        noticeBeanOnionRecycleAdapter.setLoadMoreContainer(getPresenter().loadMoreDefault);


        mRvNotice.setFocusableInTouchMode(false);
        mRvNotice.setNestedScrollingEnabled(false);
        mRvNotice.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvNotice.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvNotice.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvNotice.setAdapter(noticeBeanOnionRecycleAdapter);

    }

    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<ItemInsdustry> noticeBeanList = new ArrayList<>();


    /**
     * 关闭刷新/更新数据
     */
    public void updateList() {
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter.notifyDataSetChanged();
    }

    public void delSuccess() {
        List<ItemInsdustry> noticeBeanList = noticeBeanOnionRecycleAdapter.getData();
        ItemInsdustry itemInsdustry = noticeBeanList.get(currentPosition);
        itemInsdustry.setIs_has(1);
        noticeBeanOnionRecycleAdapter.notifyItemChanged(currentPosition, 0);
    }

}
