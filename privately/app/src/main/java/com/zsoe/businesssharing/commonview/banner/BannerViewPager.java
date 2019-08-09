package com.zsoe.businesssharing.commonview.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-07 10:50
 * @version:
 * @类说明:
 */
public class BannerViewPager extends ViewPager {

    private ViewGroup parent;

    public BannerViewPager(Context context) {
        super(context);
        parent = (ViewGroup) getParent();
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        parent = (ViewGroup) getParent();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (parent != null) {
                    //禁止上一层的View不处理该事件,屏蔽父组件的事件
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (parent != null) {
                    //拦截
                    parent.requestDisallowInterceptTouchEvent(false);
                }
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}