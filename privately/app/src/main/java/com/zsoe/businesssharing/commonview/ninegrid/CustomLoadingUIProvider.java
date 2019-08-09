package com.zsoe.businesssharing.commonview.ninegrid;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zsoe.businesssharing.R;


public class CustomLoadingUIProvider implements ImageWatcher.LoadingUIProvider {
    private final FrameLayout.LayoutParams lpCenterInParent = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    @Override
    public View initialView(Context context) {
//        ImageView load = new ImageView(context);
        View load = LayoutInflater.from(context).inflate(R.layout.dialog_upload_layout,null);
        lpCenterInParent.gravity = Gravity.CENTER;
        load.setLayoutParams(lpCenterInParent);
//        load.setImageResource(R.drawable.anim);
        return load;
    }

    @Override
    public void start(View loadView) {
        loadView.setVisibility(View.VISIBLE);
//        ((AnimationDrawable) ((ImageView) loadView).getDrawable()).start();
    }

    @Override
    public void stop(View loadView) {
//        ((AnimationDrawable) ((ImageView) loadView).getDrawable()).stop();
        loadView.setVisibility(View.GONE);
    }
}
