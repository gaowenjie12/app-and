package com.zsoe.businesssharing.business.main;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.bean.BannerItemBean;
import com.zsoe.businesssharing.business.exhibitionhall.CommunicationMeetingForeshowActivity;
import com.zsoe.businesssharing.business.exhibitionhall.ExpandAdapter;
import com.zsoe.businesssharing.business.exhibitionhall.IndustryClassificationActivity;
import com.zsoe.businesssharing.business.exhibitionhall.LatestNewsActivity;
import com.zsoe.businesssharing.business.exhibitionhall.MasterDetailActivity;
import com.zsoe.businesssharing.business.exhibitionhall.MasterListActivity;
import com.zsoe.businesssharing.commonview.HeaderGridSpacingItemDecoration;
import com.zsoe.businesssharing.commonview.banner.BannerView;
import com.zsoe.businesssharing.commonview.expandablelayout.Utils;
import com.zsoe.businesssharing.utils.FrecoFactory;
import com.zsoe.businesssharing.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * 展厅
 */

public class GalleryRoomFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";

    public static GalleryRoomFragment newInstance(String title) {
        GalleryRoomFragment f = new GalleryRoomFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        f.setArguments(args);
        return f;
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
    }

    @Override
    protected int createViewByLayoutId() {
        return R.layout.fragment_galleryroom;
    }

    private BannerView banner_view;
    private RecyclerView hangye_recyclerView;
    private TextView more_tv, tv_daka_more, tv_daliang_more;
    private LinearLayout daka1, daka2, daka3, daka4;
    private CardView daliang1, daliang2, daliang3, daliang4;
    private RelativeLayout jiaoliu1, jiaoliu2, jiaoliu3;
    private ImageView xiajiantou;
    private LinearLayout more_layout;
    private NestedScrollView scrollView;

    ExpandAdapter expandAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        banner_view = view.findViewById(R.id.banner_view);
        hangye_recyclerView = view.findViewById(R.id.hangye_recyclerView);
        more_tv = view.findViewById(R.id.more_tv);
        tv_daka_more = view.findViewById(R.id.tv_daka_more);
        tv_daliang_more = view.findViewById(R.id.tv_daliang_more);
        xiajiantou = view.findViewById(R.id.xiajiantou);
        more_layout = view.findViewById(R.id.more_layout);
        scrollView = view.findViewById(R.id.scrollView);

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新

            }
        });

        //左右滑动时刷新控件禁止掉
        mPtrFrame.disableWhenHorizontalMove(true);


        daka1 = view.findViewById(R.id.daka1);
        daka2 = view.findViewById(R.id.daka2);
        daka3 = view.findViewById(R.id.daka3);
        daka4 = view.findViewById(R.id.daka4);
        daliang1 = view.findViewById(R.id.daliang1);
        daliang2 = view.findViewById(R.id.daliang2);
        daliang3 = view.findViewById(R.id.daliang3);
        daliang4 = view.findViewById(R.id.daliang4);

        jiaoliu1 = view.findViewById(R.id.jiaoliu1);
        jiaoliu2 = view.findViewById(R.id.jiaoliu2);
        jiaoliu3 = view.findViewById(R.id.jiaoliu3);

        List<BannerItemBean> bannerItemBeans = new ArrayList<>();

        BannerItemBean bannerItemBean = new BannerItemBean();
        bannerItemBean.setImg("http://hbimg.b0.upaiyun.com/3e14d836d89498b116834b2987dbaa1c8f2e85a418a9fc-nLVWsW_fw658");
        bannerItemBean.setUrl_title("马云");
        bannerItemBeans.add(bannerItemBean);

        BannerItemBean bannerItemBean2 = new BannerItemBean();
        bannerItemBean2.setImg("http://hbimg.b0.upaiyun.com/9be8e0054e2ed5e02fa91c6c66267f9d51859e951b83e-qMhDYE_fw658");
        bannerItemBean2.setUrl_title("牛云");
        bannerItemBeans.add(bannerItemBean2);

        BannerItemBean bannerItemBean3 = new BannerItemBean();
        bannerItemBean3.setImg("http://img694.ph.126.net/2CR9OPpnSjmHa_7BzGVE9Q==/2868511487659481204.jpg");
        bannerItemBean3.setUrl_title("狗云");
        bannerItemBeans.add(bannerItemBean3);

        BannerItemBean bannerItemBean4 = new BannerItemBean();
        bannerItemBean4.setImg("http://i1.hdslb.com/bfs/archive/20b81aa9dcffd6db03dc14296ff3b84874f0c529.png");
        bannerItemBean4.setUrl_title("猴云");
        bannerItemBeans.add(bannerItemBean4);

