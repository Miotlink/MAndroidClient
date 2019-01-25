package com.homepaas.sls.ui.homepage_3_3_0;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.homepaas.sls.R;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.di.component.DaggerPersonalInfoComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.PersonalInfoModule;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.jsinterface.JavaScriptInteractiveIndex;
import com.homepaas.sls.jsinterface.JavaScriptObject;
import com.homepaas.sls.mvp.presenter.DetailWebViewPresenter;
import com.homepaas.sls.mvp.view.DetailServiceWebView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.imchating.ImLoginActivity;
import com.homepaas.sls.ui.login.LoginDialogFragment;
import com.homepaas.sls.ui.order.directOrder.AddressManageActivity;
import com.homepaas.sls.ui.widget.web.CommonWebChromeClient;
import com.homepaas.sls.ui.widget.web.CommonWebClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

import static com.homepaas.sls.ui.order.directOrder.AddressManageActivity.KEY_TYPE;

public class DetailWebActivity extends CommonBaseActivity implements DetailServiceWebView {

    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.call_secretary)
    ImageView callSecretary;
    @Bind(R.id.unreadIcon)
    View unreadIcon;
    @Bind(R.id.message)
    FrameLayout message;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.toolbar)
    RelativeLayout toolbar;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.webView_progressbar)
    ProgressBar webViewProgressbar;
    private ServiceItem serviceItem;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    DetailWebViewPresenter detailWebViewPresenter;
    private LoginDialogFragment loginDialogFragment;
    private JavaScriptObject javaScriptObject;

    public static void start(Context context, ServiceItem serviceItem) {
        Intent intent = new Intent(context, DetailWebActivity.class);
        intent.putExtra("serviceItem", serviceItem);
        context.startActivity(intent);
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerPersonalInfoComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .personalInfoModule(new PersonalInfoModule())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_web;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        serviceItem = (ServiceItem) getIntent().getSerializableExtra("serviceItem");
        detailWebViewPresenter.setDetailServiceWebView(this);

        mTitle.setText(serviceItem.getServiceName());

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
//                mTitle.setText(title);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);

            }
        });
        webView.setWebViewClient(new CommonWebClient(webView, this,webViewProgressbar) {

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


            @Override
            public void onPageFinished(WebView view, String url) {//4.4上会多调一次，尽量避免用于操作业务逻辑
                super.onPageFinished(view, url);
//                mTitle.setText(view.getTitle());
            }
        });
        javaScriptObject = new JavaScriptObject(this);
        webView.addJavascriptInterface(javaScriptObject, "android");
        webView.loadUrl(serviceItem.getDetailUrl());

//        webView.loadUrl("http://192.168.1.191:3001/Web/detail-page/yuesao.html");

        //有特殊类型的服务需要调整到其他界面，为了方便直接在此界面进行跳转
//        ServiceMerchantActivity.start(mContext,serviceItem);
//        finish();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.back, R.id.message})
    public void opreate(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.message:
                //进入客服页面
                ImLoginActivity.start(this, serviceItem);
//                finish();
//                Str       ing json = "{\"Meta\":{\"ErrorCode\":\"0\",\"ErrorMsg\":\"\"},\"Body\":{\"ObjectApns\":{\"Type\":\"1\",\"AppViewId\":\"1003\"}}}";
//                javaScriptObject.androidWebJsHandle(json);
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!webView.canGoBack()) {
            onBackPressed();
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailWebViewPresenter.destroy();
        EventBus.getDefault().unregister(this);
    }


    public void onSuccessToken(String token) {
        webView.loadUrl("javascript:successGetAndroidToken('" + token + "')");
    }

    public void pushDataToH5(String jsonData) {
        webView.loadUrl("javascript:GetDataFromApp('" + jsonData + "')");
    }

    @Override
    protected void retrieveData() {
        super.retrieveData();
        onSuccessToken(getToken());

    }

    private static final int CODE_LOGIN = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_LOGIN:
                    if (data != null && data.getBooleanExtra("Status", false))
                        detailWebViewPresenter.loadPersonalInfo();
                    break;
                case JavaScriptInteractiveIndex.webChooseAddress:
                    if (data != null && TextUtils.equals("1", data.getStringExtra(KEY_TYPE))) {
                        AddressEntity entity = (AddressEntity) data.getSerializableExtra(AddressManageActivity.KEY_ADDRESS);
                        try {
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


    public void showLoginDialog(String json) {
        newUrl = json;
        mNavigator.login(DetailWebActivity.this, CODE_LOGIN);
//        if (loginDialogFragment != null && loginDialogFragment.getDialog() != null && loginDialogFragment.getDialog().isShowing() ){//check dialogfragment is showing
//                return;
//        } else {
//            loginDialogFragment = LoginDialogFragment.showDialog(this, new LoginDialogFragment.OnCatchTokenListener() {
//                @Override
//                public void token(String token) {
//                    detailWebViewPresenter.loadPersonalInfo();
//                }
//            });
//
//        }
    }

    @Override
    public void showError(Throwable e) {
        if (e instanceof AuthException) {
            showLoginDialog(newUrl);
        } else {
            showMessage(e.getMessage());
        }

    }

    private String newUrl;

    public void loadNewUrl(String url) {
        newUrl = url;
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(newUrl);
            }
        });
    }

    @Override
    public void pushTelephone(String telephone) {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                loadNewUrl(newUrl);
            }
        });
    }

    @Subscribe
    public void onEvent(EventPersonalInfo event) {
    }
}
