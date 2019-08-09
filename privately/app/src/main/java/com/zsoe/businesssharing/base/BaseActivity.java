package com.zsoe.businesssharing.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.umeng.analytics.MobclickAgent;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.presenter.Presenter;
import com.zsoe.businesssharing.base.presenter.PresenterFactory;
import com.zsoe.businesssharing.base.presenter.PresenterLifecycleDelegate;
import com.zsoe.businesssharing.base.presenter.ReflectionPresenterFactory;
import com.zsoe.businesssharing.base.presenter.ViewWithPresenter;
import com.zsoe.businesssharing.utils.DialogManager;
import com.zsoe.businesssharing.utils.EmptyUtil;
import com.zsoe.businesssharing.utils.ScreenUtils;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import rx.functions.Action1;


/**
 * This class is an example of how an activity could controls it's presenter.
 * You can inherit from this class or copy/paste this class's code to
 * create your own view implementation.
 * <p>
 * copy from com.openlibray.common.view NucleusActivity   @2016.5.9 by onion
 *
 * @param <P> a type of presenter to return with {@link #getPresenter}.
 */
public abstract class BaseActivity<P extends Presenter> extends AppCompatActivity implements ViewWithPresenter<P> {

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

    public Context mContext;

//    /**
//     * 系统是否支持黑色字体
//     */
//    protected boolean isSupportBlackFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
//        if (getIntent().getBooleanExtra(Config.INTENT_TONGJI, false))
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DApplication.getInstance().addActivity(this);


//        if (!ImageLoader.getInstance().isInited()) {
//            ImageLoaderConfig.initImageLoader(this, Config.BASE_IMAGE_CACHE);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    View view = getWindow().getDecorView();
//                    ImageUtils.WindowHeight = view.getHeight();
//                    ImageUtils.WindowWidth = view.getWidth();
//                }
//            }, 1000);
//        }
        if (savedInstanceState != null) {
            //删除上次的fragment
            String FRAGMENTS_TAG = "Android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);
            presenterDelegate.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_STATE_KEY));
        }

//        //设置沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
    }

    protected LinearLayout leftLayout;
    protected ImageView leftImageView, rightImageView;
    protected TextView leftTextView, rightTextView, titleTextView;
    protected ImmersionBar mImmersionBar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        //5.0以后展示沉浸状态栏，避免页面被状态栏覆盖做一下操作
        if (isFitSystemEnabled()) {
            ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            View parentView = contentFrameLayout.getChildAt(0);
            if (parentView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                parentView.setFitsSystemWindows(true);
            }
        }
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.navigationBarWithKitkatEnable(false);
        mImmersionBar.init();
