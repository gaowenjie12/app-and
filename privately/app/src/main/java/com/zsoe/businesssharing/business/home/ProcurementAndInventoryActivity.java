package com.zsoe.businesssharing.business.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.FancyUtils;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.FilterBean;
import com.zsoe.businesssharing.bean.GongYouBean;
import com.zsoe.businesssharing.bean.RootHangYe;
import com.zsoe.businesssharing.bean.StockBean;
import com.zsoe.businesssharing.commonview.dropfilter.DropDownMenu;
import com.zsoe.businesssharing.commonview.dropfilter.interfaces.OnFilterDoneListener;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreContainer;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreDefaultFooterRecyclerView;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreHandler;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.EmptyUtil;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

@RequiresPresenter(ProcurementAndInventoryPresenter.class)
public class ProcurementAndInventoryActivity extends BaseActivity<ProcurementAndInventoryPresenter> implements OnFilterDoneListener {


    //控件
    DropDownMenu dropDownMenu;
    //数据
    private FilterBean filterBean;//总数据源
    private String[] titleList;//标题
    //筛选器适配
    private DropMenuAdapter dropMenuAdapter;
    private RecyclerView rv_caigou_list;

    private String isCaiGou = "2", isLiulan = "2";
    private String icate="", pcate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_and_inventory);
        initTitleText("采购&库存");

        dropDownMenu = findViewById(R.id.dropDownMenu);
        rv_caigou_list = findViewById(R.id.rv_caigou_list);



        initView();
        initData();
        initFilterDropDownView();
        initListDate();




        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();
                getPresenter().stock_purchase_list(icate, pcate, isCaiGou, isLiulan);
            }
        });
        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();

    }


    /**
     * 显示
     */
    /**
     * 筛选框 初始化+获取列表数据+筛选条件监听
     */
    private void initFilterDropDownView() {
        //绑定数据源
        dropMenuAdapter = new DropMenuAdapter(mContext, titleList, this);
//        dropMenuAdapter.setSortListData(sortListData);
        dropMenuAdapter.setFilterBean(filterBean);
        dropDownMenu.setMenuAdapter(dropMenuAdapter);

        //行业
        dropMenuAdapter.setOnPlaceCallbackListener(new DropMenuAdapter.OnPlaceCallbackListener() {
            @Override
            public void onPlaceCallbackListener(int provinceId, int cityId) {
                icate = cityId + "";
                mPtrFrame.autoRefresh();
            }
        });
        //商品种类
        dropMenuAdapter.setOnMultiFilterCallbackListener(new DropMenuAdapter.OnMultiFilterCallbackListener() {
            @Override
            public void onMultiFilterCallbackListener(int objId, int propertyId) {
                pcate = propertyId + "";
                mPtrFrame.autoRefresh();
            }
        });

        //采购量
        dropMenuAdapter.setOnPriceCallbackListener(new DropMenuAdapter.OnPriceCallbackListener() {
            @Override
            public void onPriceCallbackListener() {
                if (isCaiGou.equals("2")) {
                    isCaiGou = "1";
                } else if (isCaiGou.equals("1")) {
                    isCaiGou = "2";
                }

                mPtrFrame.autoRefresh();
            }
        });

        //浏览量
        dropMenuAdapter.setLiuLanCallbackListener(new DropMenuAdapter.OnLiuLanCallbackListener() {
            @Override
            public void onLiuLanCallbackListener() {
                if (isLiulan.equals("2")) {
                    isLiulan = "1";
                } else if (isLiulan.equals("1")) {
                    isLiulan = "2";
                }

                mPtrFrame.autoRefresh();
            }
        });
    }

    /**
     * 初始化
     */
    private void initView() {

        GongYouBean rootHangYe = FancyUtils.getRootHangYe();
        if (EmptyUtil.isEmpty(rootHangYe)) {
            return;
        }
        List<RootHangYe> industrycatelist = rootHangYe.getIndustrycatelist();
        if (EmptyUtil.isEmpty(industrycatelist)) {
            return;
        }

        List<RootHangYe> productcatelist = rootHangYe.getProductcatelist();
        if (EmptyUtil.isEmpty(productcatelist)) {
            return;
        }


        filterBean = new FilterBean();
        filterBean.setIndustrycatelist(industrycatelist);
        filterBean.setProductcatelist(productcatelist);
    }

    /**
     * 制作假数据
     * 说明：FilterBean是公司项目接口返回的数据bean，现在用假数据实现，用注释+gif给大家解释
     */
    private void initData() {
        //筛选标题数据（固定数据）
        titleList = new String[]{"行业分类", "商品品类", "采购量", "浏览量"};
    }


    /**
     * 筛选器title的变化
     * <p>
     * 点击到选中的item，自动收回
     *
     * @param position
     * @param positionTitle
     * @param urlValue
     */
    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {
        //数据显示到筛选标题中
        dropDownMenu.setPositionIndicatorText(position, positionTitle);
        dropDownMenu.close();
    }


    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<StockBean> noticeBeanList = new ArrayList<>();

    private void initListDate() {

        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<StockBean>(R.layout.item_caigou_list_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final StockBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());

                holder.setText(R.id.tv_name, item.getTitle());
                holder.setText(R.id.tv_zhiwei, item.getCompanyname());
                holder.setText(R.id.tv_xuq, item.getTypetext());
                holder.setText(R.id.tv_p_c, item.getReadnum() + "人看过");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, ProcurementAndInventoryDetailActivity.class));

                    }
                });

            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().stock_purchase_list(icate, pcate, isCaiGou, isLiulan);
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault.getFooterView();
        noticeBeanOnionRecycleAdapter.setLoadMoreContainer(getPresenter().loadMoreDefault);


        rv_caigou_list.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        rv_caigou_list.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        rv_caigou_list.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        rv_caigou_list.setAdapter(noticeBeanOnionRecycleAdapter);
    }


    /**
     * 关闭刷新/更新数据
     */
    public void updateList() {
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter.notifyDataSetChanged();
    }
}
