package com.zsoe.businesssharing.business.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.ItemSearchBean;
import com.zsoe.businesssharing.bean.RootSearchBean;
import com.zsoe.businesssharing.bean.TabEntity;
import com.zsoe.businesssharing.commonview.ClearEditText;
import com.zsoe.businesssharing.commonview.MultiLineRadioGroup;
import com.zsoe.businesssharing.utils.DialogManager;

import java.util.ArrayList;
import java.util.List;

@RequiresPresenter(SearchPresenter.class)
public class SearchActivity extends BaseActivity<SearchPresenter> implements View.OnClickListener {

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
    private int type;


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
                type = position + 1;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mTabLayout_6.setCurrentTab(0);
        DialogManager.getInstance().showNetLoadingView(this);
        getPresenter().searchpage();


    }

    private void initView() {
        mSearchInput = (ClearEditText) findViewById(R.id.search_input);
        mTvSousuo = (TextView) findViewById(R.id.tv_sousuo);
        mTabLayout_6 = (CommonTabLayout) findViewById(R.id.tl_6);
        mIvShanchu = (ImageView) findViewById(R.id.iv_shanchu);
        mHistoryContent = (MultiLineRadioGroup) findViewById(R.id.history_content);
        mHotContent = (MultiLineRadioGroup) findViewById(R.id.hot_content);
        mIvShanchu.setOnClickListener(this);
        mTvSousuo.setOnClickListener(this);
    }

    public void setDate(RootSearchBean rootSearchBean) {


        List<ItemSearchBean> history_list = rootSearchBean.getHistory_list();
        mHistoryContent.removeAllViews();
        mHistoryContent.clearChecked();
        mHistoryContent.addAll(history_list);
        mHistoryContent.setOnCheckChangedListener(new MultiLineRadioGroup.OnCheckedChangedListener() {
            @Override
            public void onItemChecked(MultiLineRadioGroup group, int position, boolean checked) {
                if (checked) {
                    ItemSearchBean itemSearchBean = history_list.get(position);
                }
            }
        });


        List<ItemSearchBean> hot_list = rootSearchBean.getHot_list();
        mHotContent.removeAllViews();
        mHotContent.clearChecked();
        mHotContent.addAll(hot_list);
        mHotContent.setOnCheckChangedListener(new MultiLineRadioGroup.OnCheckedChangedListener() {
            @Override
            public void onItemChecked(MultiLineRadioGroup group, int position, boolean checked) {
                if (checked) {
                    ItemSearchBean itemSearchBean = hot_list.get(position);
                }
            }
        });


    }

    public void delSuccess() {
        List<ItemSearchBean> history_list = new ArrayList<>();
        mHistoryContent.removeAllViews();
        mHistoryContent.clearChecked();
        mHistoryContent.addAll(history_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_shanchu:
                DialogManager.getInstance().showNetLoadingView(this);
                getPresenter().historysearch_del();
                break;
            case R.id.tv_sousuo:
                String s = mSearchInput.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    ToastUtils.showShort("请输入搜索内容");
                    return;
                }


                Intent intent = new Intent(mContext, SearchListActivity.class);
                intent.putExtra(Config.INTENT_PARAMS1, type + "");
                intent.putExtra(Config.INTENT_PARAMS2, s);
                startActivity(intent);


                break;
        }
    }
}
