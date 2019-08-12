package com.zsoe.businesssharing.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.business.exhibitionhall.LatestNewsActivity;
import com.zsoe.businesssharing.business.me.MessageRemindActivity;

import rx.functions.Action1;

/**
 * 消息
 */

public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "HomeFragment";

    public static MessageFragment newInstance(String title) {
        MessageFragment f = new MessageFragment();
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
        return R.layout.fragment_message;
    }


    private RelativeLayout rl_xiaoxitixing, rl_xitonggonggao, rl_hangyezixun, rl_lingdaohuixin, rl_liaotianliebiao;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setTitleText(true,"消息");

        setTitleLeftIcon(false,0);

        setTitleRigthIcon(true, R.mipmap.tianjia, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        setTitleRightSecondIcon(R.mipmap.tongxunlu, new Action1<View>() {
            @Override
            public void call(View view) {

            }
        });

        rl_xiaoxitixing = view.findViewById(R.id.rl_xiaoxitixing);
        rl_xitonggonggao = view.findViewById(R.id.rl_xitonggonggao);
        rl_hangyezixun = view.findViewById(R.id.rl_hangyezixun);
        rl_lingdaohuixin = view.findViewById(R.id.rl_lingdaohuixin);
        rl_liaotianliebiao = view.findViewById(R.id.rl_liaotianliebiao);


        rl_xiaoxitixing.setOnClickListener(this);
        rl_xitonggonggao.setOnClickListener(this);
        rl_hangyezixun.setOnClickListener(this);
        rl_lingdaohuixin.setOnClickListener(this);
        rl_liaotianliebiao.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_xiaoxitixing:
                startActivity(new Intent(mContext, MessageRemindActivity.class));


                break;

            case R.id.rl_xitonggonggao:
                startActivity(new Intent(mContext, LatestNewsActivity.class));
                break;

            case R.id.rl_hangyezixun:
                startActivity(new Intent(mContext, LatestNewsActivity.class));

                break;

            case R.id.rl_lingdaohuixin:
                break;

            case R.id.rl_liaotianliebiao:
                break;
        }
    }
}
