package com.homepaas.sls.ui.order.servicemerchant;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.homepaas.sls.R;
import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.jsinterface.JavaScriptInteractiveIndex;
import com.homepaas.sls.jsinterface.JavaScriptObject;
import com.homepaas.sls.mvp.presenter.DetailWebViewPresenter;
import com.homepaas.sls.mvp.view.DetailServiceWebView;
import com.homepaas.sls.ui.common.fragment.SimpleLazyLoadFragment;
import com.homepaas.sls.ui.order.directOrder.AddressManageActivity;
import com.homepaas.sls.ui.widget.web.CommonWebChromeClient;
import com.homepaas.sls.ui.widget.web.CommonWebClient;
import com.homepaas.sls.util.LogUtils;

import javax.inject.Inject;

import butterknife.Bind;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mhy on 2017/12/23.
 */

public class ServiceWebFragment extends SimpleLazyLoadFragment implements DetailServiceWebView {

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.webView_progressbar)
    ProgressBar webViewProgressbar;

    @Inject
    DetailWebViewPresenter detailWebViewPresenter;
    private JavaScriptObject javaScriptObject;
    private String url;
    private String newUrl;
    private static final int CODE_LOGIN = 1;
    public static final String KEY_TYPE = "KeyType";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.i("ServiceWebFragment:onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    public static ServiceWebFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        ServiceWebFragment fragment = new ServiceWebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_web;
    }

    @Override
    protected void initView() {
        LogUtils.i("ServiceWebFragment:initView");
        url = getArguments().getString("url");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        // 开启DOM storage API 功能
        settings.setDomStorageEnabled(true);
//        WebViewCacheUtils.setWebViewCacheConfig(settings);
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebChromeClient(new CommonWebChromeClient(webViewProgressbar) {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);

            }
        });
        webView.setWebViewClient(new CommonWebClient(webView, mContext, webViewProgressbar) {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                }
                return true;
            }


//            @Override
//            public void onPageFinished(WebView view, String url) {//4.4上会多调一次，尽量避免用于操作业务逻辑
//                super.onPageFinished(view, url);
//            }
        });

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        //这里处理返回键事件
                        if (webView.canGoBack()) {
                            webView.goBack();
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        javaScriptObject = new JavaScriptObject((Activity) mContext);
        webView.addJavascriptInterface(javaScriptObject, "android");
        webView.loadUrl(this.url);
    }

    @Override
    protected void initData() {
        LogUtils.i("ServiceWebFragment:initData");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_LOGIN:
                    if (data != null && data.getBooleanExtra("Status", false)) {
                        if (detailWebViewPresenter != null)
                            detailWebViewPresenter.loadPersonalInfo();
                    }
                    break;
                case JavaScriptInteractiveIndex.webChooseAddress:
                    if (data != null && TextUtils.equals("1", data.getStringExtra(KEY_TYPE))) {
                        AddressEntity entity = (AddressEntity) data.getSerializableExtra(AddressManageActivity.KEY_ADDRESS);
                        try {
                            PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();
                            String token = personalInfoPDataSource.getToken();
                            entity.setToken(token);
                        } catch (GetPersistenceDataException e) {
                            e.printStackTrace();
                        }
                        entity.setAppViewId(String.valueOf(JavaScriptInteractiveIndex.webChooseAddress));
                        String address = new Gson().toJson(entity);
                        pushDataToH5(address);
                    }
                    break;
                case JavaScriptInteractiveIndex.loginViewControlller:
                    if (data != null && data.getSerializableExtra("LoginResponse") != null) {
                        LoginBody loginBody = (LoginBody) data.getSerializableExtra("LoginResponse");
                        loginBody.setAppViewId(String.valueOf(JavaScriptInteractiveIndex.loginViewControlller));
                        pushDataToH5(new Gson().toJson(loginBody));
                    }
                    break;
                case JavaScriptInteractiveIndex.loginPopupView:
                    if (data != null && data.getSerializableExtra("LoginResponse") != null) {
                        LoginBody loginBody = (LoginBody) data.getSerializableExtra("LoginResponse");
                        loginBody.setAppViewId(String.valueOf(JavaScriptInteractiveIndex.loginPopupView));
                        pushDataToH5(new Gson().toJson(loginBody));
                    }
                    break;
                case JavaScriptInteractiveIndex.useCouponCenterViewController:
                    if (data != null && data.getSerializableExtra("CouponContents") != null) {
                        CouponContents couponContents = (CouponContents) data.getSerializableExtra("CouponContents");
                        couponContents.setAppViewId(String.valueOf(JavaScriptInteractiveIndex.useCouponCenterViewController));
                        pushDataToH5(new Gson().toJson(couponContents));
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        if (detailWebViewPresenter != null)
            detailWebViewPresenter.destroy();
        LogUtils.i("ServiceWebFragment:onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void showError(Throwable e) {
        if (e instanceof AuthException) {
            showLoginDialog(newUrl);
        } else {
            showMessage(e.getMessage());
        }
    }

    public void showLoginDialog(String json) {
        newUrl = json;
        mNavigator.login((Activity) mContext, CODE_LOGIN);
    }

    public void onSuccessToken(String token) {
        webView.loadUrl("javascript:successGetAndroidToken('" + token + "')");
    }

    public void pushDataToH5(String jsonData) {
        webView.loadUrl("javascript:GetDataFromApp('" + jsonData + "')");
    }


    public void loadNewUrl(String url) {
        newUrl = url;
        new Handler(mContext.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(newUrl);
            }
        });
    }

    @Override
    public void pushTelephone(String telephone) {
        new Handler(mContext.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                loadNewUrl(newUrl);
            }
        });
    }
}
