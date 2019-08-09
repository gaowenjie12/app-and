package com.zsoe.businesssharing.business.main;

import android.os.Bundle;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;

/**
 * 消息
 */

public class MessageFragment extends BaseFragment {

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
}
