package com.homepaas.sls.ui.personalcenter.more;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.homepaas.sls.R;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.Url;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.jsinterface.JavaScriptInteractiveIndex;
import com.homepaas.sls.jsinterface.JavaScriptObject;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.order.directOrder.AddressManageActivity;
import com.homepaas.sls.ui.widget.NewCallDialogFragment;
import com.homepaas.sls.ui.widget.web.CommonWebChromeClient;
import com.homepaas.sls.ui.widget.web.CommonWebClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.homepaas.sls.R.id.webView;
import static com.homepaas.sls.ui.order.directOrder.AddressManageActivity.KEY_TYPE;

public class GeneralWebViewActivity extends CommonBaseActivity {

    @Bind(webView)
    WebView mWebView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.webView_progressbar)
    ProgressBar webViewProgressbar;

    private static final int REQUEST_PERMISSION_CALL_AND_CALL_LOG = 3;
    private String mCallingPhone;
    private String mTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_general_web_view;
    }

    @Override
    protected void initView() {

        String title = getIntent().getStringExtra(Navigator.WEB_VIEW_TITLE);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);

        String url = getIntent().getStringExtra(Navigator.WEB_VIEW_URL);

        if (TextUtils.isEmpty(url)) {
            url = Url.HTM_BASE_URL_DEFAULT + "gyss.htm";
        }
        Log.e("url",url);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//设置使用够执行JS脚本
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        // 开启DOM storage API 功能
        settings.setDomStorageEnabled(true);
//        WebViewCacheUtils.setWebViewCacheConfig(settings);
        mWebView.setWebViewClient(new CommonWebClient(mWebView, this,webViewProgressbar) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                if (url.startsWith("tel:")) {
                    String phoneNumber = url.substring(4, url.length());
                    dial(phoneNumber, "客服电话");
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        mWebView.setWebChromeClient(new CommonWebChromeClient(webViewProgressbar));
        mWebView.addJavascriptInterface(new JavaScriptObject(this), "android");
        mWebView.loadUrl(url);
    }

    @Override
    protected void initData() {

    }

    public void pushDataToH5(String jsonData) {
        mWebView.loadUrl("javascript:GetDataFromApp('" + jsonData + "')");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!mWebView.canGoBack()) {
            onBackPressed();
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    // 拨打电话
    private void dial(String phone, String title) {
        List<String> permissions = new ArrayList<>(2);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CALL_PHONE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            //Service 中无法回调onRequestPermissionsResult，提前请求
            permissions.add(Manifest.permission.READ_CALL_LOG);
        }
        if (permissions.isEmpty()) {
            NewCallDialogFragment serviceFragment = NewCallDialogFragment.newInstance(phone, title);
            serviceFragment.show(getSupportFragmentManager(), null);
        } else {
            mCallingPhone = phone;
            mTitle = title;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION_CALL_AND_CALL_LOG);
            }
        }
    }
}
