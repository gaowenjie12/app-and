package com.zsoe.businesssharing.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.bean.BannerItemBean;
import com.zsoe.businesssharing.business.exhibitionhall.CommunicationMeetingForeshowActivity;
import com.zsoe.businesssharing.business.exhibitionhall.CompanyProfilesActivity;
import com.zsoe.businesssharing.business.exhibitionhall.EventDetailsActivity;
import com.zsoe.businesssharing.business.exhibitionhall.LatestNewsActivity;
import com.zsoe.businesssharing.business.exhibitionhall.ProductDetailActivity;
import com.zsoe.businesssharing.business.exhibitionhall.ProductListActivity;
import com.zsoe.businesssharing.business.home.FinancingLoansActivity;
import com.zsoe.businesssharing.business.home.JoinInvestmentActivity;
import com.zsoe.businesssharing.business.home.ProcurementAndInventoryActivity;
import com.zsoe.businesssharing.business.home.SearchActivity;
import com.zsoe.businesssharing.business.me.MessageRemindActivity;
import com.zsoe.businesssharing.commonview.ClearEditText;
import com.zsoe.businesssharing.commonview.UpDownViewSwitcher;
import com.zsoe.businesssharing.commonview.banner.BannerView;
import com.zsoe.businesssharing.commonview.citypicker.CityPicker;
import com.zsoe.businesssharing.commonview.citypicker.adapter.OnPickListener;
import com.zsoe.businesssharing.commonview.citypicker.model.City;
import com.zsoe.businesssharing.commonview.citypicker.model.LocateState;
import com.zsoe.businesssharing.commonview.citypicker.model.LocatedCity;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * 首页
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    public static HomeFragment newInstance(String title) {
        HomeFragment f = new HomeFragment();
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
        return R.layout.fragment_home;
    }

    private BannerView banner_view;
    private TextView kunc, jianzhi, zhaoshang, daikuan, tv_tuiguang_more,
            tv_caigou_more, tv_jiazhi_more, tv_zhaoshang_more, tv_xinwen_more, tv_diqu;
    private RecyclerView rv_tuiguang, rv_caigou, rv_jiazhi, rv_zhaoshang;
    private UpDownViewSwitcher home_view_switcher;
    private ClearEditText search_input;
    private ImageView iv_xiaoxi;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        banner_view = view.findViewById(R.id.banner_view);
        home_view_switcher = view.findViewById(R.id.home_view_switcher);

        kunc = view.findViewById(R.id.kunc);
        jianzhi = view.findViewById(R.id.jianzhi);
        zhaoshang = view.findViewById(R.id.zhaoshang);
        daikuan = view.findViewById(R.id.daikuan);
        tv_tuiguang_more = view.findViewById(R.id.tv_tuiguang_more);
        tv_caigou_more = view.findViewById(R.id.tv_caigou_more);
        tv_jiazhi_more = view.findViewById(R.id.tv_jiazhi_more);
        tv_zhaoshang_more = view.findViewById(R.id.tv_zhaoshang_more);

        rv_tuiguang = view.findViewById(R.id.rv_tuiguang);
        rv_caigou = view.findViewById(R.id.rv_caigou);
        rv_jiazhi = view.findViewById(R.id.rv_jiazhi);
        rv_zhaoshang = view.findViewById(R.id.rv_zhaoshang);
        tv_xinwen_more = view.findViewById(R.id.tv_xinwen_more);
        tv_diqu = view.findViewById(R.id.tv_diqu);
        iv_xiaoxi = view.findViewById(R.id.iv_xiaoxi);
        search_input = view.findViewById(R.id.search_input);
        search_input.setEnabled(true);
        search_input.setFocusable(false);

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新

            }
        });

        //左右滑动时刷新控件禁止掉
        mPtrFrame.disableWhenHorizontalMove(true);

        kunc.setOnClickListener(this);
        jianzhi.setOnClickListener(this);
        zhaoshang.setOnClickListener(this);
        daikuan.setOnClickListener(this);
        tv_tuiguang_more.setOnClickListener(this);
        tv_caigou_more.setOnClickListener(this);
        tv_jiazhi_more.setOnClickListener(this);
        tv_zhaoshang_more.setOnClickListener(this);
        tv_xinwen_more.setOnClickListener(this);
        tv_diqu.setOnClickListener(this);
        iv_xiaoxi.setOnClickListener(this);
        search_input.setOnClickListener(this);

        setDate();
    }

    public void setDate() {

        final List<String> stringList = new ArrayList<>();
        stringList.add("七次国事访问，习近平受到这些“特殊”礼遇");
        stringList.add("自上而下与自下而上形成合力");
        stringList.add("马骏：人民币汇率\"破7\"与\"8·11\"汇改有五点不同");
        stringList.add("香港局势座谈会  爱国爱港是香港社会主流");

        home_view_switcher.setSwitcheNextViewListener(new UpDownViewSwitcher.SwitchNextViewListener() {
            @Override
            public void switchTONextView(View nextView, int index) {
                if (nextView == null) return;
                final String tag1 = stringList.get(index % stringList.size());
                ((TextView) nextView.findViewById(R.id.switch_title)).setText(tag1);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext().getApplicationContext(), tag1, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        home_view_switcher.setContentLayout(R.layout.item_home_buttelin_switch_view);


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

        banner_view.setDate(bannerItemBeans);

        OnionRecycleAdapter hangyeAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_tuiguang_list_layout, bannerItemBeans) {
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
                        startActivity(new Intent(mContext, EventDetailsActivity.class));
                    }
                });

            }
        };

        rv_tuiguang.setFocusableInTouchMode(false);
        rv_tuiguang.setNestedScrollingEnabled(false);
        rv_tuiguang.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        rv_tuiguang.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        rv_tuiguang.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        rv_tuiguang.setAdapter(hangyeAdapter);


        OnionRecycleAdapter chanpinAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_caigou_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getImg());
                holder.setText(R.id.tv_name, item.getUrl_title());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, CompanyProfilesActivity.class));
                    }
                });

            }
        };


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rv_caigou.setFocusableInTouchMode(false);
        rv_caigou.setNestedScrollingEnabled(false);
        rv_caigou.setLayoutManager(linearLayoutManager);// 布局管理器。
        rv_caigou.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        rv_caigou.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        rv_caigou.setAdapter(chanpinAdapter);


        OnionRecycleAdapter jiazhiAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_jiazhi_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getImg());
                holder.setText(R.id.tv_name, item.getUrl_title());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, ProductDetailActivity.class));

                    }
                });

            }
        };

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        rv_jiazhi.setFocusableInTouchMode(false);
        rv_jiazhi.setNestedScrollingEnabled(false);
        rv_jiazhi.setLayoutManager(linearLayoutManager2);// 布局管理器。
        rv_jiazhi.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        rv_jiazhi.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        rv_jiazhi.setAdapter(jiazhiAdapter);


        OnionRecycleAdapter zhaoshangAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_tuiguang_list_layout, bannerItemBeans) {
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
                        startActivity(new Intent(mContext, EventDetailsActivity.class));
                    }
                });


            }
        };


        rv_zhaoshang.setFocusableInTouchMode(false);
        rv_zhaoshang.setNestedScrollingEnabled(false);
        rv_zhaoshang.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        rv_zhaoshang.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        rv_zhaoshang.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        rv_zhaoshang.setAdapter(zhaoshangAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.kunc:

                startActivity(new Intent(mContext, ProcurementAndInventoryActivity.class));

                break;

            case R.id.jianzhi:
                startActivity(new Intent(mContext, ProductListActivity.class));

                break;

            case R.id.zhaoshang:
                startActivity(new Intent(mContext, JoinInvestmentActivity.class));

                break;
            case R.id.daikuan:
                startActivity(new Intent(mContext, FinancingLoansActivity.class));

                break;
            case R.id.tv_tuiguang_more:
                startActivity(new Intent(mContext, CommunicationMeetingForeshowActivity.class));
                break;
            case R.id.tv_caigou_more:
                startActivity(new Intent(mContext, ProcurementAndInventoryActivity.class));

                break;
            case R.id.tv_jiazhi_more:
                startActivity(new Intent(mContext, ProductListActivity.class));
                break;
            case R.id.tv_zhaoshang_more:
                startActivity(new Intent(mContext, JoinInvestmentActivity.class));

                break;
            case R.id.tv_xinwen_more:
                startActivity(new Intent(mContext, LatestNewsActivity.class));
                break;

            case R.id.tv_diqu:

                CityPicker.from(getActivity())
                        .enableAnimation(true)
//                        .setAnimationStyle(R.style.CustomAnim)
                        .setLocatedCity(null)
                        .setHotCities(null)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                tv_diqu.setText(data.getName());
                            }

                            @Override
                            public void onCancel() {

                            }

                            @Override
                            public void onLocate() {
                                //开始定位，这里模拟一下定位
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        CityPicker.from(getActivity()).locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                                    }
                                }, 3000);
                            }
                        })
                        .show();
                break;

            case R.id.iv_xiaoxi:
                startActivity(new Intent(mContext, MessageRemindActivity.class));
                break;

            case R.id.search_input:
                startActivity(new Intent(mContext, SearchActivity.class));
                break;
        }
    }
}
