package com.zsoe.businesssharing.business.home;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.bean.TabEntity;
import com.zsoe.businesssharing.commonview.ClearEditText;
import com.zsoe.businesssharing.commonview.MultiLineRadioGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    /**
     * 请输入要搜索的内容
     */
    private ClearEditText mSearchInput;
    /**
     * 搜索
     */
    private TextView mTvSousuo;
    private CommonTabLayout mTabLayout_6;
    /**
     * 查看全部
     */
    private ImageView mIvShanchu;
    private MultiLineRadioGroup mHistoryContent;
    private MultiLineRadioGroup mHotContent;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();

        //按地域     按企业     按行业     按产品     按人名     按服务站

        mTabEntities.add(new TabEntity("按地域", 0, 0));
        mTabEntities.add(new TabEntity("按企业", 0, 0));
        mTabEntities.add(new TabEntity("按行业", 0, 0));
        mTabEntities.add(new TabEntity("按产品", 0, 0));
        mTabEntities.add(new TabEntity("按人名", 0, 0));
        mTabEntities.add(new TabEntity("按服务站", 0, 0));


        mTabLayout_6.setTabData(mTabEntities);
        mTabLayout_6.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:

                        break;

                    case 1:
                        break;

                    case 2:
                        break;
                    case 3:
                        break;

                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mTabLayout_6.setCurrentTab(0);

        List<String> phrase = new ArrayList<>();
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");
        phrase.add("培训班");

        mHistoryContent.removeAllViews();
        mHistoryContent.clearChecked();
        mHistoryContent.addAll(phrase);
        mHistoryContent.setEnabled(false);


        mHotContent.removeAllViews();
        mHotContent.clearChecked();
        mHotContent.addAll(phrase);
        mHotContent.setEnabled(false);


    }

    private void initView() {
        mSearchInput = (ClearEditText) findViewById(R.id.search_input);
        mTvSousuo = (TextView) findViewById(R.id.tv_sousuo);
        mTabLayout_6 = (CommonTabLayout) findViewById(R.id.tl_6);
        mIvShanchu = (ImageView) findViewById(R.id.iv_shanchu);
        mHistoryContent = (MultiLineRadioGroup) findViewById(R.id.history_content);
        mHotContent = (MultiLineRadioGroup) findViewById(R.id.hot_content);
    }
}
