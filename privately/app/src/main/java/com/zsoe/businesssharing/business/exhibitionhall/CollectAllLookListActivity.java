package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.HttpResponseFunc;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ItemInsdustry;
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
 * 查看全部
 */

@RequiresPresenter(CollectAllLookListPresenter.class)
public class CollectAllLookListActivity extends BaseActivity<CollectAllLookListPresenter> {

    private RecyclerView mRvProductList;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_list);
        type = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        initView();
        findViewById(R.id.rl_search).setVisibility(View.GONE);
        if (type == 1) {
            initTitleText("收藏企业");
        } else if (type == 2) {
            initTitleText("收藏产品");
        } else if (type == 3) {
            initTitleText("收藏交流");
        } else if (type == 4) {
            initTitleText("收藏招商加盟");
        } else if (type == 5) {
            initTitleText("收藏银行信贷");
        }

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();
                getPresenter().collect_infolist(DApplication.getInstance().getLoginUser().getId() + "", type + "");
            }
        });

//        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();
    }

    private List<ItemInsdustry> noticeBeanList = new ArrayList<>();
    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;

    private void initView() {
        mRvProductList = (RecyclerView) findViewById(R.id.rv_product_list);

        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<ItemInsdustry>(R.layout.item_companies_list_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemInsdustry item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());

                holder.setText(R.id.tv_name, item.getName());
                holder.setText(R.id.tv_zhiwei, item.getDes());

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
                        collect(item.getType(), item.getId() + "", acttype);
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
                getPresenter().collect_infolist(DApplication.getInstance().getLoginUser().getId() + "", type + "");
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault.getFooterView();
        noticeBeanOnionRecycleAdapter.setLoadMoreContainer(getPresenter().loadMoreDefault);


        mRvProductList.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvProductList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvProductList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvProductList.setAdapter(noticeBeanOnionRecycleAdapter);
    }


    /**
     * 关闭刷新/更新数据
     */
    public void updateList() {
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter.notifyDataSetChanged();
    }

    /**
     *
     */
    private int currentPosition;

    public void collect(String type, String valueid, String acttype) {

        DialogManager.getInstance().showNetLoadingView(mContext);

        HashMap<String, String> params = new HashMap<>();
        params.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        params.put("type", type);
        params.put("acttype", acttype);
        params.put("valueid", valueid);


        FormBody formBody = getPresenter().signForm(params);
        DApplication.getInstance().getServerAPI().collect(formBody).map(new HttpResponseFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber() {

            @Override
            public void onNext(Object o) {
                DialogManager.getInstance().dismissNetLoadingView();

                List<ItemInsdustry> noticeBeanList = noticeBeanOnionRecycleAdapter.getData();
                ItemInsdustry itemCompany = noticeBeanList.get(currentPosition);
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

}
