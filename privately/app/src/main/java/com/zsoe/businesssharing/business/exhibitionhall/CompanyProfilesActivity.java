package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.RootResponse;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.HttpResponseFunc;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.CompanyInfo;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;
import com.zsoe.businesssharing.utils.GlideUtils;
import com.zsoe.businesssharing.utils.android.schedulers.AndroidSchedulers;

import java.util.HashMap;
import java.util.List;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import okhttp3.FormBody;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@RequiresPresenter(CompanyInfoPresenter.class)
public class CompanyProfilesActivity extends BaseActivity<CompanyInfoPresenter> implements View.OnClickListener {

    private SimpleDraweeView mCompanyImage;
    private TextView mTvMasterName;
    private TextView mTvMasterZhiwei;
    private JzvdStd mJzVideo;
    private RecyclerView mRvPic;
    /**
     * 产品列表
     */
    private TextView mTvPrdList;
    private ExpandableTextView mTvChenguo;
    /**
     * 请输入姓名
     */
    private EditText mEtName;
    /**
     * 请输入号码
     */
    private EditText mEtPhone;
    /**
     * 请输入要留言的内容
     */
    private EditText mEtContent;
    /**
     * 提交
     */
    private Button mBtnLogin;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profiles);
        initView();

        initTitleText("企业简介");
        id = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        DialogManager.getInstance().showNetLoadingView(this);
        getPresenter().company_info("" + id);
    }

    private void initView() {
        mCompanyImage = (SimpleDraweeView) findViewById(R.id.company_image);
        mTvMasterName = (TextView) findViewById(R.id.tv_master_name);
        mTvMasterZhiwei = (TextView) findViewById(R.id.tv_master_zhiwei);
        mJzVideo = (JzvdStd) findViewById(R.id.jz_video);
        mRvPic = (RecyclerView) findViewById(R.id.rv_pic);
        mTvPrdList = (TextView) findViewById(R.id.tv_prd_list);
        mTvPrdList.setOnClickListener(this);
        mTvChenguo = (ExpandableTextView) findViewById(R.id.tv_chenguo);
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
    }

    CompanyInfo companyInfo;

    public void setData(CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
        initRightIcon();
        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams params = mJzVideo.getLayoutParams();
        // 宽度为屏幕宽度
        params.width = getResources().getDisplayMetrics().widthPixels;
        // 高度为宽度的9/16
        params.height = (int) (params.width * 9f / 16f);
        mJzVideo.setLayoutParams(params);

        JZDataSource jzDataSource = new JZDataSource(companyInfo.getVideourl());
        mJzVideo.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL);

        mJzVideo.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        GlideUtils.loadImage(mContext, companyInfo.getVideocoverurl(), mJzVideo.thumbImageView);


        FrecoFactory.getInstance().disPlay(mCompanyImage, companyInfo.getAvatar());

        mTvMasterName.setText(companyInfo.getName());
        mTvMasterZhiwei.setText(companyInfo.getCompanydes());

        List<String> photos = companyInfo.getPhotos();

        OnionRecycleAdapter jiazhiAdapter = new OnionRecycleAdapter<String>(R.layout.item_xuanchuanpian_layout, photos) {
            @Override
            protected void convert(BaseViewHolder holder, final String item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item);
            }
        };

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        mRvPic.setFocusableInTouchMode(false);
        mRvPic.setNestedScrollingEnabled(false);
        mRvPic.setLayoutManager(linearLayoutManager2);// 布局管理器。
        mRvPic.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvPic.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvPic.setAdapter(jiazhiAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_prd_list:
                Intent intent = new Intent(mContext, ProductListActivity.class);
                intent.putExtra(Config.INTENT_PARAMS3,companyInfo.getId()+"");
                startActivity(intent);
                break;
            case R.id.btn_login:

                String s = mEtName.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    ToastUtils.showShort("请输入姓名");
                    return;
                }

                String s2 = mEtPhone.getText().toString();
                if (TextUtils.isEmpty(s2)) {
                    ToastUtils.showShort("请输入手机号");
                    return;
                }

                String s3 = mEtContent.getText().toString();
                if (TextUtils.isEmpty(s3)) {
                    ToastUtils.showShort("请输入内容");
                    return;
                }

                if (null == companyInfo) {
                    ToastUtils.showShort("公司数据为空");
                    return;
                }

                DialogManager.getInstance().showNetLoadingView(mContext);
                getPresenter().save_mailbox_msg(s, s2, companyInfo.getUid() + "", DApplication.getInstance().getLoginUser().getId() + "", s3);
                break;
        }
    }


    private void initRightIcon() {
        if (companyInfo.getIs_collect() == 1) {
            setTitleRigthIcon(R.mipmap.shoucang, new Action1<View>() {
                @Override
                public void call(View view) {
                    collect(companyInfo.getId() + "", "2");
                }
            });
        } else {
            setTitleRigthIcon(R.mipmap.shoucang_pre, new Action1<View>() {
                @Override
                public void call(View view) {
                    collect(companyInfo.getId() + "", "1");
                }
            });
        }
    }


    public void collect(String valueid, String acttype) {

        DialogManager.getInstance().showNetLoadingView(mContext);

        HashMap<String, String> params = new HashMap<>();
        params.put("uid", DApplication.getInstance().getLoginUser().getId() + "");
        params.put("type", "1");
        params.put("acttype", acttype);
        params.put("valueid", valueid);


        FormBody formBody = getPresenter().signForm(params);
        DApplication.getInstance().getServerAPI().collect(formBody).map(new HttpResponseFunc())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber() {

            @Override
            public void onNext(Object o) {
                DialogManager.getInstance().dismissNetLoadingView();
                //1收藏 2 取消收藏
                if (acttype.equals("1")) {
                    companyInfo.setIs_collect(1);
                } else {
                    companyInfo.setIs_collect(0);
                }
                initRightIcon();
            }

            @Override
            public void onCompleted() {
                DialogManager.getInstance().dismissNetLoadingView();
            }

            @Override
            public void onError(Throwable e) {
                DialogManager.getInstance().dismissNetLoadingView();

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    public void setMsgSuccess(RootResponse t) {
        ToastUtils.showShort(t.getMsg());
    }
}
