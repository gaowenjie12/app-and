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
import com.zsoe.businesssharing.base.BrowserActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.business.exhibitionhall.BangZhuActivity;
import com.zsoe.businesssharing.business.exhibitionhall.LatestNewsActivity;
import com.zsoe.businesssharing.business.exhibitionhall.TuiGuangActivity;
import com.zsoe.businesssharing.business.home.FinancingLoansActivity;
import com.zsoe.businesssharing.business.home.JoinInvestmentActivity;
import com.zsoe.businesssharing.business.me.BasicInformationActivity;
import com.zsoe.businesssharing.business.me.CardVolumeListActivity;
import com.zsoe.businesssharing.business.me.CertificationCompanyActivity;
import com.zsoe.businesssharing.business.me.CollectionListActivity;
import com.zsoe.businesssharing.business.me.LiuYanBanActivity;
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


    }

    private void initUser() {
        FrecoFactory.getInstance().disPlay(user_image, DApplication.getInstance().getLoginUser().getAvatar());
        user_name.setText(DApplication.getInstance().getLoginUser().getUsername());
        user_gongsi.setText(DApplication.getInstance().getLoginUser().getIdentify());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != DApplication.getInstance().getLoginUser() && DApplication.getInstance().getLoginUser().getType() > 0) {
            //领导身份

            iamge2.setImageResource(R.mipmap.bangzhu);
            tv_liuyan.setText("帮助中心");
            ll_liuyanban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //跳入帮助中心
                    if (isLoginIntent()) {

                        Intent intent = new Intent(mContext, BangZhuActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1, 4);
                        startActivity(intent);
                    }

                }
            });

            ll_xinxiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isLoginIntent()) {

                        startActivity(new Intent(mContext, MessageRemindActivity.class));
                    }
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
                    if (isLoginIntent()) {

                        Intent intent = new Intent(mContext, BangZhuActivity.class);
                        intent.putExtra(Config.INTENT_PARAMS1, 4);
                        startActivity(intent);
                    }
                }
            });

            ll_liuyanban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isLoginIntent()) {

                        startActivity(new Intent(mContext, LiuYanBanActivity.class));
                    }
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
        ll_wode.setOnClickListener(this);
        ll_shoucang.setOnClickListener(this);
        ll_kajuan.setOnClickListener(this);
        ll_shezhi.setOnClickListener(this);
        ll_kefu.setOnClickListener(this);
        tv_sign.setOnClickListener(this);

        if (null != DApplication.getInstance().getLoginUser()) {
            initUser();
        } else {
            user_name.setText("请登录");
            user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DApplication.getInstance().startLogin();
                }
            });
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign:
                if (isLoginIntent()) {

                    startActivity(new Intent(mContext, MySignActivity.class));
                }

                break;

            case R.id.ll_jianzhi:
                if (isLoginIntent()) {

                    startActivity(new Intent(mContext, ProductManagementActivity.class));
                }

                break;

            case R.id.ll_jiameng:
                if (isLoginIntent()) {

                    Intent intent3 = new Intent(mContext, JoinInvestmentActivity.class);
                    intent3.putExtra(Config.INTENT_PARAMS1, DApplication.getInstance().getLoginUser().getShopid() + "");
                    intent3.putExtra(Config.INTENT_PARAMS2, "my");
                    startActivity(intent3);
                }

                break;

            case R.id.ll_kunc:
                if (isLoginIntent()) {

                    Intent intent1 = new Intent(mContext, ProcurementManagementActivity.class);
                    intent1.putExtra(Config.INTENT_PARAMS1, 1);
                    startActivity(intent1);
                }

                break;
            case R.id.ll_daikuan:
                if (isLoginIntent()) {

                    Intent intent4 = new Intent(mContext, FinancingLoansActivity.class);
                    intent4.putExtra(Config.INTENT_PARAMS1, DApplication.getInstance().getLoginUser().getShopid() + "");
                    intent4.putExtra(Config.INTENT_PARAMS2, "my");
                    startActivity(intent4);
                }
                break;
            case R.id.ll_zixun:
                if (isLoginIntent()) {

                    Intent intent = new Intent(mContext, LatestNewsActivity.class);
                    intent.putExtra(Config.INTENT_PARAMS1, 2);
                    startActivity(intent);
                }

                break;

            case R.id.ll_caigou:
                if (isLoginIntent()) {

                    Intent intent2 = new Intent(mContext, ProcurementManagementActivity.class);
                    intent2.putExtra(Config.INTENT_PARAMS1, 2);
                    startActivity(intent2);
                }

                break;
            case R.id.ll_renzheng:
                if (isLoginIntent()) {

                    startActivity(new Intent(mContext, CertificationCompanyActivity.class));
                }
                break;

            case R.id.ll_tuiguang:
                if (isLoginIntent()) {

                    Intent intent5 = new Intent(mContext, TuiGuangActivity.class);
                    intent5.putExtra(Config.INTENT_PARAMS1, DApplication.getInstance().getLoginUser().getShopid() + "");
                    intent5.putExtra(Config.INTENT_PARAMS2, "my");
                    startActivity(intent5);
                }

                break;
            case R.id.ll_wode:
                if (isLoginIntent()) {

                    startActivityForResult(new Intent(mContext, BasicInformationActivity.class), 2);
                }
                break;
            case R.id.ll_shoucang:
                if (isLoginIntent()) {

                    startActivity(new Intent(mContext, CollectionListActivity.class));
                }
                break;
            case R.id.ll_kajuan:
                if (isLoginIntent()) {

                    startActivity(new Intent(mContext, CardVolumeListActivity.class));
                }
                break;
            case R.id.ll_shezhi:
                if (isLoginIntent()) {

                    startActivity(new Intent(mContext, SetUpActivity.class));
                }
                break;
            case R.id.ll_kefu:
                if (isLoginIntent()) {

                    Intent intent6 = new Intent(mContext, BrowserActivity.class);
                    intent6.putExtra(Config.INTENT_PARAMS1, DApplication.getInstance().getLoginUser().getCustomerurl());
                    intent6.putExtra(Config.INTENT_PARAMS2, "客服");
                    startActivity(intent6);
                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            initUser();
        }
    }
}
