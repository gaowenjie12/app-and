package com.zsoe.businesssharing.business.home;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.utils.GlideUtils;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class FinancingLoansDetailActivity extends BaseActivity implements View.OnClickListener {

    private JzvdStd mJzVideo;
    private FrameLayout mFlVideo;
    private TextView mTvTitle;
    private TextView mTvCompanyName;
    private ExpandableTextView mTvJieshao;
    /**
     * 提交
     */
    private Button mBtnLogin;
    /**
     * 请输入姓名
     */
    private EditText mEtName;
    /**
     * 请输入号码
     */
    private EditText mEtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_loans_detail);
        initView();

        initTitleText("融资详情");
        setDate();
    }

    public void setDate() {
        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams params = mJzVideo.getLayoutParams();
        // 宽度为屏幕宽度
        params.width = getResources().getDisplayMetrics().widthPixels;
        // 高度为宽度的9/16
        params.height = (int) (params.width * 9f / 16f);
        mJzVideo.setLayoutParams(params);

        JZDataSource jzDataSource = new JZDataSource("http://v.cdn.sohu.com/93/7a28fd59b399ae79ea441253504a278a/0");
        mJzVideo.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL);

        mJzVideo.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        GlideUtils.loadImage(mContext, "http://p.cdn.sohu.com/089a1b13/5b4098facc8075a2012419c8793270bc", mJzVideo.thumbImageView);


        mTvTitle.setText("王者荣耀：国服诸葛亮极限手速一闪大招二技能连招，视觉盛宴");
        mTvCompanyName.setText("北京智能科技有限公司");
        mTvJieshao.setText("在终端入口方面，华为拥有竞争对手无法比拟的优势，此前华为提出的“1+8+N” 全场景智慧服务战略口号是一个非常明显的例证。" +
                "1是手机终端为主要入口;8是包括PC、平板、智慧大屏、车机在内容的4个大屏入口，加上耳机、音箱、手表，" +
                "眼镜在内4个非大屏入口;N是指泛IoT硬件构成的华为HiLink生态。" +
                "颇有远见的产品布局，让华为在近几年以肉眼可见的速度形成了自己的生态壁垒。近两年，华为一直不断对外发出发展全场景生态战略的信号，" +
                "生态系统的建立又显然不是一朝一夕可以完成，需要更多合作伙伴的加入。从近来华为的开发姿态和此次开发者大会释放的诸多信号，" +
                "华为正在朝着既定的目标努力迈进");
    }

    private void initView() {
        mJzVideo = (JzvdStd) findViewById(R.id.jz_video);
        mFlVideo = (FrameLayout) findViewById(R.id.fl_video);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvCompanyName = (TextView) findViewById(R.id.tv_company_name);
        mTvJieshao = (ExpandableTextView) findViewById(R.id.tv_jieshao);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
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
