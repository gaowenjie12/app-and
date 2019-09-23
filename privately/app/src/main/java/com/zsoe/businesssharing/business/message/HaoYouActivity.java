package com.zsoe.businesssharing.business.message;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseActivity;
import com.zsoe.businesssharing.base.DApplication;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.HaoYouBean;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.easechat.ChatActivity;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.List;

@RequiresPresenter(HaoYouPresenter.class)
public class HaoYouActivity extends BaseActivity<HaoYouPresenter> {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hao_you);
        initView();

        initTitleText("好友列表");

        DialogManager.getInstance().showNetLoadingView(mContext);
        getPresenter().myfriend_list();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);


    }


    public void getSuccess(List<HaoYouBean> haoYouBeanList) {

        OnionRecycleAdapter noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<HaoYouBean>(R.layout.item_haoyou_layout, haoYouBeanList) {
            @Override
            protected void convert(BaseViewHolder holder, final HaoYouBean item) {
                super.convert(holder, item);


                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item.getAvatar());
                holder.setText(R.id.tv_title, item.getFriend_name());


                View view = holder.getView(R.id.btnDelete);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DialogManager.getInstance().showNormalDialog(mContext, "删除好友", "删除好友后将不能收到该好友消息", "删除", "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DialogManager.getInstance().showNetLoadingView(mContext);
                                getPresenter().remove_friend(DApplication.getInstance().getLoginUser().getId() + "",
                                        item.getFriend_username() + ""
                                );
                            }
                        });

                    }
                });

                holder.getView(R.id.SwipeMenuLayout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // demo中直接进入聊天页面，实际一般是进入用户详情页
                        startActivity(new Intent(mContext, ChatActivity.class).putExtra("userId", item.getFriend_username()));
                    }
                });

            }
        };


        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        mRecyclerView.setAdapter(noticeBeanOnionRecycleAdapter);
    }


    public void deleteSuccess() {
        DialogManager.getInstance().showNetLoadingView(mContext);
        getPresenter().myfriend_list();
    }
}
