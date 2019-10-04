package com.zsoe.businesssharing.business.me;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.ServerAPI;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.CallBean;
import com.zsoe.businesssharing.bean.RenCompanyBean;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;
import com.zsoe.businesssharing.utils.GlideUtils;
import com.zsoe.businesssharing.utils.ImageCompressUtils;
import com.zsoe.businesssharing.utils.UpLoadFileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

@RequiresPresenter(RenzhengPresenter.class)
public class CertificationCompanyActivity extends BaseActivity<RenzhengPresenter> implements View.OnClickListener {

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

    private Button btn_login;

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
        DialogManager.getInstance().showNetLoadingView(mContext);
        getPresenter().auth_companyinfo(DApplication.getInstance().getLoginUser().getId() + "");
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
        btn_login = (Button) findViewById(R.id.btn_login);

        mIvYingyeImg = (SimpleDraweeView) findViewById(R.id.iv_yingye_img);
        mYingyeDeleteBtn = (ImageView) findViewById(R.id.yingye_delete_btn);
        mIvJiehunImg = (SimpleDraweeView) findViewById(R.id.iv_jiehun_img);
        mJiehunDeleteBtn = (ImageView) findViewById(R.id.jiehun_delete_btn);
        mIvYingyeImg.setOnClickListener(this);
        mYingyeDeleteBtn.setOnClickListener(this);
        mIvJiehunImg.setOnClickListener(this);
        mJiehunDeleteBtn.setOnClickListener(this);
        btn_login.setOnClickListener(this);

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
                yingyePath = "";

                break;
            case R.id.iv_jiehun_img:
                toPic(REQUEST_LIST_CODE2);
                break;
            case R.id.jiehun_delete_btn:
                mIvJiehunImg.setImageResource(R.mipmap.shangchuantupian);
                mJiehunDeleteBtn.setVisibility(View.GONE);
                jiehunPath = "";
                break;
            case R.id.btn_login:

                String mEtQuanchengStr = mEtQuancheng.getText().toString();
                if (TextUtils.isEmpty(mEtQuanchengStr)) {
                    ToastUtils.showShort("请输入企业全称");
                    return;
                }


                String mEtLeimuStr = mEtLeimu.getText().toString();
                if (TextUtils.isEmpty(mEtLeimuStr)) {
                    ToastUtils.showShort("请输入主营类目");
                    return;
                }


                String mEtShenfenzhengStr = mEtShenfenzheng.getText().toString();
                if (TextUtils.isEmpty(mEtShenfenzhengStr)) {
                    ToastUtils.showShort("请输入身份证号");
                    return;
                }


                String mEtFarenStr = mEtFaren.getText().toString();
                if (TextUtils.isEmpty(mEtFarenStr)) {
                    ToastUtils.showShort("请输入法人姓名");
                    return;
                }


                String mEtPhoneStr = mEtPhone.getText().toString();
                if (TextUtils.isEmpty(mEtPhoneStr)) {
                    ToastUtils.showShort("请输入手机号");
                    return;
                }


                String mEtMailStr = mEtMail.getText().toString();
                if (TextUtils.isEmpty(mEtMailStr)) {
                    ToastUtils.showShort("请输入企业邮箱");
                    return;
                }


                if (TextUtils.isEmpty(yingyePath)) {
                    ToastUtils.showShort("请上传营业执照");
                    return;
                }


                if (TextUtils.isEmpty(jiehunPath)) {
                    ToastUtils.showShort("请上结婚证书");
                    return;
                }


                DialogManager.getInstance().showNetLoadingView(mContext);
                getPresenter().auth_company(DApplication.getInstance().getLoginUser().getId() + "",
                        mEtQuanchengStr, mEtLeimuStr, mEtShenfenzhengStr, mEtFarenStr, mEtPhoneStr, mEtMailStr, yingyePath, jiehunPath
                );

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


            final String path = pathList.get(0);
            ArrayList<String> arrayList = new ArrayList<>();
            if (!TextUtils.isEmpty(path)) {
                arrayList.add(path);
            }
            sendImg(1, arrayList);
        }

        if (requestCode == REQUEST_LIST_CODE2 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            // 测试Fresco
            mIvJiehunImg.setImageURI(Uri.parse("file://" + pathList.get(0)));
            mJiehunDeleteBtn.setVisibility(View.VISIBLE);

            final String path = pathList.get(0);
            ArrayList<String> arrayList = new ArrayList<>();
            if (!TextUtils.isEmpty(path)) {
                arrayList.add(path);
            }
            sendImg(2, arrayList);
        }

    }

    public void getSuccess(RenCompanyBean renCompanyBean) {

        if (null == renCompanyBean) {
            return;
        }
        mEtQuancheng.setText(renCompanyBean.getCompanyname());
        mEtLeimu.setText(renCompanyBean.getMaincate());
        mEtShenfenzheng.setText(renCompanyBean.getIdcardnum());
        mEtFaren.setText(renCompanyBean.getOwner());
        mEtMail.setText(renCompanyBean.getEmail());

        mEtPhone.setText(renCompanyBean.getMobile());
        if (!TextUtils.isEmpty(renCompanyBean.getLicense())) {
            FrecoFactory.getInstance().disPlay(mIvYingyeImg, renCompanyBean.getLicense());
        }

        if (!TextUtils.isEmpty(renCompanyBean.getMarrlicense())) {
            FrecoFactory.getInstance().disPlay(mIvJiehunImg, renCompanyBean.getMarrlicense());
        }
    }


    private String yingyePath, jiehunPath;

    public void sendImg(int type, ArrayList<String> arrayList) {
        final HashMap<String, String> map = new HashMap<>();
        ImageCompressUtils.compress(arrayList, new ImageCompressUtils.CompressListener() {
            @Override
            public void compress(List<File> fileList) {
                Request filesRequest = UpLoadFileUtils.getFilesRequest("file", ServerAPI.ENDPOINT + "common/upload", fileList, map);

                DApplication.getInstance().okHttpClient.newCall(filesRequest).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                DialogManager.getInstance().dismissNetLoadingView();
                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                DialogManager.getInstance().dismissNetLoadingView();

                                String bodyString = null;
                                try {
                                    bodyString = response.body().string();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                TypeToken<RootResponse<CallBean>> typeToken = new TypeToken<RootResponse<CallBean>>() {
                                };
                                RootResponse<CallBean> rootResponse = new Gson().fromJson(bodyString, typeToken.getType());

                                if (rootResponse.getCode() == 1) {
                                    ToastUtils.showShort(rootResponse.getMsg());
                                    CallBean data = rootResponse.getData();
                                    if (type == 1) {
                                        yingyePath = data.getUrl();
                                    } else {
                                        jiehunPath = data.getUrl();
                                    }
                                }
                            }
                        });
                    }
                });
            }
        });
    }

}
