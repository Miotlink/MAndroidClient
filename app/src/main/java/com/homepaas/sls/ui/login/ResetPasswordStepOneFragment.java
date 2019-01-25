package com.homepaas.sls.ui.login;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.homepaas.sls.R;
import com.homepaas.sls.common.AccountUtils;
import com.homepaas.sls.di.component.ResetPasswordComponent;
import com.homepaas.sls.domain.entity.CaptchaBody;
import com.homepaas.sls.mvp.presenter.login.ResetPasswordPresenter;
import com.homepaas.sls.mvp.view.login.ResetPasswordOneView;
import com.homepaas.sls.ui.common.fragment.CommonBaseFragment;
import com.homepaas.sls.ui.widget.ColdDownButton;
import com.homepaas.sls.ui.widget.verify.VerfiyCodeDialogFragment;
import com.homepaas.sls.util.StaticData;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static android.content.Context.MODE_PRIVATE;

/**
 * 请求重置密码界面
 */
public class ResetPasswordStepOneFragment extends CommonBaseFragment implements ResetPasswordOneView, VerfiyCodeDialogFragment.OnVerifyInviladListener {


    @Bind(R.id.account)
    EditText mAccount;

    @Bind(R.id.auth_code)
    EditText mAuthCode;

    @Bind(R.id.confirm_button)
    Button mConfirmButton;

    @Bind(R.id.send_auth_code)
    ColdDownButton mSendAuthCode;

    @Inject
    ResetPasswordPresenter mPresenter;

    private OnResetPasswordListener mOnResetPasswordListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnResetPasswordListener) {
            mOnResetPasswordListener = (OnResetPasswordListener) context;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reset_password;
    }

    @Override
    protected void initView() {
        mPresenter.setResetPasswordOneView(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initializeInjector() {
        getComponent(ResetPasswordComponent.class)
                .inject(this);
    }
    private VerfiyCodeDialogFragment verfiyCodeDialogFragment;
    @OnClick(R.id.send_auth_code)
    void sendAuthCode(View view) {
        if (view.isEnabled()) {
            String account = mAccount.getText().toString();
            if (!AccountUtils.isAccountValid(account)) {
                showMessage(getString(R.string.login_account_format_error));
                mAccount.requestFocus();
            } else {

                String countId = getActivity().getSharedPreferences(StaticData.SEND_CAPTCH_COUNT,MODE_PRIVATE).getString(StaticData.CAPTCH_COUNT_ID,"0");
                String countPhone = getActivity().getSharedPreferences(StaticData.SEND_CAPTCH_COUNT,MODE_PRIVATE).getString(StaticData.CAPTCH_COUNT_PHONE,"0");
                if (countId.compareTo("2") <= 0 || countPhone.compareTo("2") <= 0){
                    mPresenter.sendAuthCode(account);
                } else {
                    verfiyCodeDialogFragment = VerfiyCodeDialogFragment.newInstance();
                    verfiyCodeDialogFragment.setOnVerifyInviladListener(this);
                    verfiyCodeDialogFragment.show(getFragmentManager(),null);
                }

            }
            throttleClickable(view);
        }
    }

    @OnTextChanged({R.id.account, R.id.auth_code})
    void checkConfirmEnabled() {
        boolean enabled = !TextUtils.isEmpty(mAccount.getText().toString())
                && !TextUtils.isEmpty(mAuthCode.getText().toString());
        mConfirmButton.setEnabled(enabled);
    }

    @OnClick(R.id.confirm_button)
    void confirm() {
        String account = mAccount.getText().toString();
        String authCode = mAuthCode.getText().toString();

        mAccount.setError(null);
        mAuthCode.setError(null);
        boolean cancel = false;
        View focusView = null;

        if (!AccountUtils.isAuthCodeValid(authCode)) {
            showMessage(getString(R.string.login_auth_code_format_error));
            cancel = true;
            focusView = mAuthCode;
        }

        if (!AccountUtils.isAccountValid(account)) {
            showMessage(getString(R.string.login_account_format_error));
            cancel = true;
            focusView = mAccount;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            mPresenter.requestResetPassword(account, authCode);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnResetPasswordListener = null;
    }


    @Override
    public void showNewPasswordView() {
        if (mOnResetPasswordListener != null) {
            String account = mAccount.getText().toString();
            String authCode = mAuthCode.getText().toString();
            mOnResetPasswordListener.afterRequestResetPasswordSucceed(account, authCode);
        }
    }
    private SharedPreferences captchPref;
    @Override
    public void coldDown(CaptchaBody captchaBody) {
        if (captchaBody != null){
            captchPref = getActivity().getSharedPreferences(StaticData.SEND_CAPTCH_COUNT,MODE_PRIVATE);
            captchPref.edit().putString(StaticData.CAPTCH_COUNT_ID ,captchaBody.getCountIp()).commit();
            captchPref.edit().putString(StaticData.CAPTCH_COUNT_PHONE ,captchaBody.getCountPhone()).commit();
        }
        showMessage(getString(R.string.login_auth_code_sent,mAccount.getText().toString()));
        mSendAuthCode.startCold();
    }

    @Override
    public void onVerifyInvilad(boolean isVerifyInvilad) {
        mPresenter.sendAuthCode(mAccount.getText().toString().trim());
        verfiyCodeDialogFragment.dismissAllowingStateLoss();
    }

    public interface OnResetPasswordListener {

        void afterRequestResetPasswordSucceed(String phone, String captcha);
    }
}
