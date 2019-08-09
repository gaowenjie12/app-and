package com.zsoe.businesssharing.commonview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.commonview.popup.BasePopup;

/**
 * 选择图片
 */

public class PhotoTypePopup extends BasePopup<PhotoTypePopup> {
    private static final String TAG = "OutCompanyPopup";
    private Context mContext;
    private OperClickListener operClickListener;

    public static PhotoTypePopup create(Context context,OperClickListener operClickListener) {
        return new PhotoTypePopup(context,operClickListener);
    }

    protected PhotoTypePopup(Context context, OperClickListener operClickListener) {
        mContext = context;
        this.operClickListener = operClickListener;
        setContext(context);
    }


    @Override
    protected void initAttributes() {
        setContentView(R.layout.my_pop, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusAndOutsideEnable(false)
                .setBackgroundDimEnable(true)
                .setDimValue(0.5f);

    }

    @Override
    protected void initViews(View view, PhotoTypePopup basePopup) {


        TextView pop_xi = view.findViewById(R.id.pop_xi);
        TextView pop_mu = view.findViewById(R.id.pop_mu);
        TextView pop_no = view.findViewById(R.id.pop_no);

        pop_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        pop_xi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                operClickListener.takePhoto();
            }
        });
        pop_mu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                operClickListener.gallery();

            }
        });


    }

    public interface OperClickListener {
        void takePhoto();

        void gallery();
    }

}
