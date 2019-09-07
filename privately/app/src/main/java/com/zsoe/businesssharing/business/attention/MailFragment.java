package com.zsoe.businesssharing.business.attention;

import android.content.Intent;
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
import com.zsoe.businesssharing.bean.XinXiBena;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.List;

import rx.functions.Action1;

/**
 * 信箱
 */
@RequiresPresenter(MailPresenter.class)
public class MailFragment extends BaseFragment<MailPresenter> {

    private static final String TAG = "HomeFragment";

    public static MailFragment newInstance(String title) {
        MailFragment f = new MailFragment();
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
        return R.layout.fragment_mail;
    }


    private RecyclerView recyclerView;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().attentionMailbox();
            }
        });

        //左右滑动时刷新控件禁止掉
        mPtrFrame.disableWhenHorizontalMove(true);
        recyclerView = view.findViewById(R.id.recyclerView);

        mPtrFrame.autoRefresh();

    }


    public void setData(List<XinXiBena> xinXiBenas) {

        mPtrFrame.refreshComplete();
        OnionRecycleAdapter noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<XinXiBena>(R.layout.item_mail_layout, xinXiBenas) {
            @Override
            protected void convert(BaseViewHolder holder, final XinXiBena item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getThumb());
                holder.setText(R.id.tv_title, item.getName());

                int type = item.getType();
                if (type == 3 || type == 4) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, BuZhangXinxiActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        };


        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        recyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        recyclerView.setAdapter(noticeBeanOnionRecycleAdapter);


    }
}
