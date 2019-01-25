package com.homepaas.sls.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.homepaas.sls.Global;
import com.homepaas.sls.R;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.event.CloseDialogEvent;
import com.homepaas.sls.event.LoginSuccessEvent;
import com.homepaas.sls.event.RegisterSuccessEvent;
import com.homepaas.sls.event.ZoomActivity;
import com.homepaas.sls.jsinterface.JavaScriptInteractiveIndex;
import com.homepaas.sls.jsinterface.JavaScriptObject;
import com.homepaas.sls.jsinterface.entity.Command;
import com.homepaas.sls.socialization.ShareDialog;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.directOrder.AddressManageActivity;
import com.homepaas.sls.ui.widget.HorizontalProgressBarWithNumber;
import com.homepaas.sls.util.WebViewCacheUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.homepaas.sls.ui.order.directOrder.AddressManageActivity.KEY_TYPE;

/**
 * Created by Administrator on 2016/7/12.
 */

public class FirstOpenAppActivity extends CommonBaseActivity {

    private static final String TAG = "FirstOpenAppActivity";
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.progress)
    HorizontalProgressBarWithNumber progress;
    @Bind(R.id.fl)
    FrameLayout fl;
    @Bind(R.id.close)
    ImageView close;
    @Bind(R.id.close_zoom)
    ImageView closeZoom;

    private String uniqueUrl = null;
    private String UNIQUE_URL = "UNIQUE_URL";
    private String token;
    private boolean loadFinish;

    @Inject
    Global global;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, FirstOpenAppActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);

    }
    public void pushDataToH5(String jsonData){
        webView.loadUrl("javascript:GetDataFromApp('" + jsonData + "')");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode) {
                case JavaScriptInteractiveIndex.webChooseAddress:
                    if (data != null && TextUtils.equals("1",data.getStringExtra(KEY_TYPE))){
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
                    if (data != null && data.getSerializableExtra("LoginResponse") != null){
                        LoginBody loginBody = (LoginBody) data.getSerializableExtra("LoginResponse");
                        loginBody.setAppViewId(String.valueOf(JavaScriptInteractiveIndex.loginViewControlller));
                        pushDataToH5(new Gson().toJson(loginBody));
                    }
                    break;
                case JavaScriptInteractiveIndex.loginPopupView:
                    if (data != null && data.getSerializableExtra("LoginResponse") != null){
                        LoginBody loginBody = (LoginBody) data.getSerializableExtra("LoginResponse");
                        loginBody.setAppViewId(String.valueOf(JavaScriptInteractiveIndex.loginPopupView));
                        pushDataToH5(new Gson().toJson(loginBody));
                    }
                    break;
                case JavaScriptInteractiveIndex.useCouponCenterViewController:
                    if (data != null && data.getSerializableExtra("CouponContents") != null){
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
    protected int getLayoutId() {
        return R.layout.activity_first_open;
    }

    @Override
    protected void initView() {
        setFinishOnTouchOutside(false);
        ButterKnife.bind(this);
        close.setVisibility(View.GONE);
        closeZoom.setVisibility(View.GONE);
        EventBus.getDefault().register(this);
        uniqueUrl = getIntent().getStringExtra("url");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        // 开启DOM storage API 功能
        settings.setDomStorageEnabled(true);
//        WebViewCacheUtils.setWebViewCacheConfig(settings);
        webView.setBackgroundColor(0);
        webView.getBackground().setAlpha(0);

        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setScrollContainer(false);
        settings.setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                WebViewActivity.start(FirstOpenAppActivity.this,firstOpenAppInfo.getTitle(),url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadFinish = true;

            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
                //bugly----Javascript的异常捕获功能
                CrashReport.setJavascriptMonitor(webView, true);
                progress.setProgress(newProgress);
                if (newProgress >= 100) {
                    webView.setVisibility(View.VISIBLE);
                    close.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                }

            }
        });
        webView.addJavascriptInterface(new JavaScriptObject(this), "android");
        webView.loadUrl(uniqueUrl);

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!loadFinish) {
                    EventBus.getDefault().post(new CloseDialogEvent());
                    FirstOpenAppActivity.this.finish();
                }
            }
        }, 15000);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.close)
    public void closePage() {
        EventBus.getDefault().post(new CloseDialogEvent());
        this.finish();
    }

    @OnClick(R.id.close_zoom)
    public void close() {
        EventBus.getDefault().post(new CloseDialogEvent());
        this.finish();
    }

    @Override
    protected void initializeInjector() {
        getApplicationComponent().inject(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccess(LoginSuccessEvent event) {
        webView.loadUrl(event.data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(ZoomActivity event) {
        EventBus.getDefault().post(new CloseDialogEvent());
        this.finish();
//        close.setVisibility(View.GONE);
        //原本是变大效果，执行的就是这里变大,需求改变后直接到有title的webview
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        FrameLayout.LayoutParams layoutParmas = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        fl.setLayoutParams(layoutParmas);
//        FrameLayout.LayoutParams Params = (FrameLayout.LayoutParams) webView.getLayoutParams();
//        Params.height = displayMetrics.heightPixels;
//        Params.width = displayMetrics.widthPixels;
//        webView.setLayoutParams(Params);

//        closeZoom.setVisibility(View.VISIBLE);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterSuccess(final RegisterSuccessEvent event) {
        token = event.token;
        webView.loadUrl(event.url);
    }

    public void onSuccessToken(String token) {
        webView.loadUrl("javascript:successGetAndroidToken('" + token + "')");
    }

    public void onSuccessGetTicket(final String url) {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(url);
            }
        });
    }

    public void onSuccessGetHongbao() {
        //获取红包后,下次不需要再弹了
        SharedPreferences preferences = getContext().getSharedPreferences(global.getFirstPrefNewcomerActive(), Context.MODE_PRIVATE);
        preferences.edit().putInt(global.PREF_KEY_FOR_NEWCOMER_ACTIVE, global.MAX_TIMES_DISPLAY_FOR_NEWCOMER_ACTIVE).apply();
    }

    public void share(final Activity activity, final Command shareInfo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ShareDialog shareDialog = new ShareDialog(activity);
                shareDialog.setImage(new UMImage(activity, shareInfo.getData().getPic()));
                shareDialog.setTitle(shareInfo.getData().getTitle());
                shareDialog.setText(shareInfo.getData().getMessage());
                shareDialog.setUrl(shareInfo.getData().getUrl());
                shareDialog.show();
            }
        });
    }

}
