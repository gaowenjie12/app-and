package com.zsoe.businesssharing.base;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zsoe.businesssharing.R;


/**
 * x5webview activity
 */
public class BrowserActivity extends BaseActivity {
    /**
     * 作为一个浏览器的示例展示出来，采用android+web的模式
     */
    private WebView mWebView;
    private ViewGroup mViewParent;
    private ProgressBar mPageLoadingProgressBar = null;
    private TextView title_title_tv;
    protected ImageView title_rigth_iv;

    /**
     * 资源地址
     */
    protected String mIntentUrl;

    /**
     * 资源名
     */
    protected String mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Intent intent = getIntent();
        if (intent != null) {
            mIntentUrl = intent.getStringExtra(Config.INTENT_PARAMS1);
            mTitle = intent.getStringExtra(Config.INTENT_PARAMS2);

        }

        mIntentUrl = mIntentUrl + "?v=" + System.currentTimeMillis();
        Log.e("open", mIntentUrl);

        setContentView(loadContentView());

        mViewParent = (ViewGroup) findViewById(R.id.webView1);
        title_title_tv = (TextView) findViewById(R.id.title_title_tv);
        title_title_tv.setVisibility(View.VISIBLE);
        title_rigth_iv = (ImageView) findViewById(R.id.title_rigth_iv);
        title_title_tv.setText(mTitle);
        initBtnListenser();
        init();
    }

    /**
     * 子类可重新该方法实现自己的布局
     * 但布局的关键id必须存在
     */
    protected int loadContentView() {
        return R.layout.activity_browser;
    }

//    private void webViewTransportTest() {
//        X5WebView.setSmallWebViewEnabled(true);
//    }

//    private void changGoForwardButton(WebView view) {
//        if (view.canGoBack())
//            mBack.setAlpha(enable);
//        else
//            mBack.setAlpha(disable);
//    }

    private void initProgressBar() {
        mPageLoadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        mPageLoadingProgressBar.setMax(100);
        mPageLoadingProgressBar.setProgressDrawable(this.getResources()
                .getDrawable(R.drawable.color_progressbar));
    }


    private void init() {
        mWebView = new WebView(this, null);
//        mWebView.setInitialScale(100); //代表不缩放。
        mViewParent.addView(mWebView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        initProgressBar();
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

            /**
             * 全屏播放配置
             */
//            @Override
//            public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
//                super.onShowCustomView(view, customViewCallback);
//
////                FrameLayout normalView = (FrameLayout) findViewById(R.id.web_filechooser);
////                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
////                viewGroup.removeView(normalView);
////                viewGroup.addView(view);
////                myVideoView = view;
////                myNormalView = normalView;
////                callback = customViewCallback;
//                System.out.println("onShowCustomView :" + view + "," + customViewCallback);
//            }

//            @Override
//            public void onHideCustomView() {
//                super.onHideCustomView();
//                System.out.println("onHideCustomView :");
//
////                if (callback != null) {
////                    callback.onCustomViewHidden();
////                    callback = null;
////                }
////                if (myVideoView != null) {
////                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
////                    viewGroup.removeView(myVideoView);
////                    viewGroup.addView(myNormalView);
////                }
//            }
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
                if (title != null && TextUtils.isEmpty(mTitle)) {
                    title_title_tv.setText(title);
                }
            }

            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i == 100) {
                    mPageLoadingProgressBar.setVisibility(View.GONE);
                } else {
                    mPageLoadingProgressBar.setVisibility(View.VISIBLE);
                    mPageLoadingProgressBar.setProgress(i);
                }
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
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
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

        mWebView.loadUrl(mIntentUrl);

//        CookieSyncManager.createInstance(this);
//        CookieSyncManager.getInstance().sync();
    }

    private void initBtnListenser() {
        View backFinish = findViewById(R.id.title_lift_layout);
        backFinish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowserActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null || mWebView == null || intent.getData() == null) {
            return;
        }
        mWebView.loadUrl(intent.getData().toString());
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }

    /**
     * 防止webview缩放时退出崩溃。
     */
    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }

    @Override
    protected boolean isLoginIntent() {
        return false;
    }
}
