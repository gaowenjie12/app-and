package com.zsoe.businesssharing.business.exhibitionhall;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.bean.BannerItemBean;
import com.zsoe.businesssharing.utils.GlideUtils;

import java.util.List;


/**
 * 第二种方式的伸展布局适配器，相比于第一种不依赖javabean
 */
public class ExpandAdapter extends RecyclerView.Adapter<ExpandAdapter.ViewHolder> {


    private int criticalCount = 15;//伸缩临界值

    private boolean hide = true;//是否处于回缩状态


    //② 创建ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView hangye_image;
        public final TextView tv_hangye;

        public ViewHolder(View v) {
            super(v);
            hangye_image = (ImageView) v.findViewById(R.id.hangye_image);
            tv_hangye = (TextView) v.findViewById(R.id.tv_hangye);
        }
    }

    private List<BannerItemBean> mDatas;
    private Context mContext;

    public ExpandAdapter(Context mContext, List<BannerItemBean> data) {
        this.mContext = mContext;
        this.mDatas = data;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_hangye.setText(mDatas.get(position).getUrl_title());
        GlideUtils.loadImage(mContext, mDatas.get(position).getImg(), holder.hangye_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item 点击事件
                mContext.startActivity(new Intent(mContext, IndustryActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        int dataLength = mDatas.size();
        if (dataLength >= criticalCount) {
            //超过临界值，添加footer
            if (hide) {
                return criticalCount;
            } else {
                return dataLength;
            }
        }
        return dataLength;
    }


    public boolean toggle() {
        hide = !hide;
        notifyDataSetChanged();
        return hide;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hangye_layout, parent, false);
        return new ViewHolder(v);
    }
}
