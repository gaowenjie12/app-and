package com.zsoe.businesssharing.commonview.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.bean.SlideBean;
import com.zsoe.businesssharing.commonview.wcviewpager.ObjectAtPositionPagerAdapter;
import com.zsoe.businesssharing.utils.FrecoFactory;

import java.util.List;


public class BannerAdapter extends ObjectAtPositionPagerAdapter {
    private List<SlideBean> adList;
    private Context context;

    public BannerAdapter(Context mContext, List<SlideBean> adList) {
        super();
        this.adList = adList;
        context = mContext;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }


    @Override
    public Object instantiateItemObject(ViewGroup container, int arg2) {

        final int position = arg2 % adList.size();

        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        SimpleDraweeView simpleDraweeView = view.findViewById(R.id.banner_image);
        FrecoFactory.getInstance().disPlay(simpleDraweeView, adList.get(position).getSlide());

        container.addView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, BrowserActivity.class);
//                intent.putExtra(Config.INTENT_PARAMS1, adList.get(position).getUrl());
//                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    @Override
    public void destroyItemObject(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
