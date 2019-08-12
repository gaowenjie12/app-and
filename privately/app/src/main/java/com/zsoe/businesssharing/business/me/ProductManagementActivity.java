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
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.bean.BannerItemBean;
import com.zsoe.businesssharing.business.exhibitionhall.ProductDetailActivity;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class ProductManagementActivity extends BaseActivity {

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

            }
        });

        initTitleText("产品管理");
        setDate();
    }

    private void initView() {
        mRvNotice = (RecyclerView) findViewById(R.id.rv_notice);
    }

    public void setDate() {
        List<BannerItemBean> bannerItemBeans = new ArrayList<>();

        BannerItemBean bannerItemBean = new BannerItemBean();
        bannerItemBean.setUrl_title("简介");
        bannerItemBean.setImg("http://hbimg.b0.upaiyun.com/3e14d836d89498b116834b2987dbaa1c8f2e85a418a9fc-nLVWsW_fw658");
        bannerItemBeans.add(bannerItemBean);

        BannerItemBean bannerItemBean2 = new BannerItemBean();
        bannerItemBean2.setUrl_title("简介");

        bannerItemBean2.setImg("http://hbimg.b0.upaiyun.com/9be8e0054e2ed5e02fa91c6c66267f9d51859e951b83e-qMhDYE_fw658");
        bannerItemBeans.add(bannerItemBean2);

        BannerItemBean bannerItemBean3 = new BannerItemBean();
        bannerItemBean3.setUrl_title("简介");

        bannerItemBean3.setImg("http://img694.ph.126.net/2CR9OPpnSjmHa_7BzGVE9Q==/2868511487659481204.jpg");
        bannerItemBeans.add(bannerItemBean3);

        BannerItemBean bannerItemBean4 = new BannerItemBean();
        bannerItemBean4.setUrl_title("简介");

        bannerItemBean4.setImg("http://i1.hdslb.com/bfs/archive/20b81aa9dcffd6db03dc14296ff3b84874f0c529.png");
        bannerItemBeans.add(bannerItemBean4);


        OnionRecycleAdapter hangyeAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_chanpin_guanli_list_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getImg());
                holder.setText(R.id.tv_name, "南繁，筑牢粮食安全的创新底座 聚力建设幸福美好新甘肃");
                holder.setText(R.id.tv_zhiwei, "7月4日 16:00 朝阳区");
                holder.setText(R.id.tv_p_c, "1268人看过");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, ProductDetailActivity.class));
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
