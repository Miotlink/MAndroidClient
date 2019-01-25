package com.homepaas.sls.ui.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.growingio.android.sdk.collection.GrowingIO;
import com.homepaas.sls.R;
import com.homepaas.sls.common.AccountUtils;
import com.homepaas.sls.data.BuildConfig;
import com.homepaas.sls.data.network.Url;
import com.homepaas.sls.di.component.DaggerLoginComponent;
import com.homepaas.sls.domain.entity.Account;
import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.event.LoginSuccessEvent;
import com.homepaas.sls.jsinterface.JavaScriptObject;
import com.homepaas.sls.mvp.presenter.login.LoginPresenter;
import com.homepaas.sls.mvp.view.login.LoginView;
import com.homepaas.sls.ui.MainActivity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.ChangeEnvironmentDialog;
import com.homepaas.sls.ui.widget.ColdDownButton;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.ui.widget.customview.AddSpaceTextWatcher;
import com.homepaas.sls.ui.widget.verify.VerfiyCodeDialogFragment;
import com.homepaas.sls.util.PermissionUtils;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.SpanUtils;
import com.homepaas.sls.util.StaticData;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.runtimepermission.acp.AcpListener;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.homepaas.sls.R.id.phone_code_input;

public class FastLoginActivity extends CommonBaseActivity implements LoginView, VerfiyCodeDialogFragment.OnVerifyInviladListener, ColdDownButton.OnResetListener {

    private static final String TAG = "FastLoginActivity";
    @Bind(R.id.close)
    ImageView close;
    @Bind(R.id.pwd_login)
    TextView pwdLogin;
    @Bind(R.id.toolbar)
    RelativeLayout toolbar;
    @Bind(R.id.phone_input)
    EditText phoneInput;
    @Bind(R.id.send_auth_code)
    ColdDownButton sendAuthCode;
    @Bind(R.id.login_button)
    Button loginButton;
    @Bind(R.id.input_ll)
    LinearLayout inputLl;
    @Bind(R.id.protocol)
    TextView protocol;
    @Bind(R.id.root)
    CoordinatorLayout root;
    @Bind(R.id.ly_root)
    LinearLayout lyRoot;
    @Bind(R.id.phone_code_input)
    EditText phoneCodeInput;
    @Bind(R.id.img_clear_phone)
    ImageView imgClearPhone;

    @Inject
    LoginPresenter mPresenter;
    private AddSpaceTextWatcher addSpaceTextWatcher;
    private VerfiyCodeDialogFragment verfiyCodeDialogFragment;
    private CommonAppPreferences commonAppPreferences;
    private SharedPreferences newUserPref;
    private SharedPreferences captchPref;

