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
import com.zsoe.businesssharing.bean.ChanPinBeanItem;
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

@RequiresPresenter(ProductListPresenter.class)
public class ProductListActivity extends BaseActivity<ProductListPresenter> implements View.OnClickListener {

    private RecyclerView mRvProductList;
    /**
     * 请输入要搜索的内容
     */
    private ClearEditText mSearchInput;
    /**
     * 搜索
     */
    private TextView mTvSousuo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        initView();
        initTitleText("产品列表");

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().loadMoreDefault.refresh();
                getPresenter().product_list("");
            }
        });

        DialogManager.getInstance().showNetLoadingView(mContext);
        mPtrFrame.autoRefresh();
    }


    OnionRecycleAdapter noticeBeanOnionRecycleAdapter;
    private List<ChanPinBeanItem> noticeBeanList = new ArrayList<>();


    private void initView() {
        mRvProductList = (RecyclerView) findViewById(R.id.rv_product_list);


        noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<ChanPinBeanItem>(R.layout.item_product_list_layout, noticeBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final ChanPinBeanItem item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.product_image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());

                holder.setText(R.id.tv_name, item.getProductname());
                holder.setText(R.id.tv_zhiwei, item.getContent());


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
                        Intent intent = new Intent(mContext, ProductDetailActivity.class);
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
                getPresenter().product_list("");
            }
        });


        LoadMoreDefaultFooterRecyclerView defaultFooterRecyclerView = (LoadMoreDefaultFooterRecyclerView) getPresenter().loadMoreDefault.getFooterView();
        noticeBeanOnionRecycleAdapter.setLoadMoreContainer(getPresenter().loadMoreDefault);

        mRvProductList.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvProductList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvProductList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvProductList.setAdapter(noticeBeanOnionRecycleAdapter);
        mSearchInput = (ClearEditText) findViewById(R.id.search_input);
        mTvSousuo = (TextView) findViewById(R.id.tv_sousuo);
        mTvSousuo.setOnClickListener(this);
    }


    /**
     * 关闭刷新/更新数据
     */
    public void updateList() {
        mPtrFrame.refreshComplete();
        noticeBeanOnionRecycleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_sousuo:
                String s = mSearchInput.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    ToastUtils.showShort("请输入关键字");
                    return;
                }
                break;
        }
    }


    /**
     *
     */
    private int currentPosition;

    public void collect(String valueid, String acttype) {

        DialogManager.getInstance().showNetLoadingView(mContext);

        HashMap<String, String> params = new HashMap<>();
        params.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        params.put("type", "2");
        params.put("acttype", acttype);
        params.put("valueid", valueid);


        FormBody formBody = getPresenter().signForm(params);
        DApplication.getInstance().getServerAPI().collect(formBody).map(new HttpResponseFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber() {

            @Override
            public void onNext(Object o) {
                DialogManager.getInstance().dismissNetLoadingView();

                List<ChanPinBeanItem> noticeBeanList = noticeBeanOnionRecycleAdapter.getData();
                ChanPinBeanItem itemCompany = noticeBeanList.get(currentPosition);
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
