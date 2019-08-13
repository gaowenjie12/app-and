package com.zsoe.businesssharing.business.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class MySignActivity extends BaseActivity {

    private ImageView mIvImage;
    /**
     * 2天
     */
    private TextView mTvDaycount;
    /**
     * 签到
     */
    private TextView mTvQiandao;
    /**
     * 连续签到三天赠送排名券
     * 连续签到五天赠送排名券
     */
    private TextView mTvFirstGuize;
    private RecyclerView mRvCard;
    /**
     * 连续签到三天赠送排名券
     * 连续签到五天赠送排名券
     */
    private TextView mTvSecondGuize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sign);
        initView();
        initTitleText("我的签到");

        setDate();
    }

    private void initView() {
        mIvImage = (ImageView) findViewById(R.id.iv_image);
        mTvDaycount = (TextView) findViewById(R.id.tv_daycount);
        mTvQiandao = (TextView) findViewById(R.id.tv_qiandao);
        mTvFirstGuize = (TextView) findViewById(R.id.tv_first_guize);
        mRvCard = (RecyclerView) findViewById(R.id.rv_card);
        mTvSecondGuize = (TextView) findViewById(R.id.tv_second_guize);
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


        OnionRecycleAdapter hangyeAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_sign_card_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                holder.setText(R.id.tv_name, "北京字节跳动科技有限公司");
                holder.setText(R.id.tv_tiaojian, "发布时间：2019-07-07");

                holder.getView(R.id.tv_shiyong).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        };

        mRvCard.setFocusableInTouchMode(false);
        mRvCard.setNestedScrollingEnabled(false);
        mRvCard.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvCard.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvCard.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvCard.setAdapter(hangyeAdapter);
    }
}
