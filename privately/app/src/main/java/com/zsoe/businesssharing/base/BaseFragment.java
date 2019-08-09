package com.zsoe.businesssharing.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gyf.barlibrary.ImmersionBar;
import com.umeng.analytics.MobclickAgent;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.presenter.Presenter;
import com.zsoe.businesssharing.base.presenter.PresenterFactory;
import com.zsoe.businesssharing.base.presenter.PresenterLifecycleDelegate;
import com.zsoe.businesssharing.base.presenter.ReflectionPresenterFactory;
import com.zsoe.businesssharing.base.presenter.ViewWithPresenter;
import com.zsoe.businesssharing.utils.ScreenUtils;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/5/16.
 */
public abstract class BaseFragment<P extends Presenter> extends Fragment implements ViewWithPresenter<P> {


    protected boolean isViewCreated = false;

    protected abstract int createViewByLayoutId();

    private static final String PRESENTER_STATE_KEY = "presenter_state";
    private PresenterLifecycleDelegate<P> presenterDelegate =
            new PresenterLifecycleDelegate<>(ReflectionPresenterFactory.<P>fromViewClass(getClass()));

    /**
     * Returns a current presenter factory.
     */
    public PresenterFactory<P> getPresenterFactory() {
        return presenterDelegate.getPresenterFactory();
    }

    /**
     * Sets a presenter factory.
     * Call this method before onCreate/onFinishInflate to override default {@link ReflectionPresenterFactory} presenter factory.
     * Use this method for presenter dependency injection.
     */
    @Override
    public void setPresenterFactory(PresenterFactory<P> presenterFactory) {
        presenterDelegate.setPresenterFactory(presenterFactory);
    }

    /**
     * Returns a current attached presenter.
     * This method is guaranteed to return a non-null value between
     * onResume/onPause and onAttachedToWindow/onDetachedFromWindow calls
     * if the presenter factory returns a non-null value.
     *
     * @return a currently attached presenter or null.
     */
    public P getPresenter() {
        return presenterDelegate.getPresenter();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            presenterDelegate.onRestoreInstanceState(bundle.getBundle(PRESENTER_STATE_KEY));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = createViewByLayoutId();
        if (layoutId == 0) {
            throw new IllegalArgumentException("你必须在createViewByLayoutId()中返回主视图");
        }
        isViewCreated = true;
        return inflater.inflate(layoutId, null);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSaveInstanceState());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenterDelegate.onResume(this);
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        presenterDelegate.onPause(getActivity().isFinishing());
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    public BaseActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (BaseActivity) context;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && isViewCreated) {
            lazyLoadData();
        }
    }

    /**
     * 懒加载方法
     */
    protected void lazyLoadData() {
    }


    /**
     * 下拉刷新模塊
     */
    public PtrClassicFrameLayout mPtrFrame;

    /**
     * for loadmore
     *
     * @param action1
     * @param contentView
     */
    protected void initPtrFrameLayout(final Action1<String> action1, final View contentView) {
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.ptr_layout);
        final MaterialHeader header = new MaterialHeader(getActivity());
        int[] colors = getResources().getIntArray(R.array.loading_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, ScreenUtils.dip2px(mContext, 15), 0, ScreenUtils.dip2px(mContext, 10));
        header.setPtrFrameLayout(mPtrFrame);
        mPtrFrame.setDurationToClose(100);
        mPtrFrame.setPinContent(false);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, contentView == null ? content : contentView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                action1.call("用戶下拉了");
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(300);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
    }

    /**
     * 检查是否可以刷新
     */
    protected boolean checkCanRefresh(PtrFrameLayout frame, View content, View header, final View contentView) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, contentView == null ? content : contentView, header);
    }

    protected void initPtrFrameLayout(final Action1<String> action1) {
        initPtrFrameLayout(action1, null);
    }

    public View findViewById(int resId) {
        View mainView = getView();
        if (mainView != null) {
            return mainView.findViewById(resId);
        }
        return null;
    }

    //--------------------------通用方法设置标题栏－－－－－－
    protected void setTitleLeftIcon(boolean show, int resid) {
        ImageView view = (ImageView) findViewById(R.id.title_left_iv);
        if (show) {
            view.setVisibility(View.VISIBLE);
            view.setImageResource(resid);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    protected void setTitleRigthIcon(boolean show, int resid) {
        ImageView view = (ImageView) findViewById(R.id.title_rigth_iv);
        if (show) {
            view.setVisibility(View.VISIBLE);
            view.setImageResource(resid);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    protected void setTitleRigthIcon(boolean show, int resid, View.OnClickListener onClickListener) {
        ImageView view = (ImageView) findViewById(R.id.title_rigth_iv);
        if (show) {
            view.setVisibility(View.VISIBLE);
            view.setImageResource(resid);
            view.setOnClickListener(onClickListener);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    protected ImageView getTitleRightImg() {
        ImageView view = (ImageView) findViewById(R.id.title_title_img);
        view.setVisibility(View.VISIBLE);
        return view;
    }


    protected void setTitleLeftText(boolean show, String text) {
        TextView view = (TextView) findViewById(R.id.title_left_tv);
        if (show) {
            view.setVisibility(View.VISIBLE);
            view.setText(text);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    protected void setTitleRightText(boolean show, String text) {
        TextView view = (TextView) findViewById(R.id.title_rigth_tv);
        if (show) {
            view.setVisibility(View.VISIBLE);
            view.setText(text);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    protected void setTitleRightText(boolean show, String text, View.OnClickListener onClickListener) {
        TextView view = (TextView) findViewById(R.id.title_rigth_tv);
        if (show) {
            view.setVisibility(View.VISIBLE);
            view.setText(text);
            view.setOnClickListener(onClickListener);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    protected void setTitleText(boolean show, String text) {
        TextView view = (TextView) findViewById(R.id.title_title_tv);
        if (show) {
            view.setVisibility(View.VISIBLE);
            view.setText(text);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    protected View getTitleText() {
        return findViewById(R.id.title_title_tv);
    }

    protected void setTitleTitleBg(int color) {
        RelativeLayout view = (RelativeLayout) findViewById(R.id.title_bar);
        view.setBackgroundColor(color);
    }

    protected ImmersionBar mImmersionBar;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null) {
            mImmersionBar.init();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPtrFrame = null;
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true)
                .navigationBarWithKitkatEnable(false).init();
    }

}
