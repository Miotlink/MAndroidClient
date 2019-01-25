package com.homepaas.sls.ui.widget.web;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by mhy on 2017/8/15.
 * 风筝公共配置
 */

public class MyWebView extends WebView {

    public MyWebView(Context context) {
        super(context);
        initConfig(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConfig(context);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig(context);
    }

    private void initConfig(Context context) {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);//设置使用够执行JS脚本
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
//        WebViewCacheUtils.setWebViewCacheConfig(settings);
    }
}
