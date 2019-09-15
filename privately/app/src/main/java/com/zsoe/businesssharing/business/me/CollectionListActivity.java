package com.zsoe.businesssharing.business.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.BrowserActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.HttpResponseFunc;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ItemCollectBean;
import com.zsoe.businesssharing.bean.ItemInsdustry;
import com.zsoe.businesssharing.business.exhibitionhall.CollectAllLookListActivity;
import com.zsoe.businesssharing.business.exhibitionhall.CompanyProfilesActivity;
import com.zsoe.businesssharing.business.exhibitionhall.EventDetailsActivity;
import com.zsoe.businesssharing.business.exhibitionhall.ProductDetailActivity;
import com.zsoe.businesssharing.business.home.JoinInvestmentDetailActivity;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.EmptyUtil;
import com.zsoe.businesssharing.utils.FrecoFactory;
import com.zsoe.businesssharing.utils.android.schedulers.AndroidSchedulers;

import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@RequiresPresenter(CollectionListPresenter.class)
public class CollectionListActivity extends BaseActivity<CollectionListPresenter> implements View.OnClickListener {

    /**
     * 查看全部
     */
    private TextView mTvQiyeMore;
    private RecyclerView mRvQiye;
    /**
     * 查看全部
     */
    private TextView mTvChanpinMore;
    private RecyclerView mRvChanpin;
    /**
     * 查看全部
     */
    private TextView mTvZixunMore;
    private RecyclerView mRvZixun;
    /**
     * 查看全部
     */
    private TextView mTvZhaoshangMore;
    private RecyclerView mRvZhaoshang;
    private LinearLayoutCompat mLlQiye;
    private LinearLayoutCompat mLlChanpin;
    private LinearLayoutCompat mLlJiaoliu;
    private LinearLayoutCompat mLlZhaoshang;
    /**
     * 查看全部
     */
    private TextView mTvYinhangMore;
    private RecyclerView mRvYinhang;
    private LinearLayoutCompat mLlYinhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);
        initView();
        initTitleText("收藏列表");

        DialogManager.getInstance().showNetLoadingView(mContext);
        getPresenter().collect_list(DApplication.getInstance().getLoginUser().getId() + "");
    }

    private void initView() {
        mTvQiyeMore = (TextView) findViewById(R.id.tv_qiye_more);
        mTvQiyeMore.setOnClickListener(this);
        mRvQiye = (RecyclerView) findViewById(R.id.rv_qiye);
        mTvChanpinMore = (TextView) findViewById(R.id.tv_chanpin_more);
        mTvChanpinMore.setOnClickListener(this);
        mRvChanpin = (RecyclerView) findViewById(R.id.rv_chanpin);
        mTvZixunMore = (TextView) findViewById(R.id.tv_zixun_more);
        mTvZixunMore.setOnClickListener(this);
        mRvZixun = (RecyclerView) findViewById(R.id.rv_zixun);
        mTvZhaoshangMore = (TextView) findViewById(R.id.tv_zhaoshang_more);
        mTvZhaoshangMore.setOnClickListener(this);
        mRvZhaoshang = (RecyclerView) findViewById(R.id.rv_zhaoshang);
        mLlQiye = (LinearLayoutCompat) findViewById(R.id.ll_qiye);
        mLlChanpin = (LinearLayoutCompat) findViewById(R.id.ll_chanpin);
        mLlJiaoliu = (LinearLayoutCompat) findViewById(R.id.ll_jiaoliu);
        mLlZhaoshang = (LinearLayoutCompat) findViewById(R.id.ll_zhaoshang);
        mTvYinhangMore = (TextView) findViewById(R.id.tv_yinhang_more);
        mTvYinhangMore.setOnClickListener(this);
        mRvYinhang = (RecyclerView) findViewById(R.id.rv_yinhang);
        mLlYinhang = (LinearLayoutCompat) findViewById(R.id.ll_yinhang);
    }


    public void setDate(ItemCollectBean itemCollectBean) {

        List<ItemInsdustry> companyist = itemCollectBean.getCompanyist();
        if (!EmptyUtil.isEmpty(companyist)) {
            mLlQiye.setVisibility(View.VISIBLE);
            OnionRecycleAdapter hangyeAdapter = new OnionRecycleAdapter<ItemInsdustry>(R.layout.item_product_list_layout, companyist) {
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
                            collect("1",item.getId() + "", acttype);
                        }
                    });

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, CompanyProfilesActivity.class);
                            intent.putExtra(Config.INTENT_PARAMS1,item.getId());
                            startActivity(intent);
                        }
                    });

                }
            };

            mRvQiye.setFocusableInTouchMode(false);
            mRvQiye.setNestedScrollingEnabled(false);
            mRvQiye.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
            mRvQiye.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
            mRvQiye.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
            mRvQiye.setAdapter(hangyeAdapter);
        } else {
            mLlQiye.setVisibility(View.GONE);
        }


        List<ItemInsdustry> productlist = itemCollectBean.getProductlist();
        if (!EmptyUtil.isEmpty(productlist)) {
            mLlChanpin.setVisibility(View.VISIBLE);
            OnionRecycleAdapter chanpinAdapter = new OnionRecycleAdapter<ItemInsdustry>(R.layout.item_product_list_layout, productlist) {
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
                            collect("2",item.getId() + "", acttype);
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

            mRvChanpin.setFocusableInTouchMode(false);
            mRvChanpin.setNestedScrollingEnabled(false);
            mRvChanpin.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
            mRvChanpin.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
            mRvChanpin.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
            mRvChanpin.setAdapter(chanpinAdapter);
        } else {
            mLlChanpin.setVisibility(View.GONE);
        }

        List<ItemInsdustry> communicationlist = itemCollectBean.getCommunicationlist();
        if (!EmptyUtil.isEmpty(communicationlist)) {
            mLlJiaoliu.setVisibility(View.VISIBLE);
            OnionRecycleAdapter zixunAdapter = new OnionRecycleAdapter<ItemInsdustry>(R.layout.item_product_list_layout, communicationlist) {
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
                            collect("3",item.getId() + "", acttype);
                        }
                    });


                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, EventDetailsActivity.class);
                            intent.putExtra(Config.INTENT_PARAMS1, item.getId());
                            startActivity(intent);
                        }
                    });

                }
            };

            mRvZixun.setFocusableInTouchMode(false);
            mRvZixun.setNestedScrollingEnabled(false);
            mRvZixun.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
            mRvZixun.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
            mRvZixun.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
            mRvZixun.setAdapter(zixunAdapter);

        } else {
            mLlJiaoliu.setVisibility(View.GONE);
        }


        List<ItemInsdustry> joinmerchantlist = itemCollectBean.getJoinmerchantlist();
        if (!EmptyUtil.isEmpty(joinmerchantlist)) {
            mLlZhaoshang.setVisibility(View.VISIBLE);
            OnionRecycleAdapter zhaoshangAdapter = new OnionRecycleAdapter<ItemInsdustry>(R.layout.item_product_list_layout, joinmerchantlist) {
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
                            collect("4",item.getId() + "", acttype);
                        }
                    });

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, JoinInvestmentDetailActivity.class);
                            intent.putExtra(Config.INTENT_PARAMS1, item.getId());
                            startActivity(intent);
                        }
                    });

                }
            };

            mRvZhaoshang.setFocusableInTouchMode(false);
            mRvZhaoshang.setNestedScrollingEnabled(false);
            mRvZhaoshang.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
            mRvZhaoshang.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
            mRvZhaoshang.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
            mRvZhaoshang.setAdapter(zhaoshangAdapter);
        } else {
            mLlZhaoshang.setVisibility(View.GONE);
        }

        List<ItemInsdustry> loanfinancelist = itemCollectBean.getLoanfinancelist();
        if (!EmptyUtil.isEmpty(loanfinancelist)) {
            mLlYinhang.setVisibility(View.VISIBLE);
            OnionRecycleAdapter zhaoshangAdapter = new OnionRecycleAdapter<ItemInsdustry>(R.layout.item_product_list_layout, loanfinancelist) {
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
                            collect("5",item.getId() + "", acttype);
                        }
                    });

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, BrowserActivity.class);
                            intent.putExtra(Config.INTENT_PARAMS1,item.getLinkurl());
                            startActivity(intent);
                        }
                    });

                }
            };

            mRvYinhang.setFocusableInTouchMode(false);
            mRvYinhang.setNestedScrollingEnabled(false);
            mRvYinhang.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
            mRvYinhang.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
            mRvYinhang.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
            mRvYinhang.setAdapter(zhaoshangAdapter);
        } else {
            mLlYinhang.setVisibility(View.GONE);
        }
    }


    private int currentPosition;
    public void collect(String type,String valueid, String acttype) {

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
                getPresenter().collect_list(DApplication.getInstance().getLoginUser().getId() + "");

//                List<ItemCompany> noticeBeanList = noticeBeanOnionRecycleAdapter.getData();
//                ItemCompany itemCompany = noticeBeanList.get(currentPosition);
//                //1收藏 2 取消收藏
//                if (acttype.equals("1")) {
//                    itemCompany.setIs_collect(1);
//                } else {
//                    itemCompany.setIs_collect(0);
//                }
//                noticeBeanOnionRecycleAdapter.notifyItemChanged(currentPosition, 0);//

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
        int type = 0;
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_qiye_more:
                type = 1;
                break;
            case R.id.tv_chanpin_more:
                type = 2;
                break;
            case R.id.tv_zixun_more:
                type = 3;
                break;
            case R.id.tv_zhaoshang_more:
                type = 4;
                break;
            case R.id.tv_yinhang_more:
                type = 5;
                break;
        }

        Intent intent = new Intent(mContext, CollectAllLookListActivity.class);
        intent.putExtra(Config.INTENT_PARAMS1, type);
        startActivity(intent);
    }
}
