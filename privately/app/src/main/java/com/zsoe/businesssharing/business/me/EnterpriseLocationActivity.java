package com.zsoe.businesssharing.business.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.bean.JsonBean;
import com.zsoe.businesssharing.commonview.wheelview.builder.OptionsPickerBuilder;
import com.zsoe.businesssharing.commonview.wheelview.listener.OnOptionsSelectListener;
import com.zsoe.businesssharing.commonview.wheelview.view.OptionsPickerView;
import com.zsoe.businesssharing.utils.StrUtils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class EnterpriseLocationActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 北京-北京市-东城区
     */
    private TextView mTvShengshiqu;
    /**
     * 请输入企业详细地址
     */
    private EditText mTvJuti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprise_location);
        initView();

        initTitleText("企业所在地");

        setTitleRightText("保存", new Action1<View>() {
            @Override
            public void call(View view) {
                String s = mTvJuti.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    ToastUtils.showShort("请填写具体地址");
                    return;
                }


                String s1 = mTvShengshiqu.getText().toString();
                if (TextUtils.isEmpty(s1) || s1.equals(getString(R.string.qiye_dizhi))) {
                    ToastUtils.showShort(getString(R.string.qiye_dizhi));
                    return;
                }


            }
        });

        initData();
    }

    private void initView() {
        mTvShengshiqu = (TextView) findViewById(R.id.tv_shengshiqu);
        mTvShengshiqu.setOnClickListener(this);
        mTvJuti = (EditText) findViewById(R.id.tv_juti);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_shengshiqu:
                KeyboardUtils.hideSoftInput(this);
                showPickerView();
                break;
        }
    }

    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";


                String address = opt1tx + "," + opt2tx + "," + opt3tx;
                mTvShengshiqu.setText(address);

            }
        })

                .setTitleText("请选择区域")
                .setDividerColor(getResources().getColor(R.color.line))
                .setTextColorCenter(getResources().getColor(R.color.text_3)) //设置选中项文字颜色
                .setContentTextSize(17)

                .setCancelColor(getResources().getColor(R.color.text_9))
                .setSubmitColor(getResources().getColor(R.color.AC9472))
                .setSubmitText("确认")
                .setSubCalSize(15)
                .setTitleSize(15)
                .setLineSpacingMultiplier(3f)
                .setTitleColor(getResources().getColor(R.color.text_3))
                .setTitleBgColor(getResources().getColor(R.color.white))
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 子线程中解析省市区数据
                initJsonData();
            }
        }).start();
    }


    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String myjson = StrUtils.getJson(mContext, "province.json");
        List<JsonBean> jsonBean = (List<JsonBean>) new Gson().fromJson(myjson,
                new TypeToken<List<JsonBean>>() {
                }.getType());


        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        isLoaded = true;

    }

    private boolean isLoaded = false;

}
