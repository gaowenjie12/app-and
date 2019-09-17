package com.zsoe.businesssharing.business.me;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.BaseFragment;


/**
 * 贷款融资
 */
public class LiuYanRootActivity extends BaseActivity {


    private String[] mTitles = {"留言板", "回复"};
    private SegmentTabLayout mTl4;
    private FrameLayout mFlChange;
    protected BaseFragment currentFragment;
    private LiuYanBanFragment yinHangXinDaiFragment;
    private LiuYanBanFragment rongZiXiangMuFragment;
    private ImageView title_left_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_loans);
        initView();
        initData();
    }

    private void initView() {
        title_left_iv = (ImageView) findViewById(R.id.title_left_iv);
        mTl4 = (SegmentTabLayout) findViewById(R.id.tl_4);
        mFlChange = (FrameLayout) findViewById(R.id.fl_change);

        title_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTl4.setTabData(mTitles);
        mTl4.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                if (position == 0) {
                    if (yinHangXinDaiFragment == null) {
                        yinHangXinDaiFragment = LiuYanBanFragment.newInstance("1");
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), yinHangXinDaiFragment);
                } else if (position == 1) {
                    if (rongZiXiangMuFragment == null) {
                        rongZiXiangMuFragment = LiuYanBanFragment.newInstance("2");
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), rongZiXiangMuFragment);
                }

            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }


    /**
     * 默认第一个tab的Fragment
     */
    private void initData() {
        if (yinHangXinDaiFragment == null) {
            yinHangXinDaiFragment = LiuYanBanFragment.newInstance("1");
        }
        if (!yinHangXinDaiFragment.isAdded()) {

            getSupportFragmentManager().beginTransaction().add(R.id.fl_change, yinHangXinDaiFragment).commitAllowingStateLoss();

        } else {
            getSupportFragmentManager().beginTransaction().show(yinHangXinDaiFragment).commitAllowingStateLoss();
        }

        currentFragment = yinHangXinDaiFragment;
    }


    /**
     * 添加或者显示 fragment
     *
     * @param transaction
     * @param fragment
     */
    protected void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.fl_change, fragment).commitAllowingStateLoss();
        } else {
            transaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
        }

        currentFragment = (BaseFragment) fragment;
    }

}
