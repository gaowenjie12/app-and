package com.zsoe.businesssharing.business.me;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.bean.BannerItemBean;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class MessageRemindActivity extends BaseActivity {

    private RecyclerView mRvJoinList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_remind);
        initView();
        initTitleText("消息提醒");

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新

            }
        });
    }

    private void initView() {
        mRvJoinList = (RecyclerView) findViewById(R.id.rv_join_list);

        List<BannerItemBean> bannerItemBeans = new ArrayList<>();

        BannerItemBean bannerItemBean = new BannerItemBean();
        bannerItemBean.setUrl_title("简介");
        bannerItemBean.setImg("http://hbimg.b0.upaiyun.com/3e14d836d89498b116834b2987dbaa1c8f2e85a418a9fc-nLVWsW_fw658");
        bannerItemBeans.add(bannerItemBean);

        BannerItemBean bannerItemBean2 = new BannerItemBean();
        bannerItemBean2.setUrl_title("简介");

        bannerItemBean2.setImg("http://hbimg.b0.upaiyun.com/9be8e0054e2ed5e02fa91c6c66267f9d51859e951b83e-qMhDYE_fw658");
        bannerItemBeans.add(bannerItemBean2);

        BannerItemBean bannerItemBean3 = new BannerItemBean();
        bannerItemBean3.setUrl_title("简介");

        bannerItemBean3.setImg("http://img694.ph.126.net/2CR9OPpnSjmHa_7BzGVE9Q==/2868511487659481204.jpg");
        bannerItemBeans.add(bannerItemBean3);

        BannerItemBean bannerItemBean4 = new BannerItemBean();
        bannerItemBean4.setUrl_title("简介");

        bannerItemBean4.setImg("http://i1.hdslb.com/bfs/archive/20b81aa9dcffd6db03dc14296ff3b84874f0c529.png");
        bannerItemBeans.add(bannerItemBean4);

        bannerItemBeans.addAll(bannerItemBeans);
        bannerItemBeans.addAll(bannerItemBeans);
        bannerItemBeans.addAll(bannerItemBeans);


        OnionRecycleAdapter noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<BannerItemBean>(R.layout.item_huixin_layout, bannerItemBeans) {
            @Override
            protected void convert(BaseViewHolder holder, final BannerItemBean item) {
                super.convert(holder, item);


                holder.setText(R.id.tv_title, "为何马云");
                holder.setText(R.id.tv_content, "这个比较简单，就是重新计算ImageView的宽高。这里可以设置宽高的比例，所以多加一个自定义view中的自定义属性。假设我们的这个控件已经写好了，那么我们会在布局文件xml中使用，我们会在xml使用我们自定义的属性来设置宽高比");
                holder.setText(R.id.tv_time, "发布时间：2019-07-07");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
        };


        mRvJoinList.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRvJoinList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRvJoinList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRvJoinList.setAdapter(noticeBeanOnionRecycleAdapter);
    }
}
