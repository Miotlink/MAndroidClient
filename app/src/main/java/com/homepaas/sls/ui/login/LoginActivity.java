package com.homepaas.sls.ui.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.growingio.android.sdk.collection.GrowingIO;
import com.homepaas.sls.R;
import com.homepaas.sls.data.BuildConfig;
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
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.ChangeEnvironmentDialog;
import com.homepaas.sls.ui.widget.ColdDownButton;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.ui.widget.customview.AddSpaceTextWatcher;
import com.homepaas.sls.ui.widget.verify.VerfiyCodeDialogFragment;
import com.homepaas.sls.util.PermissionUtils;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.runtimepermission.acp.AcpListener;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

import static com.homepaas.sls.common.AccountUtils.isAccountValid;

/**
 * A login screen that offers login via phone/password.
 */
public class LoginActivity extends CommonBaseActivity implements LoginView, VerfiyCodeDialogFragment.OnVerifyInviladListener {

    private static final int REQUEST_CODE = 257;
    @Bind(R.id.phone_text_il)
    TextInputLayout phoneTextIl;
    @Bind(R.id.password_text_il)
    TextInputLayout passwordTextIl;
    private long startTime = 0;
    private int clickCount = 0;
    private static final String TAG = "LoginActivity";


    @Bind(R.id.account)
    AutoCompleteTextView mAccountView;

    @Bind(R.id.password)
    EditText mPasswordView;

    @Bind(R.id.auth_code_layout)
    ViewGroup mAuthCodeLayout;

    @Bind(R.id.auth_code)
    EditText mAuthCodeView;

    @Bind(R.id.account_sign_in_button)
    Button mLogin;

    @Inject
    LoginPresenter mPresenter;
    private String account,password,authCode;

    private VerfiyCodeDialogFragment verfiyCodeDialogFragment;

