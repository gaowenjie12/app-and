package com.zsoe.businesssharing.business.attention;

import android.graphics.Point;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zsoe.businesssharing.R;
import com.zsoe.businesssharing.base.BaseFragment;
import com.zsoe.businesssharing.base.baseadapter.OnionRecycleAdapter;
import com.zsoe.businesssharing.base.presenter.RequiresPresenter;
import com.zsoe.businesssharing.bean.FenHuiBean;
import com.zsoe.businesssharing.business.message.MainEvent;
import com.zsoe.businesssharing.commonview.banner.BannerView;
import com.zsoe.businesssharing.commonview.recyclerview.BaseViewHolder;
import com.zsoe.businesssharing.utils.FrecoFactory;
import com.zsoe.businesssharing.utils.ScreenUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import indi.liyi.viewer.Utils;
import indi.liyi.viewer.ViewData;
import rx.functions.Action1;

/**
 * 分会介绍
 */
@RequiresPresenter(ChapterIntroductionPresenter.class)
public class ChapterIntroductionFragment extends BaseFragment<ChapterIntroductionPresenter> {

    private static final String TAG = "HomeFragment";

    public static ChapterIntroductionFragment newInstance(String title) {
        ChapterIntroductionFragment f = new ChapterIntroductionFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        f.setArguments(args);
        return f;
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
    }

    @Override
    protected int createViewByLayoutId() {
        return R.layout.fragment_chapter_introduction;
    }

    private BannerView bannerView;
    private RecyclerView rv_pic;
    private List<ViewData> mVdList;
    private WebView mWebView;

    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bannerView = view.findViewById(R.id.banner_view);
        rv_pic = view.findViewById(R.id.rv_pic);
        mWebView = view.findViewById(R.id.webView1);

        initWebView();

        initPtrFrameLayout(new Action1<String>() {
            @Override
            public void call(String s) {
                //刷新
                getPresenter().attentionIndex();
            }
        });

        //左右滑动时刷新控件禁止掉
        mPtrFrame.disableWhenHorizontalMove(true);

        mPtrFrame.autoRefresh();
    }

    public void setDate(FenHuiBean fenHuiBean) {
        mPtrFrame.refreshComplete();

        bannerView.setDate(fenHuiBean.getSlide());

        mWebView.loadUrl(fenHuiBean.getContenturl());

        Point mScreenSize = ScreenUtils.getScreenSize(getActivity());
        mVdList = new ArrayList<>();
        List<String> photos = fenHuiBean.getPhotos();
        for (int i = 0, len = photos.size(); i < len; i++) {
            ViewData viewData = new ViewData();
            viewData.setImageSrc(photos.get(i));
            viewData.setTargetX(0);
            viewData.setTargetY(0);
            viewData.setTargetWidth(mScreenSize.x);
            viewData.setTargetHeight(Utils.dp2px(getActivity(), 200));
            mVdList.add(viewData);
        }


        OnionRecycleAdapter noticeBeanOnionRecycleAdapter = new OnionRecycleAdapter<String>(R.layout.item_fenhui_layout, fenHuiBean.getPhotos()) {
            @Override
            protected void convert(BaseViewHolder holder, final String item) {
                super.convert(holder, item);

                SimpleDraweeView simpleDraweeView = holder.getView(R.id.image);
                FrecoFactory.getInstance().disPlay(simpleDraweeView, item);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int[] location = new int[2];
                        // 获取在整个屏幕内的绝对坐标
                        view.getLocationOnScreen(location);
                        ViewData viewData = mVdList.get(holder.getAdapterPosition());
                        viewData.setTargetX(location[0]);
                        viewData.setTargetY(location[1]);
                        EventBus.getDefault().post(new MainEvent(holder.getAdapterPosition(), mVdList));

                    }
                });
            }
        };


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        //调整RecyclerView的排列方向
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rv_pic.setLayoutManager(layoutManager);// 布局管理器。
        rv_pic.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        rv_pic.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加
        rv_pic.setAdapter(noticeBeanOnionRecycleAdapter);


    }


    private void initWebView() {
//        mWebView.setInitialScale(100); //代表不缩放。
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https")) {
                    return false;
                }
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            //重新这个方法，兼容https的加载情况
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            @Override
            public boolean onShowFileChooser(WebView arg0,
                                             ValueCallback<Uri[]> arg1, FileChooserParams arg2) {
                // TODO Auto-generated method stub
                Log.e("app", "onShowFileChooser");
                return super.onShowFileChooser(arg0, arg1, arg2);
            }

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2, JsResult arg3) {
                return true;
            }

            /**
             * 对应js 的通知弹框 ，可以用来实现js 和 android之间的通信
             */


            @Override
            public void onReceivedTitle(WebView arg0, final String title) {
                super.onReceivedTitle(arg0, title);

            }

            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);

            }
        });

        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
//        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(false);
        //webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
//        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
//        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
//        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
//        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
//                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        /**
         * 不显示缩放控件
         */
        webSetting.setDisplayZoomControls(false);
        //webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);

        //解除数据阻止
        webSetting.setBlockNetworkImage(false);

        /**
         * MIXED_CONTENT_NEVER_ALLOW：Webview不允许一个安全的站点（https）去加载非安全的站点内容（http）,比如，https网页内容的图片是http链接。强烈建议App使用这种模式，因为这样更安全。
         * MIXED_CONTENT_ALWAYS_ALLOW：在这种模式下，WebView是可以在一个安全的站点（Https）里加载非安全的站点内容（Http）,这是WebView最不安全的操作模式，尽可能地不要使用这种模式。
         * MIXED_CONTENT_COMPATIBILITY_MODE：在这种模式下，当涉及到混合式内容时，WebView会尝试去兼容最新Web浏览器的风格。一些不安全的内容（Http）能被加载到一个安全的站点上（Https），而其他类型的内容将会被阻塞。这些内容的类型是被允许加载还是被阻塞可能会随着版本的不同而改变，并没有明确的定义。这种模式主要用于在App里面不能控制内容的渲染，但是又希望在一个安全的环境下运行。
         *
         * 在Android5.0以下，默认是采用的MIXED_CONTENT_ALWAYS_ALLOW模式，即总是允许WebView同时加载Https和Http；而从Android5.0开始，默认用MIXED_CONTENT_NEVER_ALLOW模式，即总是不允许WebView同时加载Https和Http。
         *
         * 在webview加载页面之前，设置加载模式为
         *
         * MIXED_CONTENT_ALWAYS_ALLOW（不太安全）
         *
         * 或者 MIXED_CONTENT_COMPATIBILITY_MODE（个人建议）。
         * */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

//        CookieSyncManager.createInstance(this);
//        CookieSyncManager.getInstance().sync();
    }
}
