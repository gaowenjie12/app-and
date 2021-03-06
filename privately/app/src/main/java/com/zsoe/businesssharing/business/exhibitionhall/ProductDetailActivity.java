package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.baseadapter.BaseQuickAdapter;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.HttpResponseFunc;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ProductDetail;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;
import com.zsoe.businesssharing.utils.GlideUtils;
import com.zsoe.businesssharing.utils.PhotoLoader;
import com.zsoe.businesssharing.utils.ScreenUtils;
import com.zsoe.businesssharing.utils.android.schedulers.AndroidSchedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import indi.liyi.viewer.ImageDrawee;
import indi.liyi.viewer.ImageViewer;
import indi.liyi.viewer.Utils;
import indi.liyi.viewer.ViewData;
import indi.liyi.viewer.ViewerStatus;
import indi.liyi.viewer.listener.OnBrowseStatusListener;
import indi.liyi.viewer.listener.OnItemChangedListener;
import okhttp3.FormBody;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@RequiresPresenter(ProductInfoPresenter.class)
public class ProductDetailActivity extends BaseActivity<ProductInfoPresenter> {

    private JzvdStd mJzVideo;
    private FrameLayout mFlVideo;
    private TextView mTvTitle;
    private TextView mTvCompanyName;
    private ExpandableTextView mTvJieshao;
    private RelativeLayout mRlChakan;
    private RecyclerView mRvPic;

    private List<ViewData> mVdList;
    private ImageViewer imageViewer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initTitleText("产品介绍");

        setTitleRightSecondIcon(R.mipmap.liebiao, new Action1<View>() {
            @Override
            public void call(View view) {
                Intent intent = new Intent(mContext, ProductListActivity.class);
                intent.putExtra(Config.INTENT_PARAMS3, productDetail.getShopid() + "");
                startActivity(intent);
            }
        });

        initView();

        int id = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);

        DialogManager.getInstance().showNetLoadingView(this);
        getPresenter().product_info(id + "");
    }

    private void initView() {
        mRvPic = (RecyclerView) findViewById(R.id.rv_pic);

        mJzVideo = (JzvdStd) findViewById(R.id.jz_video);
        mFlVideo = (FrameLayout) findViewById(R.id.fl_video);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        mTvJieshao = (ExpandableTextView) findViewById(R.id.tv_jieshao);
        mRlChakan = (RelativeLayout) findViewById(R.id.rl_chakan);


        imageViewer = findViewById(R.id.imageViewer);
    }

    private ProductDetail productDetail;

    public void setData(ProductDetail productDetail) {
        this.productDetail = productDetail;
        initRightIcon();

        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams params = mJzVideo.getLayoutParams();
        // 宽度为屏幕宽度
        params.width = getResources().getDisplayMetrics().widthPixels;
        // 高度为宽度的9/16
        params.height = (int) (params.width * 9f / 16f);
        mJzVideo.setLayoutParams(params);

        JZDataSource jzDataSource = new JZDataSource(productDetail.getVideourl());
        mJzVideo.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL);

        mJzVideo.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        GlideUtils.loadImage(mContext, productDetail.getVideocoverurl(), mJzVideo.thumbImageView);


        mTvTitle.setText(productDetail.getProductname());
        mTvCompanyName.setText(productDetail.getCompanyname());
        mTvJieshao.setText(productDetail.getContent());


        mRlChakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CompanyProfilesActivity.class);
                intent.putExtra(Config.INTENT_PARAMS1, productDetail.getShopid());
                startActivity(intent);
            }
        });

        List<String> photos = productDetail.getPhotos();

        OnionRecycleAdapter jiazhiAdapter = new OnionRecycleAdapter<String>(R.layout.item_xuanchuanpian_layout, photos) {
            @Override
            protected void convert(BaseViewHolder holder, final String item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item);
            }
        };

        jiazhiAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int[] location = new int[2];
                // 获取在整个屏幕内的绝对坐标
                view.getLocationOnScreen(location);
                ViewData viewData = mVdList.get(position);
                viewData.setTargetX(location[0]);
                viewData.setTargetY(location[1]);
                imageViewer.viewData(mVdList)
                        .watch(position);
            }
        });

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);


        mRvPic.setFocusableInTouchMode(false);
        mRvPic.setNestedScrollingEnabled(false);
        mRvPic.setLayoutManager(linearLayoutManager2);// 布局管理器。
        mRvPic.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvPic.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvPic.setAdapter(jiazhiAdapter);
        linearLayoutManager2.scrollToPositionWithOffset(0, 0);

        Point mScreenSize = ScreenUtils.getScreenSize(this);
        mVdList = new ArrayList<>();
        for (int i = 0, len = photos.size(); i < len; i++) {
            ViewData viewData = new ViewData();
            viewData.setImageSrc(photos.get(i));
            viewData.setTargetX(0);
            viewData.setTargetY(0);
            viewData.setTargetWidth(mScreenSize.x);
            viewData.setTargetHeight(Utils.dp2px(this, 200));
            mVdList.add(viewData);
        }



        imageViewer.overlayStatusBar(false)
                .imageLoader(new PhotoLoader());


        imageViewer
                .setOnItemChangedListener(new OnItemChangedListener() {
                    @Override
                    public void onItemChanged(int position, ImageDrawee drawee) {
                        if (imageViewer.getViewStatus() == ViewerStatus.STATUS_WATCHING) {
                            mVdList.get(imageViewer.getCurrentPosition()).setTargetX(0);
                            linearLayoutManager2.scrollToPositionWithOffset(imageViewer.getCurrentPosition(), (int) (mVdList.get(imageViewer.getCurrentPosition()).getTargetX() / 2));
                        }
                    }
                })
                .setOnBrowseStatusListener(
                        new OnBrowseStatusListener() {
                            @Override
                            public void onBrowseStatus(int status) {
                                if (status == ViewerStatus.STATUS_BEGIN_OPEN) {
                                    ScreenUtils.changeStatusBarColor(ProductDetailActivity.this, R.color.black);
                                } else if (status == ViewerStatus.STATUS_SILENCE) {
                                    ScreenUtils.changeStatusBarColor(ProductDetailActivity.this, R.color.white);
                                }
                            }
                        });
    }


    private void initRightIcon() {
        if (productDetail.getIs_collect() == 1) {
            setTitleRigthIcon(R.mipmap.shoucang, new Action1<View>() {
                @Override
                public void call(View view) {
                    collect(productDetail.getId() + "", "2");
                }
            });
        } else {
            setTitleRigthIcon(R.mipmap.shoucang_pre, new Action1<View>() {
                @Override
                public void call(View view) {
                    collect(productDetail.getId() + "", "1");
                }
            });
        }
    }


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
                //1收藏 2 取消收藏
                if (acttype.equals("1")) {
                    productDetail.setIs_collect(1);
                } else {
                    productDetail.setIs_collect(0);
                }
                initRightIcon();
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

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean b = imageViewer.onKeyDown(keyCode, event);
        if (b) {
            return b;
        }
        return super.onKeyDown(keyCode, event);
    }
}