    @Bind(R.id.send_auth_code)
    ColdDownButton mSendAuthCode;
    private String jsInteractor;
    private CommonAppPreferences commonAppPreferences;
    private AddSpaceTextWatcher addSpaceTextWatcher;

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_no, R.anim.anim_bottom_out);
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        commonAppPreferences=new CommonAppPreferences(this);
        addSpaceTextWatcher =new AddSpaceTextWatcher(mAccountView,13);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
        phoneTextIl.setHint(getString(R.string.prompt_phone));
        passwordTextIl.setHint(getString(R.string.prompt_password));
        mAccountView.setText("");
        mPasswordView.setText("");
        mPresenter.setLoginView(this);
        mPresenter.getAccountList();
        jsInteractor = getIntent().getStringExtra(JavaScriptObject.JSInteractor);
    }

    @Override
    protected void initData() {

    }

    @OnEditorAction({R.id.password, R.id.auth_code})
    boolean loginAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
            attemptLogin();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }

    @OnClick(R.id.close)
    public void close(){
//        onBackPressed();
        finish();
    }

    @OnClick(R.id.account_sign_in_button)
    void loginClick() {
        attemptLogin();
    }


    @OnClick(R.id.register)
    void registerAccount() {
        mNavigator.register(this, REQUEST_CODE);
    }

    @OnClick(R.id.forget_password)
    void resetPassword() {
        mNavigator.resetPassword(this);
    }

    @OnTextChanged({R.id.account, R.id.password, R.id.auth_code})
    void checkLoginEnable() {
        boolean enabled = !TextUtils.isEmpty(addSpaceTextWatcher.getTextNotSpace()) &&
                !TextUtils.isEmpty(mPasswordView.getText().toString());
        if (mAuthCodeLayout.isShown()) {
            enabled = enabled && !TextUtils.isEmpty(addSpaceTextWatcher.getTextNotSpace());
        }
        mLogin.setEnabled(enabled);
    }
    private SharedPreferences sharedPreferences;
    @OnClick(R.id.send_auth_code)
    void sendAuthCode(View view) {
        if (view.isEnabled()) {
            String account = addSpaceTextWatcher.getTextNotSpace();
            if (isAccountValid(account)) {
//                if (clickCountVerfy == 0 ) {
//                    startTimeVerfy= System.currentTimeMillis();
//                }
//                clickCountVerfy++;
//                if (clickCountVerfy == 3) {
//                    if ((System.currentTimeMillis() - startTimeVerfy) < 180000) {
//                        sharedPreferences = getApplicationContext().getSharedPreferences("QuickLogin",MODE_PRIVATE);
//                        sharedPreferences.edit().putBoolean("Need_Image_Verify",true).commit();
//                    }
//                    clickCountVerfy = 0;
//                    startTimeVerfy = 0;
//                }

                String countId = getSharedPreferences(StaticData.SEND_CAPTCH_COUNT,MODE_PRIVATE).getString(StaticData.CAPTCH_COUNT_ID,"0");
                String countPhone = getSharedPreferences(StaticData.SEND_CAPTCH_COUNT,MODE_PRIVATE).getString(StaticData.CAPTCH_COUNT_PHONE,"0");
                if (countId.compareTo("2") <= 0 || countPhone.compareTo("2") <= 0){
                    mPresenter.sendAuthCode(account, Constant.CAPTCHA_LOGIN);
                } else {
                    verfiyCodeDialogFragment = VerfiyCodeDialogFragment.newInstance();
                    verfiyCodeDialogFragment.setOnVerifyInviladListener(this);
                    verfiyCodeDialogFragment.show(getSupportFragmentManager(),null);
                }

            } else {
                //mAccountView.setError(getString(R.string.login_account_format_error));
                showMessage(R.string.login_account_format_error);
                mAccountView.requestFocus();
            }
            throttleClickable(view);
        }
    }
    @Override
    public void onVerifyInvilad(boolean isVerifyInvilad) {
        getApplicationContext().getSharedPreferences("QuickLogin",MODE_PRIVATE).edit().putBoolean("Need_Image_Verify",false).commit();
        mPresenter.sendAuthCode(addSpaceTextWatcher.getTextNotSpace(), Constant.CAPTCHA_LOGIN);
        verfiyCodeDialogFragment.dismissAllowingStateLoss();
    }
    @OnClick(R.id.email_login_form)
    public void changeEnvrioment() {
        closeInputMethod();

        Log.i(TAG, "changeEnvrioment: ");
        if (BuildConfig.DEBUG) {
            if (clickCount == 0) {
                startTime = System.currentTimeMillis();
            }
            clickCount++;
            if (clickCount == 5) {
                if ((System.currentTimeMillis() - startTime) < 1500) {
//                    restApiHelper.ChangeApi();
//                    Url.setHtmBaseUrlDefault();
//                    showMessage("目前是正式环境啦，可以开始使用稳定的功能啦~~~");
                    ChangeEnvironmentDialog dialog = new ChangeEnvironmentDialog(this, restApiHelper, personalInfoPDataSource);
                    dialog.show();
                }
                clickCount = 0;
                startTime = 0;
            }

        }
    }

    @Override
    public void popupAccount(List<Account> accountList) {
        if (!accountList.isEmpty()) {
            Account account = accountList.get(0);
            mAccountView.setText(account.getAccount());
            mPasswordView.setText(account.getPassword());
            mAccountView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getAccountStringList(accountList)));
            mAccountView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // TODO: 2016/6/7 0007 密码自动填写
                    mPasswordView.setText("");
                    mPasswordView.requestFocus();
                }
            });
        } else {
            mAccountView.setText("");
            mPasswordView.setText("");
        }
    }

    private List<String> getAccountStringList(List<Account> accountList) {
        List<String> list = new ArrayList<>(accountList.size());
        for (int i = 0; i < accountList.size(); i++) {
            String account = accountList.get(i).getAccount();
            if (!list.contains(account))
                list.add(account);
        }
        return list;
    }

    @Override
    public void showAuthCodeInput() {
        showMessage(R.string.login_not_normal_device);
        mPasswordView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mAuthCodeLayout.setVisibility(View.VISIBLE);
        //此时验证码为空
        mLogin.setEnabled(false);
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

    private SharedPreferences newUserPref;

    @Override
    public void login(LoginBody loginBody) {
        //IM 欢迎语打开
        PreferencesUtil.saveBoolean(StaticData.IM_WELCOME,false);

        if(loginBody!=null) {
            PreferencesUtil.saveBoolean(StaticData.USER_LOGIN_STATUE,true);
            if (TextUtils.equals("1",loginBody.getNewUser())){
                newUserPref = getApplicationContext().getSharedPreferences(Constant.IS_NEW_USER_PRF,MODE_PRIVATE);
                newUserPref.edit().putBoolean(Constant.IS_NEW_USER_FIELD,true).commit();
                newUserPref.edit().putString(Constant.NEW_USER_MESSAGE,loginBody.getNewUserCouponMessage()).commit();
            }

            imLogout();
            if (!TextUtils.isEmpty(loginBody.getUserId())) {
                GrowingIO growingIO = GrowingIO.getInstance();
                growingIO.setCS1("user_id", loginBody.getUserId());
            }
            if(!TextUtils.isEmpty(loginBody.getImUserName())&&!TextUtils.isEmpty(loginBody.getImPwd())){
                commonAppPreferences.setImInfo(loginBody.getImUserName(),loginBody.getImPwd());
            }
            if(!TextUtils.isEmpty(loginBody.getSmallPic())){
                commonAppPreferences.setHeadPortrait(loginBody.getSmallPic());
            }
            Intent intent = new Intent();
            intent.putExtra("LoginResponse",loginBody);
            intent.putExtra("TOKEN",loginBody.getToken());
            intent.putExtra(StaticData.LOGIN_STATUE,true);
            setResult(RESULT_OK,intent);
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
    private SharedPreferences captchPref;
    @Override
    public void coldDown(CaptchaBody captchaBody) {
        if (captchaBody != null){
            captchPref = getSharedPreferences(StaticData.SEND_CAPTCH_COUNT,MODE_PRIVATE);
            captchPref.edit().putString(StaticData.CAPTCH_COUNT_ID ,captchaBody.getCountIp()).commit();
            captchPref.edit().putString(StaticData.CAPTCH_COUNT_PHONE ,captchaBody.getCountPhone()).commit();
        }
        showMessage(getString(R.string.login_auth_code_sent, addSpaceTextWatcher.getTextNotSpace()));
        mSendAuthCode.startCold();
    }

    @Override
    public void sendCodeError() {

    }


    private void attemptLogin() {
        mAccountView.setError(null);
        mPasswordView.setError(null);

         account = addSpaceTextWatcher.getTextNotSpace();
         password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
//        if (!isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.login_password_format_error));
//            focusView = mPasswordView;
//            cancel = true;
//        }

        // Check for a valid phone number.
     /*   if (TextUtils.isEmpty(account)) {
            mAccountView.setError(getString(R.string.login_account_format_error));
            focusView = mAccountView;
            cancel = true;
        } else*/
        if (!isAccountValid(account)) {
            // mAccountView.setError(getString(R.string.login_account_format_error));
            showMessage(R.string.login_account_format_error);
            focusView = mAccountView;
            cancel = true;
        }

         authCode = mAuthCodeView.getText().toString();
//        if (mAuthCodeLayout.getVisibility() == View.VISIBLE) {
//            authCode = mAuthCodeView.getText().toString();
//            focusView = mAuthCodeView;
//            if (!TextUtils.isEmpty(account) && !isAuthCodeValid(authCode)) {
//                mAuthCodeView.setError(getString(R.string.login_auth_code_format_error));
//                cancel = true;
//            }
//        }

        if (cancel) {
            focusView.requestFocus();
        } else {
//            //获取手机状态权限
//            AndPermission.with(mContext)
//                    .requestCode(200)
//                    .permission(
//                            Manifest.permission.READ_PHONE_STATE
//                    )
//                    .callback(listener)
//                    .start();
            PermissionUtils.requestPhoneLocation(mContext, new AcpListener() {
                @Override
                public void onGranted() {
                    mPresenter.login(account, password, authCode);
                }

                @Override
                public void onDenied(List<String> permissions) {

                }
            });
        }
    }

    public void checkPermission() {
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
        StringBuffer stringBuffer = new StringBuffer("您拒绝了必要的");
        if (AndPermission.hasPermission(mContext,  Manifest.permission.READ_PHONE_STATE)) {
            mPresenter.login(account, password, authCode);
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
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String account = data.getStringExtra(Navigator.REGISTER_ACCOUNT);
            String password = data.getStringExtra(Navigator.REGISTER_PASSWORD);
            mAccountView.setText(account);
            mPasswordView.setText(password);
        }
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

}

