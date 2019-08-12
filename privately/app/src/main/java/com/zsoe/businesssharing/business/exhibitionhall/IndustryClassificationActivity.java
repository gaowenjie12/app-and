package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.bean.CityBean;
import com.zsoe.businesssharing.bean.ProvinceBean;
import com.zsoe.businesssharing.commonview.dropfilter.adapter.BaseBaseAdapter;
import com.zsoe.businesssharing.commonview.dropfilter.adapter.SimpleTextAdapter;
import com.zsoe.businesssharing.commonview.dropfilter.util.DpUtils;
import com.zsoe.businesssharing.commonview.dropfilter.view.FilterCheckedTextView;

import java.util.ArrayList;
import java.util.List;

public class IndustryClassificationActivity extends BaseActivity {

    private ListView mLvFirst;
    private ListView mLvSecond;

    private BaseBaseAdapter<ProvinceBean> mFirstAdpater;
    private BaseBaseAdapter<CityBean> mSecondAdapter;

    private List<ProvinceBean> provinceBeans;//省
    private List<CityBean> selectCityBeans;

    private ProvinceBean currentProvinceBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry_classification);
        initView();

        initTitleText("全部行业");

        provinceBeans = new ArrayList<>();
        List<CityBean> cityBeans = new ArrayList<>();

        //市数据
        for (int i = 0; i < 26; i++) {
            CityBean bean = new CityBean();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");
                bean.setPid("没用String");

                cityBeans.add(bean);
            } else {
                bean.setId(i);
                bean.setName("市" + i);
                bean.setPid("没用String");
                cityBeans.add(bean);
            }
        }

        //省数据
        for (int i = 0; i < 36; i++) {
            ProvinceBean bean = new ProvinceBean();
            if (i == 0) {
                bean.setId(i);
                bean.setName("不限");
                bean.setPid("没用String");

                provinceBeans.add(bean);

                //省份添加市数据
                bean.setChild((ArrayList<CityBean>) cityBeans);
            } else {
                bean.setId(i);
                bean.setName("省" + i);
                bean.setPid("没用String");

                provinceBeans.add(bean);
                //省份添加市数据
                bean.setChild((ArrayList<CityBean>) cityBeans);
            }
        }

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

        mFirstAdpater = new SimpleTextAdapter<ProvinceBean>(provinceBeans, mContext) {//区适配
            @Override
            public String provideText(ProvinceBean provinceBean) {
                return provinceBean.getName();
            }

            @Override
            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                checkedTextView.setPadding(DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10), DpUtils.dpToPx(mContext, 10));

            }
        };


        mSecondAdapter = new SimpleTextAdapter<CityBean>(null, mContext) {//市 适配
            @Override
            public String provideText(CityBean cityBean) {
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
                CityBean cityBean = selectCityBeans.get(position);
                startActivity(new Intent(mContext, CommunicationMeetingForeshowActivity.class));
            }
        });


        //默认第一个
        mLvFirst.setItemChecked(0, true);
    }

}
