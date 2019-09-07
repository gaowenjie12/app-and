package com.zsoe.businesssharing.business.attention;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.FenHuiBean;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.commonview.banner.BannerView;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.FrecoFactory;

import rx.functions.Action1;

/**
 * 分会介绍
 */
@RequiresPresenter(ChapterIntroductionPresenter.class)
public class ChapterIntroductionFragment extends BaseFragment<ChapterIntroductionPresenter> {

    private static final String TAG = "HomeFragment";

    public static ChapterIntroductionFragment newInstance(String title) {
        ChapterIntroductionFragment f = new ChapterIntroductionFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        f.setArguments(args);
        return f;
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
    }

    @Override
    protected int createViewByLayoutId() {
        return R.layout.fragment_chapter_introduction;
    }

    private BannerView bannerView;
    private RecyclerView rv_pic;
    private ExpandableTextView tv_chenguo;

    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bannerView = view.findViewById(R.id.banner_view);
        rv_pic = view.findViewById(R.id.rv_pic);
        tv_chenguo = view.findViewById(R.id.tv_chenguo);


        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().attentionIndex();
            }
        });

        //左右滑动时刷新控件禁止掉
        mPtrFrame.disableWhenHorizontalMove(true);

        mPtrFrame.autoRefresh();
    }

    public void setDate(FenHuiBean fenHuiBean) {
        mPtrFrame.refreshComplete();

        bannerView.setDate(fenHuiBean.getSlide());

        OnionRecycleAdapter noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<String>(R.layout.item_fenhui_layout, fenHuiBean.getPhotos()) {
            @Override
            protected void convert(BaseViewHolder holder, final String item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item);


            }
        };


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        //调整RecyclerView的排列方向
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rv_pic.setLayoutManager(layoutManager);// 布局管理器。
        rv_pic.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        rv_pic.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        rv_pic.setAdapter(noticeBeanOnionRecycleAdapter);


        tv_chenguo.setText(fenHuiBean.getDes());


    }
}
