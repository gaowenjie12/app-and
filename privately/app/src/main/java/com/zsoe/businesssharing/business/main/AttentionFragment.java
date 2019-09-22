package com.zsoe.businesssharing.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.bean.TabEntity;
import com.zsoe.businesssharing.business.attention.ArchitecturalCultureFragment;
import com.zsoe.businesssharing.business.attention.ChapterIntroductionFragment;
import com.zsoe.businesssharing.business.attention.MailFragment;
import com.zsoe.businesssharing.business.attention.TheEventFragment;
import com.zsoe.businesssharing.business.home.SearchActivity;
import com.zsoe.businesssharing.commonview.ClearEditText;

import java.util.ArrayList;

/**
 * 关注
 */

public class AttentionFragment extends BaseFragment implements View.OnClickListener {

    private CommonTabLayout mTabLayout_6;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    public static AttentionFragment newInstance(String title) {
        AttentionFragment f = new AttentionFragment();
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
        return R.layout.fragment_attention;
    }


    private ChapterIntroductionFragment chapterIntroductionFragment;
    private ArchitecturalCultureFragment architecturalCultureFragment;
    private TheEventFragment theEventFragment;
    private MailFragment mailFragment;
    protected BaseFragment currentFragment;

    private ClearEditText search_input;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /** indicator矩形圆角 */
        mTabLayout_6 = view.findViewById(R.id.tl_6);

        view.findViewById(R.id.tv_sousuo).setVisibility(View.GONE);

        search_input = view.findViewById(R.id.search_input);
        view.findViewById(R.id.tv_sousuo).setVisibility(View.GONE);
        search_input.setEnabled(true);
        search_input.setFocusable(false);
        search_input.setOnClickListener(this);

        mTabEntities.add(new TabEntity("分会介绍", 0, 0));
        mTabEntities.add(new TabEntity("架构文化", 0, 0));
        mTabEntities.add(new TabEntity("事件", 0, 0));
        mTabEntities.add(new TabEntity("信箱", 0, 0));

        mTabLayout_6.setTabData(mTabEntities);
        mTabLayout_6.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:

                        if (chapterIntroductionFragment == null) {
                            chapterIntroductionFragment = ChapterIntroductionFragment.newInstance("");
                        }
                        addOrShowFragment(getChildFragmentManager().beginTransaction(), chapterIntroductionFragment);
                        break;

                    case 1:
                        if (architecturalCultureFragment == null) {
                            architecturalCultureFragment = ArchitecturalCultureFragment.newInstance("");
                        }
                        addOrShowFragment(getChildFragmentManager().beginTransaction(), architecturalCultureFragment);
                        break;

                    case 2:
                        if (theEventFragment == null) {
                            theEventFragment = TheEventFragment.newInstance("");
                        }
                        addOrShowFragment(getChildFragmentManager().beginTransaction(), theEventFragment);
                        break;
                    case 3:
                        if (mailFragment == null) {
                            mailFragment = MailFragment.newInstance("");
                        }
                        addOrShowFragment(getChildFragmentManager().beginTransaction(), mailFragment);
                        break;

                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mTabLayout_6.setCurrentTab(0);
        initData();

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


    /**
     * 默认第一个tab的Fragment
     */
    private void initData() {
        if (chapterIntroductionFragment == null) {
            chapterIntroductionFragment = ChapterIntroductionFragment.newInstance(getString(R.string.tab_1));
        }
        if (!chapterIntroductionFragment.isAdded()) {

            getChildFragmentManager().beginTransaction().add(R.id.fl_content, chapterIntroductionFragment).commitAllowingStateLoss();

        } else {
            getChildFragmentManager().beginTransaction().show(chapterIntroductionFragment).commitAllowingStateLoss();
        }

        currentFragment = chapterIntroductionFragment;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(mContext, SearchActivity.class));
    }
}
