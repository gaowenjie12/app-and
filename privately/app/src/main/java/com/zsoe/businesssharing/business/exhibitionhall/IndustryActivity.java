package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.bean.BannerItemBean;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/***
 * 行业
 * */
public class IndustryActivity extends BaseActivity implements View.OnClickListener {

    private ExpandableTextView mTvXiangqing;
    /**
     * 更多
     */
    private TextView mTvHangyeMore;
    private RecyclerView mRvHangye;
    /**
     * 更多
     */
    private TextView mTvChanpinMore;
    private RecyclerView mRvChanpin;
    private NestedScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry);
        initTitleText("行业");

        initView();

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新

            }
        });

        //左右滑动时刷新控件禁止掉
        mPtrFrame.disableWhenHorizontalMove(true);

        setDate();
    }

    private void initView() {
        mTvXiangqing = (ExpandableTextView) findViewById(R.id.tv_xiangqing);
        mTvHangyeMore = (TextView) findViewById(R.id.tv_hangye_more);
        mTvHangyeMore.setOnClickListener(this);
        mRvHangye = (RecyclerView) findViewById(R.id.rv_hangye);
        mTvChanpinMore = (TextView) findViewById(R.id.tv_chanpin_more);
        mTvChanpinMore.setOnClickListener(this);
        mRvChanpin = (RecyclerView) findViewById(R.id.rv_chanpin);
        mScrollView = (NestedScrollView) findViewById(R.id.scrollView);
    }

    public void setDate() {

        mTvHangyeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CompaniesListActivity.class));
            }
        });

        mTvChanpinMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ProductListActivity.class));
            }
        });

        mTvXiangqing.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpandStateChanged(TextView textView, boolean isExpanded) {
                if (!isExpanded) {
                    mScrollView.smoothScrollTo(0, 0);
                }
            }
        });

        mTvXiangqing.setText("腾讯科技讯 最近，三大语音助手服务商亚马逊、谷歌和苹果都爆出了雇佣外部承包商，对语音助手用户录音进行审听的消息，" +
                "引发了外界批评，认为侵犯了用户隐私权。其中，苹果公司还有额外的问题，就是Siri未经许可进行录音。据国外媒体报道，苹果公司已经为此遭遇了一场集体诉讼，原告指控称苹果为了质量控制和产品改进，" +
                "雇佣外部承包商审听并对一些匿名的Siri用户对话进行评分。据国外媒体报道，苹果Siri的做法在最近的一则新闻报道中得到了证实，其中一名承包商称，如果Siri是被意外激活时，评估Siri录音的苹果员工经常会听到十分私密的用户信息" +
                "，比如医疗信息、毒品交易和其他信息.原告日前前在加州北区一家法院提起了诉讼，诉讼列为集体诉讼，原告指控苹果“在未经个人同意的情况下，非法和故意地记录个人的保密通信”，违反了加州的隐私法," +
                "原告在起诉书中指出，Siri设备只应该记录“嘿Siri”(一个唤醒词)之后的用户对话，或者通过特定的手势，比如按下设备上的主屏键一段特定的时间。加州法律禁止在未经所有当事人同意的情况下对口头交流进行录音");

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

        OnionRecycleAdapter hangyeAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_hangye_list_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getImg());
                holder.setText(R.id.tv_name, item.getUrl_title());
                holder.setText(R.id.tv_jianjie, item.getUrl_title());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, CompanyProfilesActivity.class));
                    }
                });

            }
        };


        mRvHangye.setFocusableInTouchMode(false);
        mRvHangye.setNestedScrollingEnabled(false);
        mRvHangye.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvHangye.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvHangye.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvHangye.setAdapter(hangyeAdapter);


        OnionRecycleAdapter chanpinAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_hangye_list_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getImg());
                holder.setText(R.id.tv_name, item.getUrl_title());
                holder.setText(R.id.tv_jianjie, item.getUrl_title());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, CompanyProfilesActivity.class));
                    }
                });

            }
        };


        mRvChanpin.setFocusableInTouchMode(false);
        mRvChanpin.setNestedScrollingEnabled(false);
        mRvChanpin.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvChanpin.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvChanpin.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvChanpin.setAdapter(chanpinAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_hangye_more:
                break;
            case R.id.tv_chanpin_more:
                break;
        }
    }
}
