package com.zsoe.businesssharing.business.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.blankj.utilcode.util.KeyboardUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.permission.Permission;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.bean.JsonBean;
import com.zsoe.businesssharing.business.exhibitionhall.IndustryClassificationActivity;
import com.zsoe.businesssharing.commonview.PhotoTypePopup;
import com.zsoe.businesssharing.commonview.wheelview.builder.OptionsPickerBuilder;
import com.zsoe.businesssharing.commonview.wheelview.builder.TimePickerBuilder;
import com.zsoe.businesssharing.commonview.wheelview.listener.CustomListener;
import com.zsoe.businesssharing.commonview.wheelview.listener.OnOptionsSelectListener;
import com.zsoe.businesssharing.commonview.wheelview.listener.OnTimeSelectListener;
import com.zsoe.businesssharing.commonview.wheelview.view.OptionsPickerView;
import com.zsoe.businesssharing.commonview.wheelview.view.TimePickerView;
import com.zsoe.businesssharing.utils.DateUtil;
import com.zsoe.businesssharing.utils.StrUtils;
import com.zsoe.businesssharing.utils.permission.OpenPermission2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BasicInformationActivity extends BaseActivity implements View.OnClickListener {

    private SimpleDraweeView mUserImage;
    /**
     * 取一个好的昵称吧
     */
    private AppCompatEditText mEdNicheng;
    /**
     * 请选择性别
     */
    private TextView mTvXingbie;
    private RelativeLayout mRlXingbie;
    /**
     * 请选择年龄
     */
    private TextView mTvNianling;
    private RelativeLayout mRlNianling;
    /**
     * 请输入手机号
     */
    private AppCompatEditText mEdShoujihao;
    /**
     * 未绑定
     */
    private AppCompatEditText mEdYouxiang;
    /**
     * 请选择地区
     */
    private TextView mTvDiqu;
    private RelativeLayout mRlDiqu;
    /**
     * 请选择服务站
     */
    private TextView mTvFuwuzhan;
    private RelativeLayout mRlFuwuzhan;
    /**
     * 请选择企业所在地
     */
    private TextView mTvSuozaidi;
    private RelativeLayout mRlSuozaidi;
    /**
     * 请选择企业所属行业
     */
    private TextView mTvHangye;
    private RelativeLayout mRlHangye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information);
        initView();
        initTitleText("基本信息");

        initData();
        xingBieList.add("男");
        xingBieList.add("女");
    }

    private void initView() {
        mUserImage = (SimpleDraweeView) findViewById(R.id.user_image);
        mUserImage.setOnClickListener(this);
        mEdNicheng = (AppCompatEditText) findViewById(R.id.ed_nicheng);
        mTvXingbie = (TextView) findViewById(R.id.tv_xingbie);
        mRlXingbie = (RelativeLayout) findViewById(R.id.rl_xingbie);
        mRlXingbie.setOnClickListener(this);
        mTvNianling = (TextView) findViewById(R.id.tv_nianling);
        mRlNianling = (RelativeLayout) findViewById(R.id.rl_nianling);
        mRlNianling.setOnClickListener(this);
        mEdShoujihao = (AppCompatEditText) findViewById(R.id.ed_shoujihao);
        mEdYouxiang = (AppCompatEditText) findViewById(R.id.ed_youxiang);
        mTvDiqu = (TextView) findViewById(R.id.tv_diqu);
        mRlDiqu = (RelativeLayout) findViewById(R.id.rl_diqu);
        mRlDiqu.setOnClickListener(this);
        mTvFuwuzhan = (TextView) findViewById(R.id.tv_fuwuzhan);
        mRlFuwuzhan = (RelativeLayout) findViewById(R.id.rl_fuwuzhan);
        mRlFuwuzhan.setOnClickListener(this);
        mTvSuozaidi = (TextView) findViewById(R.id.tv_suozaidi);
        mRlSuozaidi = (RelativeLayout) findViewById(R.id.rl_suozaidi);
        mRlSuozaidi.setOnClickListener(this);
        mTvHangye = (TextView) findViewById(R.id.tv_hangye);
        mRlHangye = (RelativeLayout) findViewById(R.id.rl_hangye);
        mRlHangye.setOnClickListener(this);
    }


    OptionsPickerView xingBiePic;
    ArrayList<String> xingBieList = new ArrayList<>();


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.user_image:

                PhotoTypePopup.create(mContext, new PhotoTypePopup.OperClickListener() {
                    @Override
                    public void takePhoto() {

                        //启动拍照前检查是否有权限
                        new OpenPermission2(mContext).requestPermission(new OpenPermission2.OnPermissionListener() {
                            @Override
                            public void onPermissionSuccess() {
                                //拍照
//                                takePicture(getActivity(), Config.REQUEST_CODE_TOKEPHOTO);
                            }
                        }, false, Permission.Group.CAMERA);

                    }

                    @Override
                    public void gallery() {

                        //选择图片
//                        ImagePicker.getInstance().setShowCamera(false);
//                        DApplication.getInstance().changePickerModeAndClear(false, 1);
//                        ImagePicker.getInstance().setCrop(true);
//                        Intent intent = new Intent(mContext, ImageGridActivity.class);
//                        startActivityForResult(intent, Config.IMAGE_PICKER);

                    }
                }).setDimView((ViewGroup) getWindow().getDecorView()).apply().showAtLocation(v, Gravity.BOTTOM, 0, 0);

                break;
            case R.id.rl_xingbie:
                KeyboardUtils.hideSoftInput(this);

                if (null == xingBiePic) {
                    //条件选择器
                    xingBiePic = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                            //返回的分别是三个级别的选中位置
                            String tx = xingBieList.get(options1);
                            mTvXingbie.setText(tx);
                        }
                    }).setTitleText("请选择性别")
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
                    xingBiePic.setPicker(xingBieList);

                }
                xingBiePic.show();
                break;
            case R.id.rl_nianling:
                KeyboardUtils.hideSoftInput(this);
                if (null == startDatePicker) {
                    initStartDatePicker();
                }
                startDatePicker.show();

                break;
            case R.id.rl_diqu:
                KeyboardUtils.hideSoftInput(this);
                showPickerView();
                break;
            case R.id.rl_fuwuzhan:
                break;
            case R.id.rl_suozaidi:
                startActivity(new Intent(mContext, EnterpriseLocationActivity.class));
                break;
            case R.id.rl_hangye:
                startActivity(new Intent(mContext, IndustryClassificationActivity.class));

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
                mTvDiqu.setText(address);

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


    TimePickerView startDatePicker;//开始日期picker

    /**
     * 开始日期选择
     */
    private void initStartDatePicker() {


        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        //selectedDate.get(Calendar.YEAR) = 2019

        int endYear = selectedDate.get(Calendar.YEAR) - 18;
        int startYear = selectedDate.get(Calendar.YEAR) - 70;

        //startYear = 1949
        //endYear = 2001

        startDate.set(startYear, 0, 1);
        endDate.set(endYear, 11, 31);

        //时间选择器 ，自定义布局
        startDatePicker = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String startDateStr = DateUtil.getYyyyMmDd(date);
                mTvNianling.setText(startDateStr);
            }
        })
                .setDate(startDate)
                .setRangDate(startDate, endDate)

                .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {

                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = (TextView) v.findViewById(R.id.iv_cancel);
                        TextView cb_lunar = (TextView) v.findViewById(R.id.cb_lunar);
                        cb_lunar.setText("请选择日期");

                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startDatePicker.returnData();
                                startDatePicker.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startDatePicker.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setDividerColor(getResources().getColor(R.color.line))
                .setTextColorCenter(getResources().getColor(R.color.text_3)) //设置选中项文字颜色
                .setContentTextSize(17)
                .setCancelColor(getResources().getColor(R.color.text_9))
                .setSubmitColor(getResources().getColor(R.color.AC9472))
                .setSubCalSize(15)
                .setTitleSize(15)
                .setLineSpacingMultiplier(3f)
                .setTitleColor(getResources().getColor(R.color.text_3))
                .setTitleBgColor(getResources().getColor(R.color.white))
                .build();
    }

}
