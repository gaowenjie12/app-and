package com.zsoe.businesssharing.business.attention;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.bean.BannerItemBean;
import com.zsoe.businesssharing.commonview.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * 架构文化
 */

public class ArchitecturalCultureFragment extends BaseFragment {

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

            }
        });

        //左右滑动时刷新控件禁止掉
        mPtrFrame.disableWhenHorizontalMove(true);

        setDate();

    }

    public void setDate() {
        List<BannerItemBean> bannerItemBeans = new ArrayList<>();

        BannerItemBean bannerItemBean = new BannerItemBean();
        bannerItemBean.setImg("http://hbimg.b0.upaiyun.com/3e14d836d89498b116834b2987dbaa1c8f2e85a418a9fc-nLVWsW_fw658");
        bannerItemBeans.add(bannerItemBean);

        BannerItemBean bannerItemBean2 = new BannerItemBean();
        bannerItemBean2.setImg("http://hbimg.b0.upaiyun.com/9be8e0054e2ed5e02fa91c6c66267f9d51859e951b83e-qMhDYE_fw658");
        bannerItemBeans.add(bannerItemBean2);

        BannerItemBean bannerItemBean3 = new BannerItemBean();
        bannerItemBean3.setImg("http://img694.ph.126.net/2CR9OPpnSjmHa_7BzGVE9Q==/2868511487659481204.jpg");
        bannerItemBeans.add(bannerItemBean3);

        BannerItemBean bannerItemBean4 = new BannerItemBean();
        bannerItemBean4.setImg("http://i1.hdslb.com/bfs/archive/20b81aa9dcffd6db03dc14296ff3b84874f0c529.png");
        bannerItemBeans.add(bannerItemBean4);


//        bannerView.setDate(bannerItemBeans);

        tv_content.setText("智能家居是在互联网影响之下物联化的体现。\n" +
                "智能家居通过物联网技术将家中的各种设备连接到一起，提供家电控制、照明控制、电话远程控制、室内外遥控、防盗报警、环境监测、暖通控制、红外转发以及可编程定时控制等多种功能和手段。\n" +
                "与普通家居相比，智能家居不仅具有传统的居住功能，兼备建筑、网络通信、信息家电、设备自动化，提供全方...");


    }
}