//        isSupportBlackFont = mImmersionBar.isSupportStatusBarDarkFont();
        if (isBlackFontStatusEnabled()) {
            //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
            ImmersionBar.with(this)
                    .statusBarDarkFont(true, 0.2f)
                    .init();
        }
    }

    /**
     * 是否可以使用沉浸式
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 状态栏字体是否是黑色默认true
     */
    protected boolean isBlackFontStatusEnabled() {
        return true;
    }

    /**
     * 是否又基类设置fitSystem属性
     * 默认设置更布局第一个 api19开始
     * true
     */
    protected boolean isFitSystemEnabled() {
        return true;
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSaveInstanceState());
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(getClass().getSimpleName());
        presenterDelegate.onResume(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(getClass().getSimpleName());
        presenterDelegate.onPause(isFinishing());
//        KeyBoardUtils.closeKeybord(this);
    }


    protected void initTitle(String title) {
        initTitleText(title);
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        }
//        return true;
//    }


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
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.loading_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, ScreenUtils.dip2px(this, 15), 0, ScreenUtils.dip2px(this, 10));
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

    protected void initPtrFrameLayout(final Action1<String> action1) {
        initPtrFrameLayout(action1, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DApplication.getInstance().removeActivity(this);
        DialogManager.getInstance().dismissNetLoadingView();

        mPtrFrame = null;
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //在BaseActivity里销毁
        }
    }


    //--------------------------通用方法设置标题栏－－－－－－
    protected void setTitleLeftIcon(int resid, final Action1<View> click) {
        if (leftImageView == null)
            leftImageView = (ImageView) findViewById(R.id.title_left_iv);
        if (leftImageView != null) {
            leftImageView.setVisibility(View.VISIBLE);
            leftImageView.setImageResource(resid);
            if (click != null)
                leftImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click.call(v);
                    }
                });
        }
    }

    protected void setTitleRigthIcon(int resid, final Action1<View> click) {
        rightImageView = (ImageView) findViewById(R.id.title_rigth_iv);
        if (rightImageView != null) {
            rightImageView.setVisibility(View.VISIBLE);
            rightImageView.setImageResource(resid);
            if (click != null) {
                rightImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click.call(v);
                    }
                });
            }
        }
    }

    protected void setTitleRightSecondIcon(int resid, final Action1<View> clickAction) {
        rightImageView = (ImageView) findViewById(R.id.title_rigth_second);
        if (rightImageView != null) {
            rightImageView.setVisibility(View.VISIBLE);
            rightImageView.setImageResource(resid);
            if (clickAction != null) {
                rightImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickAction.call(v);
                    }
                });
            }
        }
    }

    protected void setTitleLeftText(String text, final Action1<View> click) {
        leftTextView = (TextView) findViewById(R.id.title_left_tv);
        if (leftTextView != null) {
            leftTextView.setVisibility(View.VISIBLE);
            leftTextView.setText(text);
            if (click != null) {
                leftTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click.call(v);
                    }
                });
            }
        }
    }

    protected void setTitleRightText(String text, final Action1<View> click) {
        rightTextView = (TextView) findViewById(R.id.title_rigth_tv);
        if (rightTextView != null) {
            rightTextView.setVisibility(View.VISIBLE);
            rightTextView.setText(text);
            if (click != null) {
                rightTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click.call(v);
                    }
                });
            }
        }
    }

    protected void setTitleRightText(String text, int colorId, final Action1<View> click) {
        rightTextView = (TextView) findViewById(R.id.title_rigth_tv);
        if (rightTextView != null) {
            rightTextView.setVisibility(View.VISIBLE);
            rightTextView.setText(text);
            rightTextView.setTextColor(colorId);
            if (click != null) {
                rightTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click.call(v);
                    }
                });
            }
        }
    }

    /**
     * 设置标题栏字体以及图标
     *
     * @param text        标题字体
     * @param resonseId   无图标传0
     * @param clickAction 标题栏点击事件，无传NULL
     */
    protected void setTitleCenterTextIcon(String text, int resonseId, final Action1<View> clickAction) {
        titleTextView = (TextView) findViewById(R.id.title_title_tv);
        titleTextView.setText(text);
        if (resonseId != 0) {
            ImageView icon = (ImageView) findViewById(R.id.title_title_img);
            icon.setVisibility(View.VISIBLE);
            icon.setImageResource(resonseId);
        }
        if (clickAction != null) {
            findViewById(R.id.title_center_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickAction.call(v);
                }
            });
        }
    }

    /**
     * 设置标题 并加入默认点击事件
     * 如果想要重新处理默认返回事件
     * 可重写defaultHanderBack()方法
     *
     * @param title
     */
    protected void initTitleText(String title) {
        leftLayout = (LinearLayout) findViewById(R.id.title_left_layout);
        if (leftLayout != null) {
            titleTextView = (TextView) findViewById(R.id.title_title_tv);
            if (!EmptyUtil.isEmpty(leftLayout)) {
                leftLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        defaultHanderBack();
                    }
                });
            }
            if (titleTextView != null) {
                titleTextView.setText(title);
            }
        }
    }

    protected void setTitleTitleBg(int color) {
        RelativeLayout view = (RelativeLayout) findViewById(R.id.title_bar);
        view.setBackgroundColor(color);
    }

    /**
     * 默认点左按钮的处理方式
     */
    protected void defaultHanderBack() {
        finish();
    }
}
