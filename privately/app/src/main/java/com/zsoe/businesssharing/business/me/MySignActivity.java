package com.zsoe.businesssharing.business.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ItemInsdustry;
import com.zsoe.businesssharing.bean.MySignBean;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.DialogManager;

@RequiresPresenter(MySignPresenter.class)
public class MySignActivity extends BaseActivity<MySignPresenter> {

    private ImageView mIvImage;
    /**
     * 2天
     */
    private TextView mTvDaycount;
    /**
     * 签到
     */
    private TextView mTvQiandao;
    /**
     * 连续签到三天赠送排名券
     * 连续签到五天赠送排名券
     */
    private TextView mTvFirstGuize;
    private RecyclerView mRvCard;
    /**
     * 连续签到三天赠送排名券
     * 连续签到五天赠送排名券
     */
    private TextView mTvSecondGuize;
    /**
     * 再签到一天可领取排名卷
     */
    private TextView mTvTishi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sign);
        initView();
        initTitleText("我的签到");

        DialogManager.getInstance().showNetLoadingView(mContext);
        getPresenter().mysign(DApplication.getInstance().getLoginUser().getId() + "");
    }

    private void initView() {
        mIvImage = (ImageView) findViewById(R.id.iv_image);
        mTvDaycount = (TextView) findViewById(R.id.tv_daycount);
        mTvQiandao = (TextView) findViewById(R.id.tv_qiandao);
        mTvFirstGuize = (TextView) findViewById(R.id.tv_first_guize);
        mRvCard = (RecyclerView) findViewById(R.id.rv_card);
        mTvSecondGuize = (TextView) findViewById(R.id.tv_second_guize);
        mTvTishi = (TextView) findViewById(R.id.tv_tishi);
    }


    public void setDate(MySignBean mySignBean) {

        mTvDaycount.setText(mySignBean.getSigndays() + "天");
        mTvTishi.setText(mySignBean.getSigntext());
        mTvFirstGuize.setText(mySignBean.getSignrule());
        mTvSecondGuize.setText(mySignBean.getRankticketrule());


        if (mySignBean.isIs_todaysign()) {
            mTvQiandao.setText("已签到");
        } else {
            mTvQiandao.setText("签到");
            mTvQiandao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogManager.getInstance().showNetLoadingView(mContext);
                    getPresenter().sign(DApplication.getInstance().getLoginUser().getId() + "");
                }
            });

        }


        OnionRecycleAdapter hangyeAdapter = new OnionRecycleAdapter<ItemInsdustry>(R.layout.item_sign_card_layout, mySignBean.getRankticket_list()) {
            @Override
            protected void convert(BaseViewHolder holder, final ItemInsdustry item) {
                super.convert(holder, item);

                holder.setText(R.id.tv_name, item.getName());
                holder.setText(R.id.tv_tiaojian, item.getDes());

                holder.getView(R.id.tv_shiyong).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogManager.getInstance().showNetLoadingView(mContext);
                        getPresenter().use_rankticket(DApplication.getInstance().getLoginUser().getId() + "", item.getId() + "");
                    }
                });

            }
        };

        mRvCard.setFocusableInTouchMode(false);
        mRvCard.setNestedScrollingEnabled(false);
        mRvCard.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvCard.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvCard.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvCard.setAdapter(hangyeAdapter);
    }

    public void success(RootResponse t) {
        ToastUtils.showShort(t.getMsg());
        getPresenter().mysign(DApplication.getInstance().getLoginUser().getId() + "");
    }
}
