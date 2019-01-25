package com.homepaas.sls.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.growingio.android.sdk.collection.GrowingIO;
import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerLoginComponent;
import com.homepaas.sls.domain.entity.Account;
import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.mvp.presenter.login.LoginPresenter;
import com.homepaas.sls.mvp.view.login.LoginView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.ColdDownButton;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

import static com.homepaas.sls.common.AccountUtils.isAccountValid;

/**
 * on 2015/12/29 0029
 *
 * @author zhudongjie .
 */
public class LoginDialogFragment extends DialogFragment implements LoginView {

    private static final int REQUEST_CODE = 8;
    @Bind(R.id.close)
    RelativeLayout close;
    @Bind(R.id.register)
    TextView register;
    @Bind(R.id.forget_password)
    TextView forgetPassword;
    @Bind(R.id.phone_text_il)
    TextInputLayout phoneTextIl;
    @Bind(R.id.password_text_il)
    TextInputLayout passwordTextIl;


    public interface OnLoginListener {

        void onLogin();
    }

    public interface OnCloseListener {

        void onClose();
    }
    public interface OnCatchTokenListener {
        void token(String token);
    }
    @Inject
    Navigator mNavigator;

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

    @Bind(R.id.send_auth_code)
    ColdDownButton mSendAuthCode;

    @Inject
    LoginPresenter mPresenter;

    private OnLoginListener mOnLoginListener;
    private OnCloseListener mOnCloseListener;
    private CommonAppPreferences commonAppPreferences;

    public void setOnLoginListener(OnLoginListener onLoginListener) {
        mOnLoginListener = onLoginListener;
    }

    public void setOnCloseListener(OnCloseListener onCloseListener){
        mOnCloseListener=onCloseListener;
    }

    private OnCatchTokenListener onCatchTokenListener;

    public void setOnCatchTokenListener(OnCatchTokenListener onCatchTokenListener) {
        this.onCatchTokenListener = onCatchTokenListener;
    }

    public static LoginDialogFragment show(FragmentActivity activity, OnLoginListener onLoginListener) {
        LoginDialogFragment fragment = new LoginDialogFragment();
        fragment.mOnLoginListener = onLoginListener;
        fragment.show(activity.getSupportFragmentManager(), null);
        return fragment;
    }

    public static LoginDialogFragment show(FragmentActivity parentFragment, OnLoginListener onLoginListener, OnCloseListener onCloseListener) {
        LoginDialogFragment fragment = new LoginDialogFragment();
        fragment.mOnLoginListener = onLoginListener;
        fragment.mOnCloseListener = onCloseListener;
        fragment.show(parentFragment.getSupportFragmentManager(), null);
        return fragment;
    }

    public static LoginDialogFragment show(Fragment parentFragment, OnLoginListener onLoginListener) {
        LoginDialogFragment fragment = new LoginDialogFragment();
        fragment.mOnLoginListener = onLoginListener;
        fragment.show(parentFragment.getFragmentManager(), null);
        return fragment;
    }

    public static LoginDialogFragment showDialog(FragmentActivity activity,OnCatchTokenListener onCatchTokenListener){
        LoginDialogFragment fragment = new LoginDialogFragment();
        fragment.onCatchTokenListener = onCatchTokenListener;
        fragment.show(activity.getSupportFragmentManager(),null);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
        setCancelable(true);//登录窗口必须登录后取消，不得让用户点击外部取消
        commonAppPreferences=new CommonAppPreferences(getActivity());
        inject();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @OnClick(R.id.close)
    public void close() {
        dismiss();
        if(mOnCloseListener!=null){
            mOnCloseListener.onClose();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        mPresenter.setLoginView(this);
        phoneTextIl.setHint(getString(R.string.prompt_phone));
        passwordTextIl.setHint(getString(R.string.prompt_password));
        mAccountView.setText("");
        mPasswordView.setText("");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter.getAccountList();
    }

    private void inject() {
        DaggerLoginComponent.builder()
                .applicationComponent(((CommonBaseActivity) getActivity()).getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
        ButterKnife.unbind(this);
    }

    @OnEditorAction({R.id.password, R.id.auth_code})
    boolean loginAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
            attemptLogin();
            return true;
        }
        return false;
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
        mNavigator.resetPassword(getActivity());
    }

    @OnTextChanged({R.id.account, R.id.password, R.id.auth_code})
    void checkLoginEnable() {
        boolean enabled = !TextUtils.isEmpty(mAccountView.getText().toString()) &&
                !TextUtils.isEmpty(mPasswordView.getText().toString());
        if (mAuthCodeLayout.isShown()) {
            enabled = enabled && !TextUtils.isEmpty(mAuthCodeView.getText().toString());
        }
        mLogin.setEnabled(enabled);
    }

    @OnClick(R.id.send_auth_code)
    void sendAuthCode(View view) {
        if (view.isEnabled()) {
            String account = mAccountView.getText().toString();
            if (isAccountValid(account)) {
                mPresenter.sendAuthCode(account, Constant.CAPTCHA_LOGIN);
            } else {
//                mAccountView.setError(getString(R.string.login_account_format_error));
                showMessage(R.string.login_account_format_error);
                mAccountView.requestFocus();
            }
            throttle(view);
        }
    }

    private void throttle(final View view) {
        if (view != null) {
            view.setClickable(false);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setClickable(true);
                }
            }, 500);
        }
    }


    @Override
    public void popupAccount(List<Account> accountList) {
        if (!accountList.isEmpty()) {
            Account account = accountList.get(0);
            mAccountView.setText(account.getAccount());
            mPasswordView.setText(account.getPassword());
            mAccountView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, getAccountStringList(accountList)));
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
        mLogin.setEnabled(false);
    }



    @Override
    public void login(LoginBody loginBody) {
        if(loginBody!=null) {
            PreferencesUtil.saveBoolean(StaticData.USER_LOGIN_STATUE,true);
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
            dismiss();
            showMessage(R.string.login_success);
            if (mOnLoginListener != null) {
                mOnLoginListener.onLogin();
            }

            if (onCatchTokenListener != null){
                onCatchTokenListener.token(loginBody.getToken());
            }
            EventPersonalInfo info = new EventPersonalInfo(EventPersonalInfo.LOGIN_STATE);
            info.setLogin(true);
            EventBus.getDefault().post(info);
        }
    }

    @Override
    public void coldDown(CaptchaBody captchaBody) {
        showMessage(getString(R.string.login_auth_code_sent, mAccountView.getText().toString()));
        mSendAuthCode.startCold();
    }

    @Override
    public void sendCodeError() {

    }


    private void attemptLogin() {
        mAccountView.setError(null);
        mPasswordView.setError(null);

        String account = mAccountView.getText().toString();
        String password = mPasswordView.getText().toString();

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
            //  mAccountView.setError(getString(R.string.login_account_format_error));
            showMessage(R.string.login_account_format_error);
            focusView = mAccountView;
            cancel = true;
        }

        String authCode = mAuthCodeView.getText().toString();
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
            mPresenter.login(account, password, authCode);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String account = data.getStringExtra(Navigator.REGISTER_ACCOUNT);
            String password = data.getStringExtra(Navigator.REGISTER_PASSWORD);
            mAccountView.setText(account);
            mPasswordView.setText(password);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoading(boolean isCancel) {

    }

    @Override
    public void showLogin() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(@StringRes int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
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
