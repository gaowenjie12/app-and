package com.zsoe.businesssharing.commonview.dropfilter.typeview.grid_holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.commonview.dropfilter.util.DpUtils;
import com.zsoe.businesssharing.commonview.dropfilter.view.FilterCheckedTextView;


/**
 *
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    private FilterCheckedTextView textView;
    private View.OnClickListener mListener;
    private View view;

    public ItemViewHolder(Context mContext, ViewGroup parent, View.OnClickListener mListener) {
        super(DpUtils.infalte(mContext, R.layout.holder_item, parent));
        this.mListener = mListener;
    }

    /**
     * tag标记的字段规则：eg:"obj_s"
     *
     * @param s
     * @param tag
     */
    public void bind(String s, Object tag) {
        textView = (FilterCheckedTextView)itemView.findViewById(R.id.tv_item);
        ((FilterCheckedTextView) textView).setText(s);
        ((FilterCheckedTextView) textView).setTag(tag);
        ((FilterCheckedTextView) textView).setOnClickListener(mListener);
    }
}
