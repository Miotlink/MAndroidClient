package com.homepaas.sls.ui.login;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.homepaas.sls.R;
import com.homepaas.sls.common.AccountUtils;
import com.homepaas.sls.di.component.ResetPasswordComponent;
import com.homepaas.sls.mvp.presenter.login.ResetPasswordStepTwoPresenter;
import com.homepaas.sls.mvp.view.login.ResetPasswordStepTwoView;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 填写新密码界面
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordStepTwoFragment extends CommonBaseFragment implements ResetPasswordStepTwoView {

    private static final String PHONE_KEY = "phone";

    private static final String CAPTCHA_KEY = "captcha";

    private String mPhone;

    private String mCaptcha;

    @Bind(R.id.password)
    EditText mPassword;

    @Bind(R.id.password_confirm)
    EditText mPasswordConfirm;

    @Bind(R.id.confirm_button)
    Button mButton;

    @Inject
    ResetPasswordStepTwoPresenter mPresenter;

    private OnPasswordResetListener mOnPasswordResetListener;


    public static ResetPasswordStepTwoFragment newInstance(String phone, String captcha) {
        ResetPasswordStepTwoFragment fragment = new ResetPasswordStepTwoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PHONE_KEY, phone);
        bundle.putString(CAPTCHA_KEY, captcha);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPasswordResetListener) {
            mOnPasswordResetListener = (OnPasswordResetListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhone = getArguments().getString(PHONE_KEY);
            mCaptcha = getArguments().getString(CAPTCHA_KEY);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_modify_password;
    }

    @Override
    protected void initView() {
        mPresenter.setResetPasswordStepTwoView(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        getComponent(ResetPasswordComponent.class)
                .inject(this);
    }

    @OnClick(R.id.confirm_button)
    void resetPassword() {
        mPassword.setError(null);
        mPasswordConfirm.setError(null);

        String password = mPassword.getText().toString();
        String passwordConfirm = mPasswordConfirm.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!AccountUtils.isPasswordValid(password)){
            showMessage(getString(R.string.login_password_format_error));
            cancel = true;
            focusView = mPassword;
        }
        if (!AccountUtils.isPasswordValid(passwordConfirm)){
           showMessage(getString(R.string.login_password_format_error));
            cancel = true;
            focusView = mPasswordConfirm;
        }

        if (!TextUtils.equals(password,passwordConfirm)){
           showMessage(getString(R.string.login_two_passwords_not_the_same));
            cancel = true;
            focusView = mPasswordConfirm;
        }
        if (cancel){
            focusView.requestFocus();
        }else {
            mPresenter.confirmNewPassword(mPhone, mCaptcha, password);
        }
    }


    @OnTextChanged({R.id.password,R.id.password_confirm})
    void checkConfirmEnabled(){
        boolean disabled = TextUtils.isEmpty(mPassword.getText().toString())||TextUtils.isEmpty(mPasswordConfirm.getText().toString());
        mButton.setEnabled(!disabled);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnPasswordResetListener = null;
    }

    @Override
    public void passwordReset() {
        if (mOnPasswordResetListener != null) {
            mOnPasswordResetListener.onPasswordReset();
        }
    }

    public interface OnPasswordResetListener {

        void onPasswordReset();
    }
}
