package com.zsoe.businesssharing.business.me;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class CardVolumeListActivity extends BaseActivity {

    private RecyclerView mRvNotice;
    private PtrClassicFrameLayout mPtrLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_volume_list);
        initView();
        initTitleText("我的卡卷列表");

    }

    private void initView() {
        mRvNotice = (RecyclerView) findViewById(R.id.rv_notice);
        mPtrLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_layout);
    }
}
