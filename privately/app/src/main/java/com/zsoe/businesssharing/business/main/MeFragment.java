package com.zsoe.businesssharing.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.business.exhibitionhall.CommunicationMeetingForeshowActivity;
import com.zsoe.businesssharing.business.exhibitionhall.LatestNewsActivity;
import com.zsoe.businesssharing.business.home.FinancingLoansActivity;
import com.zsoe.businesssharing.business.home.JoinInvestmentActivity;
import com.zsoe.businesssharing.business.me.BasicInformationActivity;
import com.zsoe.businesssharing.business.me.CardVolumeListActivity;
import com.zsoe.businesssharing.business.me.CertificationCompanyActivity;
import com.zsoe.businesssharing.business.me.CollectionListActivity;
import com.zsoe.businesssharing.business.me.MessageRemindActivity;
import com.zsoe.businesssharing.business.me.MySignActivity;
import com.zsoe.businesssharing.business.me.ProcurementManagementActivity;
import com.zsoe.businesssharing.business.me.ProductManagementActivity;
import com.zsoe.businesssharing.business.me.SetUpActivity;
import com.zsoe.businesssharing.commonview.SquareLinearLayout;
import com.zsoe.businesssharing.utils.FrecoFactory;

/**
 * 我的
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "HomeFragment";

    public static MeFragment newInstance(String title) {
        MeFragment f = new MeFragment();
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
        return R.layout.fragment_me;
    }


    SimpleDraweeView user_image;
    TextView user_name, user_gongsi, tv_sign, tv_xinxiang, tv_liuyan;
    SquareLinearLayout ll_jianzhi, ll_jiameng, ll_kunc, ll_daikuan, ll_zixun, ll_caigou,
            ll_renzheng, ll_xinxiang, ll_tuiguang, ll_liuyanban, ll_wode, ll_shoucang, ll_kajuan, ll_shezhi, ll_kefu;

    ImageView imgage, iamge2;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitleLeftIcon(false, 0);
        setTitleText(true, "我的");

        user_image = view.findViewById(R.id.user_image);
        user_name = view.findViewById(R.id.user_name);
        user_gongsi = view.findViewById(R.id.user_gongsi);
        tv_sign = view.findViewById(R.id.tv_sign);

        ll_jianzhi = view.findViewById(R.id.ll_jianzhi);
        ll_jiameng = view.findViewById(R.id.ll_jiameng);
        ll_kunc = view.findViewById(R.id.ll_kunc);
        ll_daikuan = view.findViewById(R.id.ll_daikuan);
        ll_zixun = view.findViewById(R.id.ll_zixun);
        ll_caigou = view.findViewById(R.id.ll_caigou);
        ll_renzheng = view.findViewById(R.id.ll_renzheng);
        ll_xinxiang = view.findViewById(R.id.ll_xinxiang);
        ll_tuiguang = view.findViewById(R.id.ll_tuiguang);
        ll_liuyanban = view.findViewById(R.id.ll_liuyanban);
        ll_wode = view.findViewById(R.id.ll_wode);
        ll_shoucang = view.findViewById(R.id.ll_shoucang);
        ll_kajuan = view.findViewById(R.id.ll_kajuan);
        ll_shezhi = view.findViewById(R.id.ll_shezhi);
        ll_kefu = view.findViewById(R.id.ll_kefu);

        tv_xinxiang = view.findViewById(R.id.tv_xinxiang);
        imgage = view.findViewById(R.id.imgage);

        tv_liuyan = view.findViewById(R.id.tv_liuyan);
        iamge2 = view.findViewById(R.id.iamge2);

        if (DApplication.getInstance().getLoginUser().getType() > 0) {
            //领导身份

            iamge2.setImageResource(R.mipmap.bangzhu);
            tv_liuyan.setText("帮助中心");
            ll_liuyanban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //跳入帮助中心
                }
            });

            ll_xinxiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext, MessageRemindActivity.class));
                }
            });

        } else {
            //普通身份
            imgage.setImageResource(R.mipmap.bangzhu);
            tv_xinxiang.setText("帮助中心");
            ll_xinxiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //跳入帮助中心

                }
            });

            ll_liuyanban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(mContext, MessageRemindActivity.class));
                }
            });
        }


        ll_jianzhi.setOnClickListener(this);
        ll_jiameng.setOnClickListener(this);
        ll_kunc.setOnClickListener(this);
        ll_daikuan.setOnClickListener(this);
        ll_zixun.setOnClickListener(this);
        ll_caigou.setOnClickListener(this);
        ll_renzheng.setOnClickListener(this);

        ll_tuiguang.setOnClickListener(this);
        ll_liuyanban.setOnClickListener(this);
        ll_wode.setOnClickListener(this);
        ll_shoucang.setOnClickListener(this);
        ll_kajuan.setOnClickListener(this);
        ll_shezhi.setOnClickListener(this);
        ll_kefu.setOnClickListener(this);
        tv_sign.setOnClickListener(this);

        FrecoFactory.getInstance().disPlay(user_image, "http://hbimg.b0.upaiyun.com/3e14d836d89498b116834b2987dbaa1c8f2e85a418a9fc-nLVWsW_fw658");
        user_name.setText("马云");
        user_gongsi.setText("阿里山楼宇清洁服务有限公司");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign:
                startActivity(new Intent(mContext, MySignActivity.class));

                break;

            case R.id.ll_jianzhi:
                startActivity(new Intent(mContext, ProductManagementActivity.class));

                break;

            case R.id.ll_jiameng:
                startActivity(new Intent(mContext, JoinInvestmentActivity.class));

                break;

            case R.id.ll_kunc:
                Intent intent1 = new Intent(mContext, ProcurementManagementActivity.class);
                intent1.putExtra(Config.INTENT_PARAMS1, 1);
                startActivity(intent1);

                break;
            case R.id.ll_daikuan:
                startActivity(new Intent(mContext, FinancingLoansActivity.class));
                break;
            case R.id.ll_zixun:

                Intent intent = new Intent(mContext, LatestNewsActivity.class);
                intent.putExtra(Config.INTENT_PARAMS1, 4);
                startActivity(intent);

                break;

            case R.id.ll_caigou:
                Intent intent2 = new Intent(mContext, ProcurementManagementActivity.class);
                intent2.putExtra(Config.INTENT_PARAMS1, 2);
                startActivity(intent2);

                break;
            case R.id.ll_renzheng:

                startActivity(new Intent(mContext, CertificationCompanyActivity.class));

                break;

            case R.id.ll_tuiguang:
                startActivity(new Intent(mContext, CommunicationMeetingForeshowActivity.class));
                break;
            case R.id.ll_wode:
                startActivity(new Intent(mContext, BasicInformationActivity.class));

                break;
            case R.id.ll_shoucang:
                startActivity(new Intent(mContext, CollectionListActivity.class));

                break;
            case R.id.ll_kajuan:
                startActivity(new Intent(mContext, CardVolumeListActivity.class));

                break;
            case R.id.ll_shezhi:
                startActivity(new Intent(mContext, SetUpActivity.class));
                break;
            case R.id.ll_kefu:
                break;

        }
    }
}
