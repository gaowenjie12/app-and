package com.zsoe.businesssharing.business.attention;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.FenHuiBean;
import com.zsoe.businesssharing.commonview.banner.BannerView;

import rx.functions.Action1;

/**
 * 架构文化
 */
@RequiresPresenter(ArchitecturaCulturePresenter.class)
public class ArchitecturalCultureFragment extends BaseFragment<ArchitecturaCulturePresenter> {

    private static final String TAG = "HomeFragment";

    public static ArchitecturalCultureFragment newInstance(String title) {
        ArchitecturalCultureFragment f = new ArchitecturalCultureFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        f.setArguments(args);
        return f;
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
    }


    private BannerView bannerView;
    private TextView tv_content;


    @Override
    protected int createViewByLayoutId() {
        return R.layout.fragment_architectural_culture;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bannerView = view.findViewById(R.id.banner_view);
        tv_content = view.findViewById(R.id.tv_content);



        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().attentionFrame();
            }
        });

        //左右滑动时刷新控件禁止掉
        mPtrFrame.disableWhenHorizontalMove(true);

        mPtrFrame.autoRefresh();
    }

    public void setDate(FenHuiBean FenHuiBean) {
        mPtrFrame.refreshComplete();

        bannerView.setDate(FenHuiBean.getSlide());

        tv_content.setText(FenHuiBean.getDes());


    }
}
