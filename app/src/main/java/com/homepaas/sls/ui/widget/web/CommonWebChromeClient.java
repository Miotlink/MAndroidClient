package com.homepaas.sls.ui.widget.web;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by mhy on 2017/8/15.
 */

public class CommonWebChromeClient extends WebChromeClient {
    private ProgressBar progressBar;

    public CommonWebChromeClient(ProgressBar webViewProgressbar) {
        this.progressBar = webViewProgressbar;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        //bugly----Javascript的异常捕获功能
        CrashReport.setJavascriptMonitor(view, true);
        super.onProgressChanged(view, newProgress);
        setProgressValue(newProgress);
    }

    private void setProgressValue(int progress) {
        if (progressBar == null)
            return;
        if (progressBar.getVisibility() != View.VISIBLE)
            progressBar.setVisibility(View.VISIBLE);
           progressBar.setProgress(progress);
        if (progress >= 100)
            progressBar.setVisibility(View.GONE);
    }

}