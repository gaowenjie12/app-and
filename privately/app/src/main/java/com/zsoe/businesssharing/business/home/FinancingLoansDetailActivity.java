package com.zsoe.businesssharing.business.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.DetailFinanceBean;
import com.zsoe.businesssharing.bean.FileDownBean;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.GlideUtils;
import com.zsoe.businesssharing.utils.download.DownloadInfo;
import com.zsoe.businesssharing.utils.download.DownloadManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.jzvd.JZDataSource;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


@RequiresPresenter(DetailFinacPresenter.class)
public class FinancingLoansDetailActivity extends BaseActivity<DetailFinacPresenter> implements View.OnClickListener {

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
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_loans_detail);
        initView();
        EventBus.getDefault().register(this);

        initTitleText("融资详情");


        id = getIntent().getIntExtra(Config.INTENT_PARAMS1, -1);
        DialogManager.getInstance().showNetLoadingView(mContext);
        getPresenter().finance_info(id + "");
    }

    public void setDate(DetailFinanceBean detailFinanceBean) {
        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams params = mJzVideo.getLayoutParams();
        // 宽度为屏幕宽度
        params.width = getResources().getDisplayMetrics().widthPixels;
        // 高度为宽度的9/16
        params.height = (int) (params.width * 9f / 16f);
        mJzVideo.setLayoutParams(params);

        JZDataSource jzDataSource = new JZDataSource(detailFinanceBean.getVideourl());
        mJzVideo.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL);

        mJzVideo.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        GlideUtils.loadImage(mContext, detailFinanceBean.getVideocoverurl(), mJzVideo.thumbImageView);

        mTvTitle.setText(detailFinanceBean.getTitle());
        mTvCompanyName.setText(detailFinanceBean.getCompanyname());
        mTvJieshao.setText(detailFinanceBean.getContent());
    }

    public void getFile(FileDownBean fileDownBean) {
        DownloadManager.getInstance().download(fileDownBean.getFiledata_url());
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

                String account = mEtName.getText().toString();
                if (TextUtils.isEmpty(account)) {
                    ToastUtils.showShort("请填写姓名");
                    return;
                }
                String phone = mEtPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort("请填写手机号");
                    return;
                }


                DialogManager.getInstance().showNetLoadingView(mContext);
                getPresenter().finance_file_download(id + "", account, phone);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(DownloadInfo info) {

        if (DownloadInfo.DOWNLOAD.equals(info.getDownloadStatus())) {


        } else if (DownloadInfo.DOWNLOAD_OVER.equals(info.getDownloadStatus())) {

            Toast.makeText(this, "下载资料成功,请前往文件管理AAA文件夹查看", Toast.LENGTH_SHORT).show();

        } else if (DownloadInfo.DOWNLOAD_PAUSE.equals(info.getDownloadStatus())) {

            Toast.makeText(this, "下载暂停", Toast.LENGTH_SHORT).show();

        } else if (DownloadInfo.DOWNLOAD_CANCEL.equals(info.getDownloadStatus())) {

            Toast.makeText(this, "下载取消", Toast.LENGTH_SHORT).show();

        } else if (DownloadInfo.DOWNLOAD_ERROR.equals(info.getDownloadStatus())) {

            Toast.makeText(this, "下载出错", Toast.LENGTH_SHORT).show();

        }
    }
}
