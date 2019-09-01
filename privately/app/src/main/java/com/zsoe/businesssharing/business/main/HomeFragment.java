package com.zsoe.businesssharing.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ExtenactivityBean;
import com.zsoe.businesssharing.bean.HeadNews;
import com.zsoe.businesssharing.bean.HomeBean;
import com.zsoe.businesssharing.bean.JoinmerchantBean;
import com.zsoe.businesssharing.bean.ProductBean;
import com.zsoe.businesssharing.bean.SlideBean;
import com.zsoe.businesssharing.bean.StockpurchaseBean;
import com.zsoe.businesssharing.business.exhibitionhall.CommunicationMeetingForeshowActivity;
import com.zsoe.businesssharing.business.exhibitionhall.CompanyProfilesActivity;
import com.zsoe.businesssharing.business.exhibitionhall.EventDetailsActivity;
import com.zsoe.businesssharing.business.exhibitionhall.LatestNewsActivity;
import com.zsoe.businesssharing.business.exhibitionhall.ProductDetailActivity;
import com.zsoe.businesssharing.business.exhibitionhall.ProductListActivity;
import com.zsoe.businesssharing.business.home.FinancingLoansActivity;
import com.zsoe.businesssharing.business.home.HomePresenter;
import com.zsoe.businesssharing.business.home.JoinInvestmentActivity;
import com.zsoe.businesssharing.business.home.JoinInvestmentDetailActivity;
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
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.List;

import rx.functions.Action1;

/**
 * 首页
 */

@RequiresPresenter(HomePresenter.class)
public class HomeFragment extends BaseFragment<HomePresenter> implements View.OnClickListener {

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
                getPresenter().home();
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

        DialogManager.getInstance().showNetLoadingView(getContext());
        getPresenter().home();
    }

    public void setDate(HomeBean homeBean) {
        mPtrFrame.refreshComplete();
        List<HeadNews> headnews = homeBean.getHeadnews();

        home_view_switcher.setSwitcheNextViewListener(new UpDownViewSwitcher.SwitchNextViewListener() {
            @Override
            public void switchTONextView(View nextView, int index) {
                if (nextView == null) return;
                final HeadNews tag1 = headnews.get(index % headnews.size());
                ((TextView) nextView.findViewById(R.id.switch_title)).setText(tag1.getTitle());
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
        home_view_switcher.setContentLayout(R.layout.item_home_buttelin_switch_view);


        List<SlideBean> slide = homeBean.getSlide();
        banner_view.setDate(slide);


        List<ExtenactivityBean> extenactivity = homeBean.getExtenactivity();

        OnionRecycleAdapter hangyeAdapter = new OnionRecycleAdapter<ExtenactivityBean>(R.layout.item_tuiguang_list_layout, extenactivity) {
            @Override
            protected void convert(BaseViewHolder holder, final ExtenactivityBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());
                holder.setText(R.id.tv_name, item.getTitle());
                holder.setText(R.id.tv_zhiwei, item.getActivitydate());
                holder.setText(R.id.tv_p_c, item.getReadnum() + "人看过");

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


        List<StockpurchaseBean> stockpurchase = homeBean.getStockpurchase();
        OnionRecycleAdapter chanpinAdapter = new OnionRecycleAdapter<StockpurchaseBean>(R.layout.item_caigou_layout, stockpurchase) {
            @Override
            protected void convert(BaseViewHolder holder, final StockpurchaseBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());
                holder.setText(R.id.tv_name, item.getTitle());

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


        List<ProductBean> product = homeBean.getProduct();

        OnionRecycleAdapter jiazhiAdapter = new OnionRecycleAdapter<ProductBean>(R.layout.item_jiazhi_layout, product) {
            @Override
            protected void convert(BaseViewHolder holder, final ProductBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());
                holder.setText(R.id.tv_name, item.getTitle());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, ProductDetailActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1,item.getLinkid());
                        startActivity(intent);
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


        List<JoinmerchantBean> joinmerchant = homeBean.getJoinmerchant();

        OnionRecycleAdapter zhaoshangAdapter = new OnionRecycleAdapter<JoinmerchantBean>(R.layout.item_tuiguang_list_layout, joinmerchant) {
            @Override
            protected void convert(BaseViewHolder holder, final JoinmerchantBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());
                holder.setText(R.id.tv_name, item.getTitle());
                holder.setText(R.id.tv_zhiwei, item.getActivitydate());
                holder.setText(R.id.tv_p_c, item.getReadnum() + "人看过");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, JoinInvestmentDetailActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1, item.getLinkid());
                        startActivity(intent);
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
