package com.homepaas.sls.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.growingio.android.sdk.collection.GrowingIO;
import com.homepaas.sls.R;
import com.homepaas.sls.data.network.Url;
import com.homepaas.sls.di.component.DaggerRegisterComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.domain.entity.RegisterBody;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.event.RefreshPersonalCenterFragmentEvent;
import com.homepaas.sls.event.RegisterSuccessEvent;
import com.homepaas.sls.jsinterface.JavaScriptObject;
import com.homepaas.sls.mvp.presenter.login.RegisterPresenter;
import com.homepaas.sls.mvp.view.login.RegisterView;
import com.homepaas.sls.ui.MainActivity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.ColdDownButton;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.homepaas.sls.common.AccountUtils.isAccountValid;
import static com.homepaas.sls.common.AccountUtils.isPasswordValid;


public class RegisterActivity extends CommonBaseActivity implements RegisterView {

    @Bind(R.id.account)
    EditText mAccountView;

    @Bind(R.id.password)
    EditText mPasswordView;

    @Bind(R.id.auth_code)
    EditText mAuthCodeView;

    @Bind(R.id.protocol)
    TextView mProtocol;

    @Bind(R.id.account_register_button)
    Button mRegister;

    @Inject
    RegisterPresenter mPresenter;

    @Bind(R.id.send_auth_code)
    ColdDownButton mSendAuthCode;
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    @Bind(R.id.recommend_code)
    EditText recommendCode;
    private String recommend_code;
    private CommonAppPreferences commonAppPreferences;


    private ClickableSpan mProtocolSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            mNavigator.loadWebView(RegisterActivity.this, Url.HTM_BASE_URL_DEFAULT + "yhfwxy.htm", "用户协议");
        }
    };
    private String jsinteractor;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        commonAppPreferences=new CommonAppPreferences(this);
        Intent intent = getIntent();
        recommend_code = intent.getStringExtra("code");
        if (!TextUtils.isEmpty(recommend_code)){
            recommendCode.setText(recommend_code);
        }
        SpannableString spannableString = new SpannableString(getString(R.string.register_prompt));
        spannableString.setSpan(mProtocolSpan, spannableString.length() - 6, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mProtocol.setLinkTextColor(ActivityCompat.getColor(this, R.color.appPrimary));
        mProtocol.setMovementMethod(LinkMovementMethod.getInstance());

        mProtocol.setText(spannableString);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mPresenter.setRegisterView(this);
        jsinteractor = getIntent().getStringExtra(JavaScriptObject.JSInteractor);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerRegisterComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.account_register_button)
    void register() {
        mAccountView.setError(null);
        mPasswordView.setError(null);

        String account = mAccountView.getText().toString();
        String password = mPasswordView.getText().toString();
        String authCode = mAuthCodeView.getText().toString();
        String invitationCode = recommendCode.getText().toString().trim();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            //      mPasswordView.setError(getString(R.string.login_password_format_error));
            showMessage(R.string.login_password_format_error);
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid phone number.
//        if (TextUtils.isEmpty(account)) {
//            mAccountView.setError(getString(R.string.login_account_format_error));
//            focusView = mAccountView;
//            cancel = true;
//        } else
        if (!isAccountValid(account)) {
            //  mAccountView.setError(getString(R.string.login_account_format_error));
            showMessage(R.string.login_account_format_error);
            focusView = mAccountView;
            cancel = true;
        }

//        if (!TextUtils.isEmpty(account) && !isAuthCodeValid(authCode)) {
//            mAuthCodeView.setError(getString(R.string.login_auth_code_format_error));
//            focusView = mAuthCodeView;
//            cancel = true;
//        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mPresenter.Register(account, password, authCode, invitationCode);
        }

    }

    @OnClick(R.id.send_auth_code)
    void sendAuthCode(View view) {
        if (view.isEnabled()) {
            String account = mAccountView.getText().toString();
            if (isAccountValid(account)) {
                mPresenter.sendAuthCode(account);
            } else {
                //mAccountView.setError(getString(R.string.login_account_format_error));
                showMessage(R.string.login_account_format_error);
                mAccountView.requestFocus();
            }
            throttleClickable(view);
        }
    }

    @OnTextChanged({R.id.account, R.id.password, R.id.auth_code})
    void checkEmpty() {
        boolean enabled = !TextUtils.isEmpty(mAccountView.getText().toString())
                && !TextUtils.isEmpty(mPasswordView.getText().toString())
                && !TextUtils.isEmpty(mAuthCodeView.getText().toString());
        mRegister.setEnabled(enabled);
    }



    @Override
    public void onRegisterSucceed(RegisterBody response) {
        imLogout();
        showMessage(R.string.login_register_success);
//        Intent intent = getIntent();
//        intent.putExtra(Navigator.REGISTER_ACCOUNT, mAccountView.getText().toString());
//        intent.putExtra(Navigator.REGISTER_PASSWORD, mPasswordView.getText().toString());
//        setResult(RESULT_OK, intent);
//        ActivityCompat.finishAfterTransition(this);
        //重新onCreate
        if (!TextUtils.isEmpty(response.getUserId())){
            GrowingIO growingIO = GrowingIO.getInstance();
            growingIO.setCS1("user_id", response.getUserId());
        }
        if(!TextUtils.isEmpty(response.getImUserName())&&!TextUtils.isEmpty(response.getImPwd())){
            commonAppPreferences.setImInfo(response.getImUserName(),response.getImPwd());
        }
        if(!TextUtils.isEmpty(response.getSmallPic())){
            commonAppPreferences.setHeadPortrait(response.getSmallPic());
        }
        if (jsinteractor != null) {//js交互逻辑，获取token
            EventBus.getDefault().post(new RegisterSuccessEvent(response.getToken(), jsinteractor));
            ActivityCompat.finishAfterTransition(this);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //自动登录
            EventPersonalInfo event = new EventPersonalInfo(EventPersonalInfo.LOGIN_STATE);
            RefreshPersonalCenterFragmentEvent refreshEvent = new RefreshPersonalCenterFragmentEvent();
            event.setLogin(true);
            EventBus.getDefault().post(event);
            EventBus.getDefault().post(refreshEvent);
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public void coldDown(CaptchaBody captchaBody) {
        showMessage(getString(R.string.login_auth_code_sent, mAccountView.getText().toString()));
        mSendAuthCode.startCold();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void imLogout(){
        commonAppPreferences.setHeadPortrait("");
        commonAppPreferences.setImInfo("","");
        ChatClient.getInstance().logout(true, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(int i, String s) {
                ChatClient.getInstance().logout(false, new Callback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError(int i, String s) {
                    }
                    @Override
                    public void onProgress(int i, String s) {
                    }
                });
            }
            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
}
