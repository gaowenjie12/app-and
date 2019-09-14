package com.zsoe.businesssharing.business.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.presenter.HttpResponseFunc;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.DetailJoinInvestmentBean;
import com.zsoe.businesssharing.business.exhibitionhall.CompanyProfilesActivity;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;
import com.zsoe.businesssharing.utils.GlideUtils;
import com.zsoe.businesssharing.utils.android.schedulers.AndroidSchedulers;

import java.util.HashMap;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import okhttp3.FormBody;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@RequiresPresenter(DetailJoinInvestmentPresenter.class)
public class JoinInvestmentDetailActivity extends BaseActivity<DetailJoinInvestmentPresenter> {

    private JzvdStd mJzVideo;
    private TextView mTvTitle;
    private TextView mTvCompanyName;
    private ExpandableTextView mTvJieshao;
    /**
     * 更多
     */
    private TextView mTvTuiguangMore;
    private SimpleDraweeView mProductImage;
    private ExpandableTextView mTvQiyeJieshao;
    private ExpandableTextView mTvZhengce;
    private FrameLayout mFlVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_investment_detail);
        initView();
        initTitleText("招商加盟详情");

        int id = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        DialogManager.getInstance().showNetLoadingView(mContext);
        getPresenter().join_merchant_info(id + "");
    }


    private void initView() {
        mJzVideo = (JzvdStd) findViewById(R.id.jz_video);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        mTvJieshao = (ExpandableTextView) findViewById(R.id.tv_jieshao);
        mTvTuiguangMore = (TextView) findViewById(R.id.tv_tuiguang_more);
        mProductImage = (SimpleDraweeView) findViewById(R.id.product_image);
        mTvQiyeJieshao = (ExpandableTextView) findViewById(R.id.tv_qiye_jieshao);
        mTvZhengce = (ExpandableTextView) findViewById(R.id.tv_zhengce);
        mFlVideo = (FrameLayout) findViewById(R.id.fl_video);
    }

    DetailJoinInvestmentBean detailJoinInvestmentBean;

    public void setDate(DetailJoinInvestmentBean detailJoinInvestmentBean) {
        if (null == detailJoinInvestmentBean) {
            ToastUtils.showShort("对象为空");
            finish();
            return;
        }

        this.detailJoinInvestmentBean = detailJoinInvestmentBean;

        initRightIcon();

        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams params = mJzVideo.getLayoutParams();
        // 宽度为屏幕宽度
        params.width = getResources().getDisplayMetrics().widthPixels;
        // 高度为宽度的9/16
        params.height = (int) (params.width * 9f / 16f);
        mJzVideo.setLayoutParams(params);

        JZDataSource jzDataSource = new JZDataSource(detailJoinInvestmentBean.getVideourl());
        mJzVideo.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL);

        mJzVideo.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        GlideUtils.loadImage(mContext, detailJoinInvestmentBean.getVideocoverurl(), mJzVideo.thumbImageView);


        mTvTitle.setText(detailJoinInvestmentBean.getTitle());
        mTvCompanyName.setText(detailJoinInvestmentBean.getCompanyname());
        mTvJieshao.setText(detailJoinInvestmentBean.getContent());

        mTvQiyeJieshao.setText(detailJoinInvestmentBean.getDes());
        mTvZhengce.setText(detailJoinInvestmentBean.getPolicytext());

        FrecoFactory.getInstance().disPlay(mProductImage, detailJoinInvestmentBean.getThumb());


        mTvTuiguangMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, CompanyProfilesActivity.class));
            }
        });
    }

    private void initRightIcon() {
        if (detailJoinInvestmentBean.getIs_collect() == 1) {
            setTitleRigthIcon(R.mipmap.shoucang, new Action1<View>() {
                @Override
                public void call(View view) {
                    collect(detailJoinInvestmentBean.getId() + "", "2");
                }
            });
        } else {
            setTitleRigthIcon(R.mipmap.shoucang_pre, new Action1<View>() {
                @Override
                public void call(View view) {
                    collect(detailJoinInvestmentBean.getId() + "", "1");
                }
            });
        }
    }


    /**
     *
     */
    public void collect(String valueid, String acttype) {

        DialogManager.getInstance().showNetLoadingView(mContext);

        HashMap<String, String> params = new HashMap<>();
        params.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        params.put("type", "4");
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
                    detailJoinInvestmentBean.setIs_collect(1);
                } else {
                    detailJoinInvestmentBean.setIs_collect(0);
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
}
