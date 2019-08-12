package com.zsoe.businesssharing.business.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.bean.AreaBean;
import com.zsoe.businesssharing.bean.BannerItemBean;
import com.zsoe.businesssharing.bean.CityBean;
import com.zsoe.businesssharing.bean.FilterBean;
import com.zsoe.businesssharing.bean.InstitutionPriceBean;
import com.zsoe.businesssharing.bean.ProvinceBean;
import com.zsoe.businesssharing.commonview.dropfilter.DropDownMenu;
import com.zsoe.businesssharing.commonview.dropfilter.interfaces.OnFilterDoneListener;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class ProcurementAndInventoryActivity extends BaseActivity implements OnFilterDoneListener {


    //控件
    DropDownMenu dropDownMenu;
    //数据
    private FilterBean filterBean;//总数据源
    private List<ProvinceBean> provinceBeans;//省
    private List<CityBean> cityBeans;//市
    private List<AreaBean> areaBeans;//区
    private List<InstitutionPriceBean> priceBeans;//价格
    private String[] titleList;//标题
    private List<String> sortListData;//排序
    private List<FilterBean.InstitutionObject> objectbeans;//收住对象
    private List<FilterBean.PlaceProperty> propertyBeans;//机构性质
    private List<FilterBean.Bed> beds;//床位
    private List<FilterBean.InstitutionPlace> places;//类型
    private List<FilterBean.InstitutionFeature> features;//特色

    //筛选器适配
    private DropMenuAdapter dropMenuAdapter;
    private RecyclerView rv_caigou_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_and_inventory);
        initTitleText("采购&库存");

        dropDownMenu = findViewById(R.id.dropDownMenu);
        rv_caigou_list = findViewById(R.id.rv_caigou_list);

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新

            }
        });

        initView();
        initData();
        initFilterDropDownView();
        initListDate();
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
        dropMenuAdapter.setSortListData(sortListData);
        dropMenuAdapter.setFilterBean(filterBean);
        dropDownMenu.setMenuAdapter(dropMenuAdapter);

        //01 省市区 回调参数
        dropMenuAdapter.setOnPlaceCallbackListener(new DropMenuAdapter.OnPlaceCallbackListener() {
            @Override
            public void onPlaceCallbackListener(int provinceId, int cityId, int areaId) {
                ToastUtils.showShort("省的id=" + provinceId + "--" + cityId + "--" + areaId);

            }
        });
        //02 多条件 回调参数
        dropMenuAdapter.setOnMultiFilterCallbackListener(new DropMenuAdapter.OnMultiFilterCallbackListener() {
            @Override
            public void onMultiFilterCallbackListener(int objId, int propertyId, int bedId, int typeId, String serviceId) {

                ToastUtils.showShort("多选的i依次是=" + objId + "--" + propertyId + "--" + bedId + "--" + typeId + "--" + serviceId);
            }
        });

        //03 dropMenuAdapter 价格范围回调
        dropMenuAdapter.setOnPriceCallbackListener(new DropMenuAdapter.OnPriceCallbackListener() {
            @Override
            public void onPriceCallbackListener(InstitutionPriceBean item) {
                ToastUtils.showShort("价格=" + item.getName() + "--id=" + item.getId());
            }
        });

        //04 dropMenuAdapter 排序回调 0 1 2
        dropMenuAdapter.setOnSortCallbackListener(new DropMenuAdapter.OnSortCallbackListener() {
            @Override
            public void onSortCallbackListener(int item) {
                ToastUtils.showShort("排序id=" + item);
            }
        });

        //        //异步加载列表数据
        //        loadListData();
    }

    /**
     * 初始化
     */
    private void initView() {
        filterBean = new FilterBean();
        provinceBeans = new ArrayList<>();
        cityBeans = new ArrayList<>();
        areaBeans = new ArrayList<>();
        priceBeans = new ArrayList<>();

        objectbeans = new ArrayList<>();
        propertyBeans = new ArrayList<>();
        beds = new ArrayList<>();
        places = new ArrayList<>();
        features = new ArrayList<>();
    }

    /**
     * 制作假数据
     * 说明：FilterBean是公司项目接口返回的数据bean，现在用假数据实现，用注释+gif给大家解释
     */
    private void initData() {

        //筛选标题数据（固定数据）
        titleList = new String[]{"区域", "筛选", "价格", "排序"};

        //排序数据
        sortListData = new ArrayList<>();
        sortListData.add("综合排序");
        sortListData.add("价格 从高到底");
        sortListData.add("价格 从低到高");

        //区数据
        for (int i = 0; i < 33; i++) {
            AreaBean bean = new AreaBean();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");
                bean.setPid("没用String");

                areaBeans.add(bean);
            } else {
                bean.setId(i);
                bean.setName("区" + i);
                bean.setPid("没用String");

                areaBeans.add(bean);
            }
        }

        //市数据
        for (int i = 0; i < 26; i++) {
            CityBean bean = new CityBean();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");
                bean.setPid("没用String");

                cityBeans.add(bean);

                //添加区数据
                bean.setChild((ArrayList<AreaBean>) areaBeans);
            } else {
                bean.setId(i);
                bean.setName("市" + i);
                bean.setPid("没用String");

                cityBeans.add(bean);
                //添加区数据
                bean.setChild((ArrayList<AreaBean>) areaBeans);
            }
        }

        //省数据
        for (int i = 0; i < 36; i++) {
            ProvinceBean bean = new ProvinceBean();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");
                bean.setPid("没用String");

                provinceBeans.add(bean);

                //省份添加市数据
                bean.setChild((ArrayList<CityBean>) cityBeans);
            } else {
                bean.setId(i);
                bean.setName("省" + i);
                bean.setPid("没用String");

                provinceBeans.add(bean);
                //省份添加市数据
                bean.setChild((ArrayList<CityBean>) cityBeans);
            }
        }

        //价格
        for (int i = 0; i < 10; i++) {
            InstitutionPriceBean bean = new InstitutionPriceBean();
            if (i == 0) {
                bean.setId(i);
                bean.setName("2000元以下");
                priceBeans.add(bean);
            } else if (i == 9) {
                bean.setId(i);
                bean.setName("xxx元以上");
                priceBeans.add(bean);
            } else {
                bean.setId(i);
                bean.setName((2000 + i * 100) + "元");
                priceBeans.add(bean);
            }

        }


        //多选数据（5条）
        //01收住对象
        for (int i = 0; i < 7; i++) {

            FilterBean.InstitutionObject bean = new FilterBean.InstitutionObject();
            if (i == 0) {
                bean.setId(i);
                bean.setType("不限");

                objectbeans.add(bean);
            } else {
                bean.setId(i);
                bean.setType("对象" + i);

                objectbeans.add(bean);
            }
        }
        //02 机构性质
        for (int i = 0; i < 3; i++) {

            FilterBean.PlaceProperty bean = new FilterBean.PlaceProperty();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");

                propertyBeans.add(bean);
            } else if (i == 1) {
                bean.setId(i);
                bean.setName("公办");

                propertyBeans.add(bean);
            } else if (i == 2) {
                bean.setId(i);
                bean.setName("民办");

                propertyBeans.add(bean);
            }
        }

        //03床位

        for (int i = 0; i < 4; i++) {

            FilterBean.Bed bean = new FilterBean.Bed();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");

                beds.add(bean);
            } else if (i == 1) {
                bean.setId(i);
                bean.setName("100以下");

                beds.add(bean);
            } else if (i == 2) {
                bean.setId(i);
                bean.setName("100-500张");

                beds.add(bean);
            } else if (i == 3) {
                bean.setId(i);
                bean.setName("500张以上");

                beds.add(bean);
            }
        }
        //04机构类型

        for (int i = 0; i < 7; i++) {

            FilterBean.InstitutionPlace bean = new FilterBean.InstitutionPlace();
            if (i == 0) {
                bean.setId(i);
                bean.setType("不限");

                places.add(bean);
            } else if (i == 1) {
                bean.setId(i);
                bean.setType("社区养老院");

                places.add(bean);
            } else if (i == 2) {
                bean.setId(i);
                bean.setType("敬老院");

                places.add(bean);
            } else if (i == 3) {
                bean.setId(i);
                bean.setType("疗养院");

                places.add(bean);
            } else {
                bean.setId(i);
                bean.setType("其他" + i);

                places.add(bean);
            }
        }
        //05特色

        for (int i = 0; i < 7; i++) {

            FilterBean.InstitutionFeature bean = new FilterBean.InstitutionFeature();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");

                features.add(bean);
            } else if (i == 1) {
                bean.setId(i);
                bean.setName("医养结合");

                features.add(bean);
            } else if (i == 2) {
                bean.setId(i);
                bean.setName("免费试住");

                features.add(bean);
            } else if (i == 3) {
                bean.setId(i);
                bean.setName("合作医院");

                features.add(bean);
            } else {
                bean.setId(i);
                bean.setName("其他" + i);

                features.add(bean);
            }
        }

        //假数据整合，当成接口返回bean
        filterBean.setType((ArrayList<FilterBean.InstitutionPlace>) places);
        filterBean.setFeature((ArrayList<FilterBean.InstitutionFeature>) features);
        filterBean.setBed((ArrayList<FilterBean.Bed>) beds);
        filterBean.setObject((ArrayList<FilterBean.InstitutionObject>) objectbeans);
        filterBean.setProperty((ArrayList<FilterBean.PlaceProperty>) propertyBeans);
        filterBean.setPrice((ArrayList<InstitutionPriceBean>) priceBeans);

        filterBean.setProvince((ArrayList<ProvinceBean>) provinceBeans);
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

    private void initListDate() {

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
        bannerItemBeans.addAll(bannerItemBeans);


        OnionRecycleAdapter noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_caigou_list_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getImg());

                holder.setText(R.id.tv_name, "为何马云仅有7%的股份就控制了阿里,孙正义是最大股东却说了不算");
                holder.setText(R.id.tv_zhiwei, "发布时间：2019-07-07");
                holder.setText(R.id.tv_p_c, "1268人看过");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(mContext, ProcurementAndInventoryDetailActivity.class));

                    }
                });

            }
        };


        rv_caigou_list.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        rv_caigou_list.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        rv_caigou_list.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        rv_caigou_list.setAdapter(noticeBeanOnionRecycleAdapter);
    }
}
