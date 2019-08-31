package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.CompanyInfo;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;
import com.zsoe.businesssharing.utils.GlideUtils;

import java.util.List;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profiles);
        initView();

        initTitleText("企业简介");
        int id = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
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


    public void setData(CompanyInfo companyInfo) {
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
                FrecoFactory.getInstance().disPlay(simpleDraweeView,item);
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
                startActivity(new Intent(mContext, ProductListActivity.class));
                break;
            case R.id.btn_login:
                break;
        }
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
}
