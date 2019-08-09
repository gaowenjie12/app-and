package com.zsoe.businesssharing.business.me;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yanzhenjie.permission.Permission;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.commonview.PhotoTypePopup;
import com.zsoe.businesssharing.utils.permission.OpenPermission2;

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
                break;
            case R.id.rl_nianling:
                break;
            case R.id.rl_diqu:
                break;
            case R.id.rl_fuwuzhan:
                break;
            case R.id.rl_suozaidi:
                break;
            case R.id.rl_hangye:
                break;
        }
    }
}
