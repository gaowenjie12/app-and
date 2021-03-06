package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.FancyUtils;
import com.zsoe.businesssharing.bean.ChildHangYe;
import com.zsoe.businesssharing.bean.GongYouBean;
import com.zsoe.businesssharing.bean.RootHangYe;
import com.zsoe.businesssharing.business.me.BasicInformationActivity;
import com.zsoe.businesssharing.commonview.dropfilter.adapter.BaseBaseAdapter;
import com.zsoe.businesssharing.commonview.dropfilter.adapter.SimpleTextAdapter;
import com.zsoe.businesssharing.commonview.dropfilter.util.DpUtils;
import com.zsoe.businesssharing.commonview.dropfilter.view.FilterCheckedTextView;
import com.zsoe.businesssharing.utils.EmptyUtil;

import java.util.List;

public class IndustryClassificationActivity extends BaseActivity {

    private ListView mLvFirst;
    private ListView mLvSecond;

    private BaseBaseAdapter<RootHangYe> mFirstAdpater;
    private BaseBaseAdapter<ChildHangYe> mSecondAdapter;

    private List<RootHangYe> provinceBeans;//省
    private List<ChildHangYe> selectCityBeans;

    private RootHangYe currentProvinceBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_classification);
        initView();

        initTitleText("全部行业");
        setDate();

    }

    private void initView() {
        mLvFirst = (ListView) findViewById(R.id.lv_first);
        mLvSecond = (ListView) findViewById(R.id.lv_second);

        //设置单选
        mLvFirst.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mLvSecond.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void setDate() {

        GongYouBean rootHangYe = FancyUtils.getRootHangYe();
        if (EmptyUtil.isEmpty(rootHangYe)) {
            return;
        }
        List<RootHangYe> industrycatelist = rootHangYe.getIndustrycatelist();
        if (EmptyUtil.isEmpty(industrycatelist)) {
            return;
        }

        provinceBeans = industrycatelist;
        mFirstAdpater = new SimpleTextAdapter<RootHangYe>(provinceBeans, mContext) {//区适配
            @Override
            public String provideText(RootHangYe provinceBean) {
                return provinceBean.getName();
            }

            @Override
            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                checkedTextView.setPadding(DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10));

            }
        };


        mSecondAdapter = new SimpleTextAdapter<ChildHangYe>(null, mContext) {//市 适配
            @Override
            public String provideText(ChildHangYe cityBean) {
                return cityBean.getName();
            }

            @Override
            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                checkedTextView.setPadding(DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10));
                checkedTextView.setBackgroundResource(android.R.color.white);
            }
        };


        mLvFirst.setAdapter(mFirstAdpater);
        mLvSecond.setAdapter(mSecondAdapter);

        //设置监听
        mLvFirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                currentProvinceBean = provinceBeans.get(position);
                selectCityBeans = currentProvinceBean.getChild();
                mSecondAdapter.setList(selectCityBeans);

            }
        });


        mLvSecond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ChildHangYe cityBean = selectCityBeans.get(position);
//                startActivity(new Intent(mContext, CommunicationMeetingForeshowActivity.class));

                int intExtra = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
                if (intExtra == 1) {
                    Intent intent = new Intent(mContext, BasicInformationActivity.class);
                    intent.putExtra(Config.INTENT_PARAMS1, currentProvinceBean);
                    intent.putExtra(Config.INTENT_PARAMS2, cityBean);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent(mContext, CommunicationMeetingForeshowActivity.class);
                    intent.putExtra(Config.INTENT_PARAMS1, 0);
                    intent.putExtra(Config.INTENT_PARAMS2, cityBean.getId());
                    startActivity(intent);
                }
            }
        });


        //默认第一个
        mLvFirst.setItemChecked(0, true);
    }

}
