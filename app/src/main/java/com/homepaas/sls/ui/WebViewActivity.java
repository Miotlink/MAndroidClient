package com.homepaas.sls.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.homepaas.sls.R;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.UrlUtils;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.entity.WebViewDetail;
import com.homepaas.sls.jsinterface.JavaScriptInteractiveIndex;
import com.homepaas.sls.jsinterface.JavaScriptObject;
import com.homepaas.sls.socialization.ShareDialog;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.directOrder.AddressManageActivity;
import com.homepaas.sls.ui.widget.web.CommonWebChromeClient;
import com.homepaas.sls.ui.widget.web.CommonWebClient;
import com.homepaas.sls.ui.widget.web.MyWebView;
import com.homepaas.sls.util.KeyBoardUtil;
import com.umeng.socialize.media.UMImage;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

import static com.homepaas.sls.ui.order.directOrder.AddressManageActivity.KEY_TYPE;

public class WebViewActivity extends CommonBaseActivity {

    public static final String PUSH_KEY = "pushInfo";
    public static final String BANNER_KEY = "bannerInfo";
    private static final int CODE_LOGIN = 1;
    private static final int CODE_REFRESH = 2;

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.fl_share)
    FrameLayout flShare;
    @Bind(R.id.webView)
    MyWebView webView;
    @Bind(R.id.webView_progressbar)
    ProgressBar webViewProgressbar;
    private PushInfo pushIfo;
    private WebViewDetail webViewDetail;
    private String gobackFirstWebStr;
    private String firstUrl;

    //记录URL和title
    private String mCurrentUrl;
    private Map<String, String> titles = new HashMap<String, String>();


    public void pushDataToH5(String jsonData) {
        webView.loadUrl("javascript:GetDataFromApp('" + jsonData + "')");
    }

    public static void start(Context context, PushInfo pushInfo) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PUSH_KEY, pushInfo);
        context.startActivity(intent);
    }

    public static void start(Context context, WebViewDetail webViewDetail) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(BANNER_KEY, webViewDetail);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        pushIfo = (PushInfo) getIntent().getSerializableExtra(PUSH_KEY);
        webViewDetail = (WebViewDetail) getIntent().getSerializableExtra(BANNER_KEY);
    }

    @Override
    protected void initData() {
        initShareLayout();

        webView.addJavascriptInterface(new JavaScriptObject(this), "android");
        if (pushIfo != null) {
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
//            settings.setCacheMode(LOAD_CACHE_ELSE_NETWORK);
            // 开启DOM storage API 功能
            settings.setDomStorageEnabled(true);
//            WebViewCacheUtils.setWebViewCacheConfig(settings);
            webView.setWebChromeClient(new CommonWebChromeClient(webViewProgressbar) {
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    if (WebViewActivity.this.isFinishing()) {
                        return;
                    }
                    // web加载失败的title会变成URL链接，显示不友好  通过title.indexOf(view.getUrl())==-1 进行限制
                    if (!TextUtils.isEmpty(title) && view.getUrl().indexOf(title) == -1 && !WebViewActivity.this.isFinishing()) {
                        tvTitle.setText(title);
                        titles.put(mCurrentUrl, title);
                    }
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
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    mCurrentUrl = url;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (WebViewActivity.this.isFinishing()) {
                        return;
                    }
                    String title = titles.get(url);
                    if (!TextUtils.isEmpty(title) && !WebViewActivity.this.isFinishing()) {
                        tvTitle.setText(title);
                    }
                }
            });
            webView.addJavascriptInterface(new JavaScriptObject(this), "android");
            webView.loadUrl(pushIfo.getUrl());
            firstUrl = pushIfo.getUrl();
//            webView.loadUrl("http://wap.zhujiash.com/activity/BlackFriday/Shuangshier/activity_list.html");
        } else if (webViewDetail != null) {
            if (!TextUtils.isEmpty(webViewDetail.getTitle()))
                tvTitle.setText(webViewDetail.getTitle());
            webView.setWebChromeClient(new CommonWebChromeClient(webViewProgressbar));
            webView.setWebViewClient(new CommonWebClient(webView, this,webViewProgressbar) {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            webView.loadUrl(webViewDetail.getUrl());
        }
    }

    public void initShareLayout() {
        if (pushIfo != null) {
            if (TextUtils.isEmpty(pushIfo.getIsShare()) || TextUtils.equals("0", pushIfo.getIsShare())) {
                flShare.setVisibility(View.INVISIBLE);
            } else {
                flShare.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setShare(String str) {
        pushIfo.setIsShare(str);
        initShareLayout();
    }

    private void share() {
        ShareDialog shareDialog = new ShareDialog(this);
        if (!TextUtils.isEmpty(pushIfo.getSharePic())) {
            shareDialog.setImage(new UMImage(this, pushIfo.getSharePic()));
        } else if (pushIfo.getShareResoures() != 0) {
            shareDialog.setImage(new UMImage(this, BitmapFactory.decodeResource(getResources(), pushIfo.getShareResoures())));
        }
        if (pushIfo != null) {
            if (!TextUtils.isEmpty(pushIfo.getShareDescription()))
                shareDialog.setText(pushIfo.getShareDescription());
            if (!TextUtils.isEmpty(pushIfo.getShareUrl()))
                shareDialog.setUrl(pushIfo.getShareUrl());
            if (!TextUtils.isEmpty(pushIfo.getShareTitle()))
                shareDialog.setTitle(pushIfo.getShareTitle());
        } else {
            shareDialog.setText(webViewDetail.getShareDescription());
            shareDialog.setUrl(webViewDetail.getShareUrl());
            shareDialog.setTitle(webViewDetail.getShareTitle());
        }

        shareDialog.show();
    }

    public void onSuccessToken(String token) {
        webView.loadUrl("javascript:successGetAndroidToken('" + token + "')");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_LOGIN:
                    if (data != null && data.getBooleanExtra("Status", false)) {
                        String token = data.getStringExtra("TOKEN");
                        onSuccessToken(token);
                    }

                    break;
                case CODE_REFRESH:
                    if (data != null && data.getBooleanExtra("Status", false))
                        webView.loadUrl(newUrl);
                    break;
                case JavaScriptInteractiveIndex.webChooseAddress:
                    if (data != null && TextUtils.equals("1", data.getStringExtra(KEY_TYPE))) {
                        AddressEntity entity = (AddressEntity) data.getSerializableExtra(AddressManageActivity.KEY_ADDRESS);
                        try {
                            String token2 = personalInfoPDataSource.getToken();
                            entity.setToken(token2);
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

    public void showLoginDialog() {
        mNavigator.login(WebViewActivity.this, CODE_LOGIN);
    }

    private String newUrl;

    public void loadNewUrl(String token) {
        newUrl = UrlUtils.domain() + "/template/my-reward.html?token=" + token;
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(newUrl);
            }
        });
    }

    /**
     * 调用浏览器
     */
    private void callExplore() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(pushIfo.getUrl()));
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    public void showLoginDialog(String json) {
        newUrl = json;
        mNavigator.login(WebViewActivity.this, CODE_REFRESH);
    }

    public void reLoadUrl(String url) {
        newUrl = url;
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(newUrl);
            }
        });
    }

    public void goBackFirstWeb(String s) {
        gobackFirstWebStr = s;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭输入法
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
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


    @OnClick({R.id.back_ll, R.id.fl_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                KeyBoardUtil.hideActivityKeyboard(this);
                finish();
                break;
            case R.id.fl_share:
                share();
                break;
        }
    }
}
