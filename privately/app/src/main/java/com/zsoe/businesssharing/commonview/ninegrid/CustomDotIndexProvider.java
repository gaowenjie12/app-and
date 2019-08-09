package com.zsoe.businesssharing.commonview.ninegrid;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.bean.ImageInfo;

import java.util.List;

public class CustomDotIndexProvider implements ImageWatcher.IndexProvider {
    private boolean initLayout;
    TextView textView;
//    private IndicatorView indicatorView;

    @Override
    public View initialView(Context context) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        textView = new TextView(context);

//        indicatorView = new IndicatorView(context);
        textView.setLayoutParams(lp);
        textView.setTextColor(context.getResources().getColor(R.color.white));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f);

        DisplayMetrics d = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(d);

        int size = (int) (30 * d.density + 0.5);
        lp.setMargins(0, 0, 0, size);

        initLayout = false;
        return textView;
    }

    @Override
    public void onPageChanged(ImageWatcher imageWatcher, int position, List<ImageInfo> dataList) {
        textView.setText((position + 1) + "/" + dataList.size());
//        if (!initLayout) {
//            initLayout = true;
//            indicatorView.reset(dataList.size(), position, R.drawable.b_gray_dcdcdc_oval, R.drawable.b_yellow_ffb100_oval);
//        } else {
//            indicatorView.select(position, R.drawable.b_gray_dcdcdc_oval, R.drawable.b_yellow_ffb100_oval);
//        }
    }
}
