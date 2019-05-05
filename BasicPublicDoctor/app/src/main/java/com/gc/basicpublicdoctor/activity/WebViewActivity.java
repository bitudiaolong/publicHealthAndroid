package com.gc.basicpublicdoctor.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.gc.basicpublicdoctor.R;
import com.gc.basicpublicdoctor.base.BaseActivity;
import com.gc.utils.LogUtil;

/**
 * Author:Created by zhurui
 * Time:2018/7/27 下午1:38
 * Description:This is WebViewActivity
 * web页面
 */
public class WebViewActivity extends BaseActivity {
    Context context;
    WebView webView;
    String url;
    String flags;
    String titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        context = this;

        initView();
    }

    protected void initView() {
        webView = (WebView) findViewById(R.id.webview);

        showTitleName("", true);
        url = getIntent().getStringExtra("url");
        flags = getIntent().getStringExtra("flags") == null ? "" : getIntent().getStringExtra("flags");
        titles = getIntent().getStringExtra("title") == null ? "" : getIntent().getStringExtra("title");
        LogUtil.d("WebViewActivity", "url=" + url);
        initVebView();
        if (!TextUtils.isEmpty(url)) {
            webView.loadUrl(url);
        }
    }

    void initVebView() {
        WebSettings settings = webView.getSettings();
        webView.clearCache(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAppCacheEnabled(false);
        settings.setUseWideViewPort(true); // 关键点
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(false);
        if ("随访记录详情".equals(flags)) {
            settings.setTextZoom(180);
        } else {
            settings.setTextZoom(250); // 页面字体放大倍数2.5
        }
        settings.setLoadWithOverviewMode(true);
//        settings.setTextSize(WebSettings.TextSize.LARGEST);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new MyWebViewClient());
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if ("卫计专线".equals(flags)) {
                showTitleName("卫计专线", true);
            } else if ("随访记录详情".equals(flags)) {
                showTitleName(titles, true);
            } else {
                showTitleName(title, true);
            }
        }
    }

    class MyWebViewClient extends android.webkit.WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webView.loadUrl("javascript:getTitle()");
            LogUtil.d("HomeVideosDetailActivity", "onPageFinished");
        }
    }
}
