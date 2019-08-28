package com.zsoe.businesssharing.commonview.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.lwj.widget.viewpagerindicator.ViewPagerIndicator;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.bean.SlideBean;

import java.util.List;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-06 13:16
 * @version:
 * @类说明:
 */
public class BannerView extends RelativeLayout {

    private ViewPager vp_shuffling;
    private ViewPagerIndicator viewPagerIndicator;

    private Context mContext;


    public BannerView(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        initView(context);


    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.banner_layout, this);
        vp_shuffling = inflate.findViewById(R.id.vp_banner);
        viewPagerIndicator = inflate.findViewById(R.id.indicator_line);

        vp_shuffling.setClipChildren(false);
        vp_shuffling.setOffscreenPageLimit(3);

        vp_shuffling.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                if (mHandler.hasMessages(HOME_AD_RESULT)) {
                    mHandler.removeMessages(HOME_AD_RESULT);
                }
                mHandler.sendEmptyMessageDelayed(HOME_AD_RESULT, 3000);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                if (ViewPager.SCROLL_STATE_DRAGGING == arg0
                        && mHandler.hasMessages(HOME_AD_RESULT)) {
                    mHandler.removeMessages(HOME_AD_RESULT);
                }


            }
        });
    }

    public void setDate(List<SlideBean> bannerItemBeans) {

        BannerAdapter adapter = new BannerAdapter(mContext, bannerItemBeans);
        vp_shuffling.setAdapter(adapter);

        vp_shuffling.setCurrentItem(bannerItemBeans.size() * 1000, false);

        // 自动轮播线程
        mHandler.sendEmptyMessageDelayed(HOME_AD_RESULT, 3000);


        //需要传入实际展示个数num
        viewPagerIndicator.setViewPager(vp_shuffling, bannerItemBeans.size());
        /**
         * app:vpi_selected_color
         app:vpi_default_color (如果 indicatorType=CIRCLE_LINE default_color 为指示器唯一颜色 ,selected_color 不起作用)
         app:vpi_radius (点的大小,在indicatorType= CIRCLE_LINE 的情况下 radius 是点的高)
         app:vpi_length (只作用在 indicatorType=CIRCLE_LINE 的情况下,为 指示器点的长度)
         app:vpi_distance (只作用在 distanceType=BY_DISTANCE 的情况下)
         app:vpi_num
         app:vpi_indicatorType (LINE; CIRCLE; CIRCLE_LINE; BEZIER;SPRING)
         ​ LINE：线 ; CIRCLE：圆点(默认) ; CIRCLE_LINE：圆角矩形; BEZIER：弹性球 ; SPRING： 弹簧粘性球

         app:vpi_distanceType (BY_RADIUS; BY_DISTANCE ; BY_LAYOUT )
         ​ BY_RADIUS：3倍radius ; BY_DISTANCE ：定义固定距离 ;BY_LAYOUT ：根据layout_width均分得到距离

         app:vpi_animation(默认为true:动画开启 ; false:关闭动画)
         * */

    }

    private final int HOME_AD_RESULT = 1;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 广告
                case HOME_AD_RESULT:
                    vp_shuffling.setCurrentItem(vp_shuffling.getCurrentItem() + 1,
                            true);
                    break;
            }
        }

        ;
    };
}
