package com.zsoe.businesssharing.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hyphenate.chat.EMClient;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.business.exhibitionhall.LatestNewsActivity;
import com.zsoe.businesssharing.business.me.AddFriendActivity;
import com.zsoe.businesssharing.business.me.MessageRemindActivity;
import com.zsoe.businesssharing.business.message.ConversationListActivity;
import com.zsoe.businesssharing.business.message.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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


    private RelativeLayout rl_xitonggonggao, rl_hangyezixun, rl_lingdaohuixin, rl_liaotianliebiao;
    private TextView tv_lingdao_count, tv_huanxin_count;
    private LinearLayout the_one_layout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setTitleText(true, "消息");

        setTitleLeftIcon(false, 0);

        setTitleRigthIcon(true, R.mipmap.tianjia, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, AddFriendActivity.class));
            }
        });

        rl_xitonggonggao = view.findViewById(R.id.rl_xitonggonggao);
        the_one_layout = view.findViewById(R.id.the_one_layout);
        rl_hangyezixun = view.findViewById(R.id.rl_hangyezixun);
        rl_lingdaohuixin = view.findViewById(R.id.rl_lingdaohuixin);
        rl_liaotianliebiao = view.findViewById(R.id.rl_liaotianliebiao);

        tv_lingdao_count = view.findViewById(R.id.tv_lingdao_count);
        tv_huanxin_count = view.findViewById(R.id.tv_huanxin_count);


        rl_xitonggonggao.setOnClickListener(this);
        rl_hangyezixun.setOnClickListener(this);
        rl_lingdaohuixin.setOnClickListener(this);
        rl_liaotianliebiao.setOnClickListener(this);

        if (null!=DApplication.getInstance().getLoginUser()&&DApplication.getInstance().getLoginUser().getType() > 0) {
            rl_lingdaohuixin.setVisibility(View.GONE);
        } else {
            rl_lingdaohuixin.setVisibility(View.VISIBLE);
        }

        updateUnreadLabel();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null == DApplication.getInstance().getLoginUser()) {
            the_one_layout.setVisibility(View.GONE);
        } else {
            the_one_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_xitonggonggao:
                if (isLoginIntent()) {

                    Intent intent = new Intent(mContext, LatestNewsActivity.class);
                    intent.putExtra(Config.INTENT_PARAMS1, 3);
                    startActivity(intent);
                }

                break;

            case R.id.rl_hangyezixun:
                if (isLoginIntent()) {

                    Intent intent2 = new Intent(mContext, LatestNewsActivity.class);
                    intent2.putExtra(Config.INTENT_PARAMS1, 2);
                    startActivity(intent2);
                }
                break;

            case R.id.rl_lingdaohuixin:
                if (isLoginIntent()) {

                    Intent intent1 = new Intent(mContext, MessageRemindActivity.class);
                    intent1.putExtra(Config.INTENT_PARAMS1, 1);
                    startActivity(intent1);
                }
                break;

            case R.id.rl_liaotianliebiao:
                if (isLoginIntent()) {

                    Intent intent3 = new Intent(mContext, ConversationListActivity.class);
                    startActivity(intent3);
                }
                break;
        }
    }

    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            tv_huanxin_count.setText(String.valueOf(count));
            tv_huanxin_count.setVisibility(View.VISIBLE);
        } else {
            tv_huanxin_count.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMessageCount();
    }

    /**
     * 通知“我的”显示小红点
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        updateUnreadLabel();

    }


}
