package com.zsoe.businesssharing.business.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.bean.TabEntity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {


//    private ViewPager mViewPager;

    private String[] mTitles = {"首页", "展厅", "关注", "消息", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_shouye_pre, R.mipmap.tab_zhanting_pre,
            R.mipmap.tab_guanzhu_pre, R.mipmap.tab_xiaoxi_pre, R.mipmap.tab_wode_pre};

    private int[] mIconSelectIds = {
            R.mipmap.tab_shouye, R.mipmap.tab_zhanting,
            R.mipmap.tab_guanzhu, R.mipmap.tab_xiaoxi, R.mipmap.tab_wode};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    protected BaseFragment currentFragment;
    private CommonTabLayout mBottomNavigation;


    private HomeFragment homeFragment;//首页
    private GalleryRoomFragment galleryRoomFragment;//展厅
    private AttentionFragment attentionFragment;//关注
    private MessageFragment messageFragment;//消息
    private MeFragment meFragment;//我的


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initBottomNavigationView();
        initData();
    }


    /**
     * 默认第一个tab的Fragment
     */
    private void initData() {
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance(getString(R.string.tab_1));
        }
        if (!homeFragment.isAdded()) {

            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, homeFragment).commitAllowingStateLoss();

        } else {
            getSupportFragmentManager().beginTransaction().show(homeFragment).commitAllowingStateLoss();
        }

        currentFragment = homeFragment;
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
            transaction.hide(currentFragment).add(R.id.fl_content, fragment).commitAllowingStateLoss();
        } else {
            transaction.hide(currentFragment).show(fragment).commitAllowingStateLoss();
        }

        currentFragment = (BaseFragment) fragment;
    }

    private void initView() {
//        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        mBottomNavigation = (CommonTabLayout) findViewById(R.id.bottom_navigation);
    }

    private void initBottomNavigationView() {

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }


        mBottomNavigation.setTabData(mTabEntities);
        mBottomNavigation.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {// 首页
                    if (homeFragment == null) {
                        homeFragment = HomeFragment.newInstance(getString(R.string.tab_1));
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), homeFragment);

                } else if (position == 1) {// 展厅
                    if (galleryRoomFragment == null) {
                        galleryRoomFragment = GalleryRoomFragment.newInstance(getString(R.string.tab_2));
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), galleryRoomFragment);
                } else if (position == 2) {// 关注
                    if (attentionFragment == null) {
                        attentionFragment = AttentionFragment.newInstance(getString(R.string.tab_3));
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), attentionFragment);
                } else if (position == 3) {//消息
                    if (messageFragment == null) {
                        messageFragment = MessageFragment.newInstance(getString(R.string.tab_4));
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), messageFragment);
                } else if (position == 4) {//我的
                    if (meFragment == null) {
                        meFragment = MeFragment.newInstance(getString(R.string.tab_5));
                    }
                    addOrShowFragment(getSupportFragmentManager().beginTransaction(), meFragment);
                }

            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }
}
