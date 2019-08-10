package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.zsoe.businesssharing.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import rx.functions.Action1;

public class ProductDetailActivity extends BaseActivity {

    private JzvdStd mJzVideo;
    private FrameLayout mFlVideo;
    private TextView mTvTitle;
    private TextView mTvCompanyName;
    private ExpandableTextView mTvJieshao;
    private RelativeLayout mRlChakan;
    private RecyclerView mRvPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initTitleText("产品介绍");

        setTitleRightSecondIcon(R.mipmap.liebiao, new Action1<View>() {
            @Override
            public void call(View view) {
                startActivity(new Intent(mContext, ProductListActivity.class));
            }
        });

        setTitleRigthIcon(R.mipmap.shoucang, new Action1<View>() {
            @Override
            public void call(View view) {

            }
        });

        initView();
        setDate();
    }

    private void initView() {
        mRvPic = (RecyclerView) findViewById(R.id.rv_pic);

        mJzVideo = (JzvdStd) findViewById(R.id.jz_video);
        mFlVideo = (FrameLayout) findViewById(R.id.fl_video);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        mTvJieshao = (ExpandableTextView) findViewById(R.id.tv_jieshao);
        mRlChakan = (RelativeLayout) findViewById(R.id.rl_chakan);
    }

    public void setDate() {
        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams params = mJzVideo.getLayoutParams();
        // 宽度为屏幕宽度
        params.width = getResources().getDisplayMetrics().widthPixels;
        // 高度为宽度的9/16
        params.height = (int) (params.width * 9f / 16f);
        mJzVideo.setLayoutParams(params);

        JZDataSource jzDataSource = new JZDataSource("http://v.cdn.sohu.com/93/7a28fd59b399ae79ea441253504a278a/0");
        mJzVideo.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL);

        mJzVideo.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        GlideUtils.loadImage(mContext, "http://p.cdn.sohu.com/089a1b13/5b4098facc8075a2012419c8793270bc", mJzVideo.thumbImageView);


        mTvTitle.setText("王者荣耀：国服诸葛亮极限手速一闪大招二技能连招，视觉盛宴");
        mTvCompanyName.setText("北京智能科技有限公司");
        mTvJieshao.setText("在终端入口方面，华为拥有竞争对手无法比拟的优势，此前华为提出的“1+8+N” 全场景智慧服务战略口号是一个非常明显的例证。" +
                "1是手机终端为主要入口;8是包括PC、平板、智慧大屏、车机在内容的4个大屏入口，加上耳机、音箱、手表，" +
                "眼镜在内4个非大屏入口;N是指泛IoT硬件构成的华为HiLink生态。" +
                "颇有远见的产品布局，让华为在近几年以肉眼可见的速度形成了自己的生态壁垒。近两年，华为一直不断对外发出发展全场景生态战略的信号，" +
                "生态系统的建立又显然不是一朝一夕可以完成，需要更多合作伙伴的加入。从近来华为的开发姿态和此次开发者大会释放的诸多信号，" +
                "华为正在朝着既定的目标努力迈进");


        mRlChakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CompanyProfilesActivity.class));
            }
        });

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

        bannerItemBeans.addAll(bannerItemBeans);
        bannerItemBeans.addAll(bannerItemBeans);

        OnionRecycleAdapter jiazhiAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_xuanchuanpian_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getImg());
            }
        };

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        mRvPic.setFocusableInTouchMode(false);
        mRvPic.setNestedScrollingEnabled(false);
        mRvPic.setLayoutManager(linearLayoutManager2);// 布局管理器。
        mRvPic.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvPic.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvPic.setAdapter(jiazhiAdapter);

    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
