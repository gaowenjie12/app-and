package com.zsoe.businesssharing.business.me;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.bean.BannerItemBean;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class ProcurementManagementActivity extends BaseActivity {

    private RecyclerView mRvKuncunList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_management);
        initView();
        initTitleText("采购/库存管理");
        setDate();

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新

            }
        });
    }

    private void initView() {
        mRvKuncunList = (RecyclerView) findViewById(R.id.rv_kuncun_list);
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


        OnionRecycleAdapter hangyeAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_kuncun_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                holder.setText(R.id.tv_title, "北京字节跳动科技有限公司");
                holder.setText(R.id.tv_time, "发布时间：2019-07-07");

                holder.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        };

        mRvKuncunList.setFocusableInTouchMode(false);
        mRvKuncunList.setNestedScrollingEnabled(false);
        mRvKuncunList.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvKuncunList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvKuncunList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvKuncunList.setAdapter(hangyeAdapter);
    }
}
