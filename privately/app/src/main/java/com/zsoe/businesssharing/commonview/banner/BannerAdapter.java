package com.zsoe.businesssharing.commonview.banner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BrowserActivity;
import com.zsoe.businesssharing.base.Config;
import com.zsoe.businesssharing.bean.SlideBean;
import com.zsoe.businesssharing.business.exhibitionhall.ProductDetailActivity;
import com.zsoe.businesssharing.business.home.FinancingLoansDetailActivity;
import com.zsoe.businesssharing.business.home.JoinInvestmentDetailActivity;
import com.zsoe.businesssharing.business.home.ProcurementAndInventoryDetailActivity;
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
        SlideBean slideBean = adList.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        SimpleDraweeView simpleDraweeView = view.findViewById(R.id.banner_image);
        FrecoFactory.getInstance().disPlay(simpleDraweeView, slideBean.getSlide());

        container.addView(view);

        int linktype = slideBean.getLinktype();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //幻灯片的跳转类型 跳转类型字段为  linktype ，值有以下7种，返回字段 linkurl不为空的情况跳转H5，跳转文章的字段为 linkurlid
                // 1        => '头条快讯',
                //    2       => '库存尾货',
                //    3        => '采购需求',
                //    4        => '招商加盟',
                //    5       => '信贷',
                //    6        => '融资',
                //    7       => '产品',


                if (linktype == 1 || linktype == 5) {
                    Intent intent = new Intent(context, BrowserActivity.class);
                    intent.putExtra(Config.INTENT_PARAMS1, slideBean.getLinkurl());
                    context.startActivity(intent);
                } else if (linktype == 2) {

                    Intent intent = new Intent(context, ProcurementAndInventoryDetailActivity.class);
                    intent.putExtra(Config.INTENT_PARAMS1, slideBean.getLinkurlid());
                    context.startActivity(intent);

                } else if (linktype == 3) {
                    Intent intent = new Intent(context, ProcurementAndInventoryDetailActivity.class);
                    intent.putExtra(Config.INTENT_PARAMS1, slideBean.getLinkurlid());
                    context.startActivity(intent);
                } else if (linktype == 4) {
                    Intent intent = new Intent(context, JoinInvestmentDetailActivity.class);
                    intent.putExtra(Config.INTENT_PARAMS1, slideBean.getLinkurlid());
                    context.startActivity(intent);
                } else if (linktype == 6) {
                    Intent intent = new Intent(context, FinancingLoansDetailActivity.class);
                    intent.putExtra(Config.INTENT_PARAMS1, slideBean.getLinkurlid());
                    context.startActivity(intent);
                } else if (linktype == 7) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra(Config.INTENT_PARAMS1, slideBean.getLinkurlid());
                    context.startActivity(intent);
                }

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