    private static final int CODE_LOGIN_PWD = 1;
    private String jsInteractor;
    private long startTimeChange = 0;
    private int clickCountChange = 0;
    private boolean isLoginOutTime = false;//是否是登录超时进入

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_no, R.anim.anim_bottom_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeInputMethod();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fast_login;
    }

    @Override
    protected void initView() {
        isLoginOutTime = getIntent().getBooleanExtra("isLoginOutTime", false);
        commonAppPreferences = new CommonAppPreferences(this);
        String linkText = "《助家生活用户协议》";
        SpannableString spannableString = SpanUtils.setSpanColorAndClick(getResources().getColor(R.color.login_line), getResources().getString(R.string.login1), linkText, new SpanUtils.SpanClickListener() {
            @Override
            public void onSpanClick() {
                mNavigator.loadWebView(FastLoginActivity.this, Url.HTM_BASE_URL_DEFAULT + "yhfwxy.htm", "用户协议");
            }
        });
        protocol.setText(spannableString);
        protocol.setMovementMethod(LinkMovementMethod.getInstance());
        addSpaceTextWatcher = new AddSpaceTextWatcher(phoneInput, 13);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
        sendAuthCode.setOnResetListener(this);
        phoneInput.setText("");
        phoneInput.setFocusable(true);
        phoneInput.setFocusableInTouchMode(true);
        phoneInput.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        mPresenter.setLoginView(this);
        mPresenter.getAccountList();
        jsInteractor = getIntent().getStringExtra(JavaScriptObject.JSInteractor);

//        phoneInput.addTextChangedListener(new MyTextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                loginButton.setEnabled(true);
////                String account = addSpaceTextWatcher.getTextNotSpace();
////                String captcha = phoneCodeInput.getText().toString().trim();
////                sendAuthCode.setEnabled(!TextUtils.isEmpty(account) && AccountUtils.isAccountValid(account));
//////        clear.setVisibility((!TextUtils.isEmpty(account) && !sendAuthCode.isCounting()) ? View.VISIBLE : View.GONE);
////                phoneInput.setFocusable(!sendAuthCode.isCounting());
////                loginButton.setEnabled(!(TextUtils.isEmpty(account) || TextUtils.isEmpty(captcha) || !AccountUtils.isAuthCodeValid(captcha)));
//                if (!TextUtils.isEmpty(s.toString())) {
//                    imgClearPhone.setVisibility(View.VISIBLE);
//                    loginBtnEnable();
//                } else {
//                    imgClearPhone.setVisibility(View.GONE);
//                }
//            }
//        });

//        phoneCodeInput.addTextChangedListener(new MyTextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!TextUtils.isEmpty(s.toString())) {
//                    loginBtnEnable();
//                }
//            }
//        });
    }

    public void loginBtnEnable() {
        if (phoneInput.getText().toString().trim().length() == 11 && phoneCodeInput.getText().toString().trim().length() == 4) {
            loginButton.setEnabled(true);
        } else {
            loginButton.setEnabled(false);
        }
    }

    @Override
    protected void initData() {

    }

    private void imLogout() {
        commonAppPreferences.setHeadPortrait("");
        commonAppPreferences.setImInfo("", "");
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

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    //
    @OnTextChanged({R.id.phone_input, phone_code_input})
    public void checkLoginEnable() {
        loginButton.setEnabled(true);
        String account = addSpaceTextWatcher.getTextNotSpace();
        String captcha = phoneCodeInput.getText().toString().trim();
        sendAuthCode.setEnabled(!TextUtils.isEmpty(account) && AccountUtils.isAccountValid(account)&&!sendAuthCode.isCounting());
        imgClearPhone.setVisibility((!TextUtils.isEmpty(account) && !sendAuthCode.isCounting()) ? View.VISIBLE : View.GONE);
        loginButton.setEnabled(!(TextUtils.isEmpty(account) || TextUtils.isEmpty(captcha) || !AccountUtils.isAuthCodeValid(captcha)));

    }

    @Override
    public View getSnackBarHolderView() {
        return root;
    }

    @OnClick(R.id.ly_root)
    public void changeEnvrioment() {
        closeInputMethod();
        Log.i(TAG, "changeEnvrioment: ");
        if (BuildConfig.DEBUG) {
            if (clickCountChange == 0) {
                startTimeChange = System.currentTimeMillis();
            }
            clickCountChange++;
            if (clickCountChange == 5) {
                if ((System.currentTimeMillis() - startTimeChange) < 1500) {
//                    restApiHelper.ChangeApi();
//                    Url.setHtmBaseUrlDefault();
//                    showMessage("目前是正式环境啦，可以开始使用稳定的功能啦~~~");
                    ChangeEnvironmentDialog dialog = new ChangeEnvironmentDialog(this, restApiHelper, personalInfoPDataSource);
                    dialog.show();
                }
                clickCountChange = 0;
                startTimeChange = 0;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 400: { // 这个400就是上面defineSettingDialog()的第二个参数。
                // 你可以在这里检查你需要的权限是否被允许，并做相应的操作。
                checkPermission();
                break;
            }
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_LOGIN_PWD:
                    if (data != null) {
                        LoginBody loginBody = (LoginBody) data.getSerializableExtra("LoginResponse");
                        login(loginBody);
                    } else {//没有登录的状况下不传值
                        onBackPressed();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        closeInputMethod();
        if (isLoginOutTime) {
            startActivity(new Intent(FastLoginActivity.this, MainActivity.class));
            finish();
            //登录超时
        } else {
            Intent intent = new Intent();
            intent.putExtra(StaticData.LOGIN_STATUE, false);
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
    }

    @Override
    protected void retrieveData() {
        super.retrieveData();
    }

    @OnClick({R.id.close, R.id.pwd_login, R.id.send_auth_code, R.id.login_button})
    public void controller(View view) {
        switch (view.getId()) {
            case R.id.close:
                onBackPressed();
                break;
            case R.id.pwd_login:
                Intent intent = new Intent(FastLoginActivity.this, LoginActivity.class);
                startActivityForResult(intent, CODE_LOGIN_PWD);
                overridePendingTransition(R.anim.anim_bottom_in, R.anim.anim_no);
                break;
            case R.id.send_auth_code:
                String countId = getSharedPreferences(StaticData.SEND_CAPTCH_COUNT, MODE_PRIVATE).getString(StaticData.CAPTCH_COUNT_ID, "0");
                String countPhone = getSharedPreferences(StaticData.SEND_CAPTCH_COUNT, MODE_PRIVATE).getString(StaticData.CAPTCH_COUNT_PHONE, "0");
                if (countId.compareTo("2") <= 0 || countPhone.compareTo("2") <= 0) {
                    mPresenter.sendAuthCode(addSpaceTextWatcher.getTextNotSpace(), Constant.CAPTCHA_QUICK_LOGIN);
                } else {
                    verfiyCodeDialogFragment = VerfiyCodeDialogFragment.newInstance();
                    verfiyCodeDialogFragment.setOnVerifyInviladListener(this);
                    verfiyCodeDialogFragment.show(getSupportFragmentManager(), null);
                }
                break;
            case R.id.login_button:
                PermissionUtils.requestPhoneLocation(mContext, new AcpListener() {
                    @Override
                    public void onGranted() {
                        mPresenter.quickLogin(addSpaceTextWatcher.getTextNotSpace(), phoneCodeInput.getText().toString().trim());
                    }

                    @Override
                    public void onDenied(List<String> permissions) {

                    }
                });
//                //获取手机状态权限
//                AndPermission.with(mContext)
//                        .requestCode(200)
//                        .permission(
//                                Manifest.permission.READ_PHONE_STATE
//                        )
//                        .callback(listener)
//                        .start();
                break;
        }
    }


    public void checkPermission() {
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
        StringBuffer stringBuffer = new StringBuffer("您拒绝了必要的");
        if (AndPermission.hasPermission(mContext,  Manifest.permission.READ_PHONE_STATE)) {
            mPresenter.quickLogin(addSpaceTextWatcher.getTextNotSpace(), phoneCodeInput.getText().toString().trim());
            return;
        }
        if (!AndPermission.hasPermission(mContext, Manifest.permission.READ_PHONE_STATE)) {
            stringBuffer.append("电话");
        }
        stringBuffer.append("权限，请在设置中授权！");
        // 使用AndPermission提供的默认设置dialog，用户点击确定后会打开App的设置页面让用户授权。
        // 第二种：用自定义的提示语。
        AndPermission.defaultSettingDialog((Activity) mContext, 400)
                .setTitle("权限申请失败")
                .setMessage(stringBuffer.toString())
                .setPositiveButton("好，去设置")
                .show();
        // 建议：自定义这个Dialog，提示具体需要开启什么权限，自定义Dialog具体实现上面有示例代码。
    }



    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {
                //部分中国厂商生产手机（例如vivo、pppo某型号）在用户允许权限，并且回调了权限授权成功的方法，但是实际执行代码时并没有这个权限，
                // 建议在成功的回调房中调用AppOpsManager做权限判断： if(AndPermission.hasPermission()) {// 有权限。}
                checkPermission();
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                //部分中国厂商生产手机（例如小米、华为某型号）在申请权限时，用户点击确定授权后，还是回调我们申请失败，这个时候其实我们是拥有权限的，
                // 建议在失败的回调房中调用AppOpsManager做权限判断： if(AndPermission.hasPermission()) {// 执行操作。}
                checkPermission();
            }
        }
    };
    @Override
    public void popupAccount(List<Account> accountList) {
        if (!accountList.isEmpty()) {
            Account account = accountList.get(0);
            phoneInput.setText(account.getAccount());
            imgClearPhone.setVisibility(View.VISIBLE);
            phoneCodeInput.requestFocus();
        } else {
            phoneInput.setText("");
        }
    }

    @Override

    public void showAuthCodeInput() {
        //快速登录不提示异常设备
    }


    @Override
    public void login(LoginBody loginBody) {
        if (loginBody != null) {
            //IM 欢迎语打开
            PreferencesUtil.saveBoolean(StaticData.IM_WELCOME, false);

            PreferencesUtil.saveBoolean(StaticData.USER_LOGIN_STATUE, true);
            if (TextUtils.equals("1", loginBody.getNewUser())) {
                newUserPref = getApplicationContext().getSharedPreferences(Constant.IS_NEW_USER_PRF, MODE_PRIVATE);
                newUserPref.edit().putBoolean(Constant.IS_NEW_USER_FIELD, true).commit();
                newUserPref.edit().putString(Constant.NEW_USER_MESSAGE, loginBody.getNewUserCouponMessage()).commit();
            }

            imLogout();
            if (!TextUtils.isEmpty(loginBody.getUserId())) {
                GrowingIO growingIO = GrowingIO.getInstance();
                growingIO.setCS1("user_id", loginBody.getUserId());
            }
            if (!TextUtils.isEmpty(loginBody.getImUserName()) && !TextUtils.isEmpty(loginBody.getImPwd())) {
                commonAppPreferences.setImInfo(loginBody.getImUserName(), loginBody.getImPwd());
            }
            if (!TextUtils.isEmpty(loginBody.getSmallPic())) {
                commonAppPreferences.setHeadPortrait(loginBody.getSmallPic());
            }

            Intent intent = new Intent();
            intent.putExtra("LoginResponse", loginBody);
            intent.putExtra("TOKEN", loginBody.getToken());
            intent.putExtra(StaticData.LOGIN_STATUE, true);
            setResult(RESULT_OK, intent);

            if (jsInteractor != null) {//js交互的登陆回调逻辑
                EventBus.getDefault().post(new LoginSuccessEvent(jsInteractor));
                EventPersonalInfo info = new EventPersonalInfo(EventPersonalInfo.LOGIN_STATE);
                info.setLogin(true);
                EventBus.getDefault().post(info);

                this.finish();
            } else {//普通登陆逻辑

//                ActivityCompat.finishAfterTransition(this);

                showMessage(R.string.login_success);
                EventPersonalInfo info = new EventPersonalInfo(EventPersonalInfo.LOGIN_STATE);
                info.setLogin(true);
                EventBus.getDefault().post(info);
//                overridePendingTransition(R.anim.anim_no, R.anim.anim_bottom_out);
                finish();
            }
        }
    }


    @Override
    public void coldDown(CaptchaBody captchaBody) {
        if (captchaBody != null) {
            captchPref = getSharedPreferences(StaticData.SEND_CAPTCH_COUNT, MODE_PRIVATE);
            captchPref.edit().putString(StaticData.CAPTCH_COUNT_ID, captchaBody.getCountIp()).commit();
            captchPref.edit().putString(StaticData.CAPTCH_COUNT_PHONE, captchaBody.getCountPhone()).commit();
        }

        showMessage(getString(R.string.login_auth_code_sent, addSpaceTextWatcher.getTextNotSpace()));
        sendAuthCode.startCold();
//        phoneCodeInput.setFocusable(true);
//        phoneCodeInput.setFocusableInTouchMode(true);
        phoneCodeInput.requestFocus();
//        phoneInput.setFocusable(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }

    @Override
    public void sendCodeError() {
        phoneCodeInput.setText("");
    }

    @Override
    public void onVerifyInvilad(boolean isVerifyInvilad) {
//        getApplicationContext().getSharedPreferences("QuickLogin",MODE_PRIVATE).edit().putBoolean("Need_Image_Verify",false).commit();
        mPresenter.sendAuthCode(addSpaceTextWatcher.getTextNotSpace(), Constant.CAPTCHA_QUICK_LOGIN);
        verfiyCodeDialogFragment.dismissAllowingStateLoss();
    }

    @Override
    public void onReset() {
//        clear.setVisibility((!TextUtils.isEmpty(addSpaceTextWatcher.getTextNotSpace()) && !sendAuthCode.isCounting()) ? View.VISIBLE : View.GONE);
        phoneInput.setFocusable(!sendAuthCode.isCounting());
    }

    @OnClick(R.id.img_clear_phone)
    public void onViewClicked() {
        imgClearPhone.setVisibility(View.GONE);
        phoneInput.setText("");
        phoneInput.requestFocus();
    }
}
