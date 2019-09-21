package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.HttpResponseFunc;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ItemCompany;
import com.zsoe.businesssharing.commonview.ClearEditText;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreContainer;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreDefaultFooterRecyclerView;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreHandler;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.OpenLoadMoreDefault;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;
import com.zsoe.businesssharing.utils.android.schedulers.AndroidSchedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * 企业列表
 */

@RequiresPresenter(CompanyListPresenter.class)
public class CompaniesListActivity extends BaseActivity<CompanyListPresenter> implements View.OnClickListener {

    private RecyclerView mRvProductList;
    /**
     * 搜索
     */
    private TextView mTvSousuo;
    /**
     * 请输入要搜索的内容
     */
    private ClearEditText mSearchInput;


    private String industryId;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_list);

        industryId = getIntent().getStringExtra(Config.INTENT_PARAMS1);
        userId = getIntent().getStringExtra(Config.INTENT_PARAMS2);

        initView();
        initTitleText("企业列表");

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                if (TextUtils.isEmpty(keyword)) {
                    getPresenter().loadMoreDefault.refresh();
                } else {
                    getPresenter().loadMoreDefault2.refresh();
                }
                getPresenter().company_list(keyword,industryId);
            }
        });

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();
    }

    private List<ItemCompany> noticeBeanList = new ArrayList<>();
    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;


    OnionRecycleAdapter noticeBeanOnionRecycleAdapter2;
    private List<ItemCompany> noticeBeanList2 = new ArrayList<>();

    private void initView() {
        mRvProductList = (RecyclerView) findViewById(R.id.rv_product_list);

        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<ItemCompany>(R.layout.item_companies_list_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemCompany item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());

                holder.setText(R.id.tv_name, item.getName());
                holder.setText(R.id.tv_zhiwei, item.getMaincate());

                ImageView iv_shoucang = holder.getView(R.id.iv_shoucang);

                if (item.getIs_collect() == 1) {
                    iv_shoucang.setImageResource(R.mipmap.shoucang);
                } else {
                    iv_shoucang.setImageResource(R.mipmap.shoucang_pre);
                }

                iv_shoucang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String acttype;
                        if (item.getIs_collect() == 1) {
                            acttype = "2";
                        } else {
                            acttype = "1";
                        }

                        currentPosition = holder.getAdapterPosition();
                        collect(item.getId() + "", acttype);
                    }
                });


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, CompanyProfilesActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1, item.getId());
                        startActivity(intent);
                    }
                });

            }
        };


        getPresenter().loadMoreDefault = new OpenLoadMoreDefault(mContext, noticeBeanList);
        getPresenter().loadMoreDefault.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().company_list(keyword,industryId);
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault.getFooterView();
        noticeBeanOnionRecycleAdapter.setLoadMoreContainer(getPresenter().loadMoreDefault);


        noticeBeanOnionRecycleAdapter2 = new OnionRecycleAdapter<ItemCompany>(R.layout.item_companies_list_layout, noticeBeanList2) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemCompany item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());

                holder.setText(R.id.tv_name, item.getName());
                holder.setText(R.id.tv_zhiwei, item.getMaincate());

                ImageView iv_shoucang = holder.getView(R.id.iv_shoucang);

                if (item.getIs_collect() == 1) {
                    iv_shoucang.setImageResource(R.mipmap.shoucang);
                } else {
                    iv_shoucang.setImageResource(R.mipmap.shoucang_pre);
                }

                iv_shoucang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String acttype;
                        if (item.getIs_collect() == 1) {
                            acttype = "2";
                        } else {
                            acttype = "1";
                        }

                        currentPosition = holder.getAdapterPosition();
                        collect(item.getId() + "", acttype);
                    }
                });


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, CompanyProfilesActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1, item.getId());
                        startActivity(intent);
                    }
                });

            }
        };


        getPresenter().loadMoreDefault2 = new OpenLoadMoreDefault(mContext, noticeBeanList2);
        getPresenter().loadMoreDefault2.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getPresenter().company_list(keyword,industryId);
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView2 = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault2.getFooterView();
        noticeBeanOnionRecycleAdapter2.setLoadMoreContainer(getPresenter().loadMoreDefault2);


        mRvProductList.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvProductList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvProductList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvProductList.setAdapter(noticeBeanOnionRecycleAdapter);
        mTvSousuo = (TextView) findViewById(R.id.tv_sousuo);
        mTvSousuo.setOnClickListener(this);
        mSearchInput = (ClearEditText) findViewById(R.id.search_input);
        mSearchInput.setClearClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = "";
                mRvProductList.setAdapter(noticeBeanOnionRecycleAdapter);
            }
        });
    }


    private String keyword;


    /**
     * 关闭刷新/更新数据
     */
    public void updateList() {
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter.notifyDataSetChanged();
    }


    boolean isFirstSearch = false;

    /**
     * 关闭刷新/更新数据
     */
    public void updateList2() {
        if (isFirstSearch) {
            mRvProductList.setAdapter(noticeBeanOnionRecycleAdapter2);
        }
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter2.notifyDataSetChanged();
        isFirstSearch = false;
    }


    /**
     *
     */
    private int currentPosition;

    public void collect(String valueid, String acttype) {

        DialogManager.getInstance().showNetLoadingView(mContext);

        HashMap<String, String> params = new HashMap<>();
        params.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        params.put("type", "1");
        params.put("acttype", acttype);
        params.put("valueid", valueid);


        FormBody formBody = getPresenter().signForm(params);
        DApplication.getInstance().getServerAPI().collect(formBody).map(new HttpResponseFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber() {

            @Override
            public void onNext(Object o) {
                DialogManager.getInstance().dismissNetLoadingView();

                List<ItemCompany> noticeBeanList = noticeBeanOnionRecycleAdapter.getData();
                ItemCompany itemCompany = noticeBeanList.get(currentPosition);
                //1收藏 2 取消收藏
                if (acttype.equals("1")) {
                    itemCompany.setIs_collect(1);
                } else {
                    itemCompany.setIs_collect(0);
                }
                noticeBeanOnionRecycleAdapter.notifyItemChanged(currentPosition, 0);//

            }

            @Override
            public void onCompleted() {
                DialogManager.getInstance().dismissNetLoadingView();
            }

            @Override
            public void onError(Throwable e) {
                DialogManager.getInstance().dismissNetLoadingView();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_sousuo:
                keyword = mSearchInput.getText().toString();
                if (TextUtils.isEmpty(keyword)) {
                    ToastUtils.showShort("请输入关键字");
                    return;
                }

                mPtrFrame.autoRefresh();
                isFirstSearch = true;
                break;
        }
    }
}
