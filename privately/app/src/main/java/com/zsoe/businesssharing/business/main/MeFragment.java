package com.zsoe.businesssharing.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.business.me.CardVolumeListActivity;
import com.zsoe.businesssharing.business.me.SetUpActivity;

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
    TextView user_name, user_gongsi, tv_sign;
    RelativeLayout rl_qiye_guanli, rl_wodesoucang, rl_kajuanbao, rl_shezhi, rl_dianhua, rl_zhongxin;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitleLeftIcon(false, 0);
        setTitleText(true, "我的");

        user_image = view.findViewById(R.id.user_image);
        user_name = view.findViewById(R.id.user_name);
        user_gongsi = view.findViewById(R.id.user_gongsi);
        tv_sign = view.findViewById(R.id.tv_sign);
        rl_qiye_guanli = view.findViewById(R.id.rl_qiye_guanli);
        rl_wodesoucang = view.findViewById(R.id.rl_wodesoucang);
        rl_kajuanbao = view.findViewById(R.id.rl_kajuanbao);
        rl_shezhi = view.findViewById(R.id.rl_shezhi);
        rl_dianhua = view.findViewById(R.id.rl_dianhua);
        rl_zhongxin = view.findViewById(R.id.rl_zhongxin);

        tv_sign.setOnClickListener(this);
        rl_qiye_guanli.setOnClickListener(this);
        rl_wodesoucang.setOnClickListener(this);
        rl_kajuanbao.setOnClickListener(this);
        rl_shezhi.setOnClickListener(this);
        rl_dianhua.setOnClickListener(this);
        rl_zhongxin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign:
                break;

            case R.id.rl_qiye_guanli:
                break;

            case R.id.rl_wodesoucang:
                break;

            case R.id.rl_kajuanbao:
                startActivity(new Intent(mContext, CardVolumeListActivity.class));
                break;
            case R.id.rl_shezhi:
                startActivity(new Intent(mContext, SetUpActivity.class));
                break;
            case R.id.rl_dianhua:
                break;

            case R.id.rl_zhongxin:
                break;


        }
    }
}
