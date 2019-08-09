package com.zsoe.businesssharing.commonview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-07 10:56
 * @version:
 * @类说明:
 */
public class MyRecycleView extends RecyclerView {

    private ViewGroup parent;


    public MyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        parent = (ViewGroup) getParent();

    }


    private int lastX = -1;
    private int lastY = -1;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                dealtX = 0;
                dealtY = 0;

                if (parent != null) {
                    //禁止上一层的View不处理该事件,屏蔽父组件的事件
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);

//                Log.i("dispatchTouchEvent", "dealtX:=" + dealtX);
//                Log.i("dispatchTouchEvent", "dealtY:=" + dealtY);
// 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截

                if (dealtX >= dealtY) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                lastX = x;
                lastY = y;
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
