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
import com.zsoe.businesssharing.bean.BannerItemBean;
import com.zsoe.businesssharing.commonview.ExpandableTextView;
import com.zsoe.businesssharing.commonview.banner.BannerView;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * 分会介绍
 */

public class ChapterIntroductionFragment extends BaseFragment {

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

        OnionRecycleAdapter noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_fenhui_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getImg());


            }
        };


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        //调整RecyclerView的排列方向
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rv_pic.setLayoutManager(layoutManager);// 布局管理器。
        rv_pic.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        rv_pic.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        rv_pic.setAdapter(noticeBeanOnionRecycleAdapter);


        tv_chenguo.setText("智能家居是在互联网影响之下物联化的体现。\n" +
                "智能家居通过物联网技术将家中的各种设备连接到一起，提供家电控制、照明控制、电话远程控制、室内外遥控、防盗报警、环境监测、暖通控制、红外转发以及可编程定时控制等多种功能和手段。\n" +
                "与普通家居相比，智能家居不仅具有传统的居住功能，兼备建筑、网络通信、信息家电、设备自动化，提供全方...饺子表示，当时父亲去世后家里仅有的收入就是母亲每个月1000块的退休金，他跟母亲住在一起，吃超市特价菜，不买新衣服，不拉网线，尽量不出门。饺子说：“那三年半的时间，我过得跟生活在空间站似的，三点一线：客厅、卧室、厕所。”\n" +
                "不过，虽然《打，打个大西瓜》获得了包括柏林国际短片电影节评委会特别奖等30多个奖项，但饺子没有选择拿投资、拍大片，而是销声匿迹了六年。\n" +
                "彼时国漫环境糟糕，如2008年投入近千万美金打造5年的国产动画《风云决》上映，最终票房只有2400万人民币，投资方损失惨重。这种环境下，很多人对这一行绝望了，饺子便一直靠做外包活着。\n" +
                "直到2014年，光线传媒CEO王长田野心勃勃，成立旗下动画公司“彩条屋”，要做中国的皮克斯。彩条屋CEO易巧看过饺子的作品，他一上任，便专程飞到成都去找饺子。\n" +
                "易巧问饺子“能不能把手上的外包都停掉，把钱退回去，我们一起花三五年干一票大的？”时，饺子几乎没有犹豫就答应了。\n" +
                "于是，哪吒诞生了。\n" +
                "据悉，《哪吒》的剧本他们磨了整整两年。我们如今在电影院里看到的故事，是第66稿。第66稿和第一稿改动超过了50％……\n" +
                "如今回首入行动画17年来的辛酸苦辣，饺子感慨道：“我做这个电影，是想给那些正在追求理想的青年人更多希望与温暖。”\n" +
                "饺子说 “春天要到，需要恒定产出更多好作品。当大家都不再提国漫崛起，都不抱着支持鼓励的心态，国漫已经变成大家的生活必需品了，那个时候，国漫就真正崛起了。”");


    }
}
