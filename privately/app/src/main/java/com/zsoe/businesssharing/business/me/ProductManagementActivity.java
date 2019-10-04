package com.zsoe.businesssharing.business.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ChanPinBeanItem;
import com.zsoe.businesssharing.business.exhibitionhall.ProductDetailActivity;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.List;

import rx.functions.Action1;

@RequiresPresenter(ProductMangerPresenter.class)
public class ProductManagementActivity extends BaseActivity<ProductMangerPresenter> {

    private RecyclerView mRvNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);
        initView();

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().product_list();
            }
        });

        initTitleText("产品管理");
        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();
    }

    private void initView() {
        mRvNotice = (RecyclerView) findViewById(R.id.rv_notice);
    }

    public void setDate(List<ChanPinBeanItem> noticeBeanList) {
        mPtrFrame.refreshComplete();
        OnionRecycleAdapter hangyeAdapter = new OnionRecycleAdapter<ChanPinBeanItem>(R.layout.item_chanpin_guanli_list_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final ChanPinBeanItem item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());
                holder.setText(R.id.tv_name, item.getProductname());
                holder.setText(R.id.tv_zhiwei, item.getContent());
                holder.setText(R.id.tv_p_c, item.getReadnum() + "人看过");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, ProductDetailActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1, item.getId() );
                        startActivity(intent);
                    }
                });

            }
        };

        mRvNotice.setFocusableInTouchMode(false);
        mRvNotice.setNestedScrollingEnabled(false);
        mRvNotice.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvNotice.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvNotice.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvNotice.setAdapter(hangyeAdapter);
    }
}
