package com.zsoe.businesssharing.business.me;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.utils.GlideUtils;

import java.util.List;

public class CertificationCompanyActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 请输入企业全称
     */
    private EditText mEtQuancheng;
    /**
     * 请输入主营类目
     */
    private EditText mEtLeimu;
    /**
     * 请输入身份证号
     */
    private EditText mEtShenfenzheng;
    /**
     * 请输入法人姓名
     */
    private EditText mEtFaren;
    /**
     * 请输入手机号码
     */
    private EditText mEtPhone;
    /**
     * 请输入企业邮箱
     */
    private EditText mEtMail;
    private SimpleDraweeView mIvYingyeImg;
    private ImageView mYingyeDeleteBtn;
    private SimpleDraweeView mIvJiehunImg;
    private ImageView mJiehunDeleteBtn;

    private static final int REQUEST_LIST_CODE1 = 0;
    private static final int REQUEST_LIST_CODE2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification_company);
        initView();

        initTitleText("认证公司");

        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
//                Glide.with(context).load(path).into(imageView);
                GlideUtils.loadImage(context, path, imageView);
            }
        });
    }

    private void toPic(int requestionCode) {

        ISListConfig config = new ISListConfig.Builder()
                // 是否多选
                .multiSelect(false)
                .btnText("确定")
                // 确定按钮背景色
                //.btnBgColor(Color.parseColor(""))
                // 确定按钮文字颜色
                .btnTextColor(R.color.white)
                // 使用沉浸式状态栏
//                .statusBarColor(R.color.black)
                // 返回图标ResId
//                .backResId(R.mipmap.back_arrow)
                .title("图片")
                .titleColor(Color.WHITE)
//                .titleBgColor(R.color.black)
                .allImagesText("全部图片")
                .needCrop(false)
                .cropSize(1, 1, 200, 200)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(1)
                .build();

        ISNav.getInstance().toListActivity(this, config, requestionCode);

    }

    private void initView() {
        mEtQuancheng = (EditText) findViewById(R.id.et_quancheng);
        mEtLeimu = (EditText) findViewById(R.id.et_leimu);
        mEtShenfenzheng = (EditText) findViewById(R.id.et_shenfenzheng);
        mEtFaren = (EditText) findViewById(R.id.et_faren);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtMail = (EditText) findViewById(R.id.et_mail);

        mIvYingyeImg = (SimpleDraweeView) findViewById(R.id.iv_yingye_img);
        mYingyeDeleteBtn = (ImageView) findViewById(R.id.yingye_delete_btn);
        mIvJiehunImg = (SimpleDraweeView) findViewById(R.id.iv_jiehun_img);
        mJiehunDeleteBtn = (ImageView) findViewById(R.id.jiehun_delete_btn);
        mIvYingyeImg.setOnClickListener(this);
        mYingyeDeleteBtn.setOnClickListener(this);
        mIvJiehunImg.setOnClickListener(this);
        mJiehunDeleteBtn.setOnClickListener(this);

        mIvYingyeImg.setImageResource(R.mipmap.shangchuantupian);
        mIvJiehunImg.setImageResource(R.mipmap.shangchuantupian);
        mYingyeDeleteBtn.setVisibility(View.GONE);
        mJiehunDeleteBtn.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_yingye_img:
                toPic(REQUEST_LIST_CODE1);
                break;
            case R.id.yingye_delete_btn:
                mIvYingyeImg.setImageResource(R.mipmap.shangchuantupian);
                mYingyeDeleteBtn.setVisibility(View.GONE);

                break;
            case R.id.iv_jiehun_img:
                toPic(REQUEST_LIST_CODE2);
                break;
            case R.id.jiehun_delete_btn:
                mIvJiehunImg.setImageResource(R.mipmap.shangchuantupian);
                mJiehunDeleteBtn.setVisibility(View.GONE);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LIST_CODE1 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            // 测试Fresco
            mIvYingyeImg.setImageURI(Uri.parse("file://" + pathList.get(0)));
            mYingyeDeleteBtn.setVisibility(View.VISIBLE);
        }

        if (requestCode == REQUEST_LIST_CODE2 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            // 测试Fresco
            mIvJiehunImg.setImageURI(Uri.parse("file://" + pathList.get(0)));
            mJiehunDeleteBtn.setVisibility(View.VISIBLE);
        }

    }
}