//        banner_view.setDate(bannerItemBeans);

        setDate(bannerItemBeans);


        List<BannerItemBean> bannerItemBeans2 = new ArrayList<>();

        BannerItemBean bannerItemBean5 = new BannerItemBean();
        bannerItemBean5.setImg("http://b-ssl.duitang.com/uploads/item/201601/08/20160108194244_JxGRy.thumb.700_0.jpeg");
        bannerItemBean5.setUrl_title("马云");
        bannerItemBeans2.add(bannerItemBean5);

        BannerItemBean bannerItemBean6 = new BannerItemBean();
        bannerItemBean6.setImg("http://cdn.duitang.com/uploads/item/201410/16/20141016202155_5ycRZ.thumb.700_0.jpeg");
        bannerItemBean6.setUrl_title("牛云");
        bannerItemBeans2.add(bannerItemBean6);

        BannerItemBean bannerItemBean7 = new BannerItemBean();
        bannerItemBean7.setImg("http://cdn.duitang.com/uploads/item/201407/24/20140724190906_MCkXs.thumb.700_0.jpeg");
        bannerItemBean7.setUrl_title("狗云");
        bannerItemBeans2.add(bannerItemBean7);

        BannerItemBean bannerItemBean8 = new BannerItemBean();
        bannerItemBean8.setImg("http://cdn.duitang.com/uploads/item/201412/09/20141209183953_uiree.thumb.700_0.jpeg");
        bannerItemBean8.setUrl_title("猴云");
        bannerItemBeans2.add(bannerItemBean8);

        bannerItemBeans2.addAll(bannerItemBeans2);
        bannerItemBeans2.addAll(bannerItemBeans2);
        bannerItemBeans2.addAll(bannerItemBeans2);


        //设置布局的方式
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, spanCount);
        expandAdapter = new ExpandAdapter(mContext, bannerItemBeans2);

        int spacing = ScreenUtils.dip2px(mContext, 10);//每一个矩形的间距

        //设置每个item间距
        hangye_recyclerView.addItemDecoration(new HeaderGridSpacingItemDecoration(spanCount, spacing, includeEdge, 0));
        hangye_recyclerView.setNestedScrollingEnabled(false);
        hangye_recyclerView.setLayoutManager(layoutManager);// 布局管理器。
        hangye_recyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        hangye_recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        hangye_recyclerView.setAdapter(expandAdapter);

        tv_daka_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, MasterListActivity.class));
            }
        });

        tv_daliang_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, MasterListActivity.class));
            }
        });

        if (bannerItemBeans2.size() > 15) {
            more_layout.setVisibility(View.VISIBLE);
            more_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    boolean hide = expandAdapter.toggle();
                    if (hide) {
                        more_tv.setText("更多行业");
                        createRotateAnimator(xiajiantou, 180f, 0f).start();
                        scrollView.smoothScrollTo(0, 0);

                    } else {
                        more_tv.setText("收起更多");
                        createRotateAnimator(xiajiantou, 0f, 180f).start();
                    }
                }
            });
        } else {
            more_layout.setVisibility(View.GONE);
        }
    }

    int spanCount = 5;//跟布局里面的spanCount属性是一致的
    boolean includeEdge = true;//如果设置成false那边缘地带就没有间距


    private void setDate(List<BannerItemBean> bannerItemBeans) {
        daka1.setVisibility(View.INVISIBLE);
        daka2.setVisibility(View.INVISIBLE);
        daka3.setVisibility(View.INVISIBLE);
        daka4.setVisibility(View.INVISIBLE);
        daliang1.setVisibility(View.INVISIBLE);
        daliang2.setVisibility(View.INVISIBLE);
        daliang3.setVisibility(View.INVISIBLE);
        daliang4.setVisibility(View.INVISIBLE);

        List<LinearLayout> dakaViews = new ArrayList<>();
        dakaViews.add(daka1);
        dakaViews.add(daka2);
        dakaViews.add(daka3);
        dakaViews.add(daka4);

        List<CardView> daliangViews = new ArrayList<>();
        daliangViews.add(daliang1);
        daliangViews.add(daliang2);
        daliangViews.add(daliang3);
        daliangViews.add(daliang4);

        List<RelativeLayout> jiaoliuViews = new ArrayList<>();
        jiaoliuViews.add(jiaoliu1);
        jiaoliuViews.add(jiaoliu2);
        jiaoliuViews.add(jiaoliu3);

        for (int i = 0; i < bannerItemBeans.size(); i++) {
            BannerItemBean bannerItemBean = bannerItemBeans.get(i);
            LinearLayout linearLayout = dakaViews.get(i);
            linearLayout.setVisibility(View.VISIBLE);
            SimpleDraweeView simpleDraweeView = linearLayout.findViewById(R.id.image);
            FrecoFactory.getInstance().disPlay(simpleDraweeView, bannerItemBean.getImg());
            TextView tv_name = linearLayout.findViewById(R.id.tv_name);
            tv_name.setText(bannerItemBean.getUrl_title());

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext, MasterDetailActivity.class));

                }
            });
        }

        for (int i = 0; i < bannerItemBeans.size(); i++) {
            BannerItemBean bannerItemBean = bannerItemBeans.get(i);
            CardView linearLayout = daliangViews.get(i);
            linearLayout.setVisibility(View.VISIBLE);
            SimpleDraweeView simpleDraweeView = linearLayout.findViewById(R.id.image);
            FrecoFactory.getInstance().disPlay(simpleDraweeView, bannerItemBean.getImg());
            TextView tv_name = linearLayout.findViewById(R.id.tv_name);
            TextView tv_dongtai = linearLayout.findViewById(R.id.tv_dongtai);
            tv_name.setText(bannerItemBean.getUrl_title());

            tv_dongtai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext, LatestNewsActivity.class));
                }
            });
        }

        List<BannerItemBean> bannerItemBeans1 = bannerItemBeans.subList(0, 3);
        for (int i = 0; i < bannerItemBeans1.size(); i++) {
            BannerItemBean bannerItemBean = bannerItemBeans1.get(i);
            RelativeLayout relativeLayout = jiaoliuViews.get(i);
            SimpleDraweeView simpleDraweeView = relativeLayout.findViewById(R.id.image);
            FrecoFactory.getInstance().disPlay(simpleDraweeView, bannerItemBean.getImg());
            TextView tv_dongtai = relativeLayout.findViewById(R.id.tv_dongtai);

            if (i == 0) {
                tv_dongtai.setText("交流会分类");
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, IndustryClassificationActivity.class));
                    }
                });
            } else if (i == 1) {
                tv_dongtai.setText("交流会预告");
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startActivity(new Intent(mContext, CommunicationMeetingForeshowActivity.class));

                    }
                });
            } else if (i == 2) {
                tv_dongtai.setText("成果展示");
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, CommunicationMeetingForeshowActivity.class));
                    }
                });
            }

        }

    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

}
