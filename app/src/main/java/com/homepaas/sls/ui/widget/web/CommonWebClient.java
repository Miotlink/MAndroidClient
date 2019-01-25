package com.homepaas.sls.ui.widget.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.homepaas.sls.R;
import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.common.NetUtils;
import com.homepaas.sls.httputils.rxbinding.RxBindingViewClickHelper;
import com.homepaas.sls.ui.widget.networkerrorview.CommonNetWorkErrorViewReplacer;
import com.homepaas.sls.util.ToastUtil;

/**
 * Created by mhy on 2017/8/15.
 */

public class CommonWebClient extends WebViewClient {

    //加载过程，暂时使用进度条进行替代
    private CommonNetWorkErrorViewReplacer commonNetWorkErrorViewReplacer;
    private Context context;
    private WebView webView;
    private ToastUtil toastUtil;
    private ProgressBar progressBar;

    public CommonWebClient(WebView webView, final Context context, ProgressBar progressBar) {
        toastUtil = SimpleTinkerInApplicationLike.getApplicationComponent().getToastUtil();
        this.context = context;
        this.progressBar=progressBar;
        this.webView = webView;
        this.commonNetWorkErrorViewReplacer = new CommonNetWorkErrorViewReplacer(context, this.webView, new RxBindingViewClickHelper.OnRxClick() {
            @Override
            public void rxClick(View view) {
                if (CommonWebClient.this.webView != null) {
                    CommonWebClient.this.webView.reload();//刷新网页;
                }
            }
        });
    }

//    @Override
//    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//        super.onReceivedError(view, errorCode, description, failingUrl);
//        if (!NetUtils.isConnected(context)) {
//            commonNetWorkErrorViewReplacer.showErrorLayout();
//            toastUtil.showToast(R.string.please_check_network);
//        }
//    }

    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (!NetUtils.isConnected(context)) {
            commonNetWorkErrorViewReplacer.showErrorLayout();
            toastUtil.showToast(R.string.please_check_network);
        }
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (NetUtils.isConnected(context))
            commonNetWorkErrorViewReplacer.showOriginalLayout();
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
//        commonNetWorkErrorViewReplacer.showLoadLayout(R.layout.webview_load_default_layout);
    }
}
