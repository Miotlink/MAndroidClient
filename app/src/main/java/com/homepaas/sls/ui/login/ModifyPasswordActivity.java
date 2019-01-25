package com.homepaas.sls.ui.login;

import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.homepaas.sls.R;
import com.homepaas.sls.common.AccountUtils;
import com.homepaas.sls.di.component.DaggerModifyPasswordComponent;
import com.homepaas.sls.mvp.presenter.login.ModifyPasswordPresenter;
import com.homepaas.sls.mvp.view.login.ModifyPasswordView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ModifyPasswordActivity extends CommonBaseActivity implements ModifyPasswordView {

    @Bind(R.id.old_password)
    EditText mOldPassword;

    @Bind(R.id.new_password)
    EditText mNewPassword;

    @Bind(R.id.confirm_button)
    Button mButton;

    @Inject
    ModifyPasswordPresenter mPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPresenter.setView(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerModifyPasswordComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @OnClick(R.id.confirm_button)
    void modifyPassword() {
        mOldPassword.setError(null);
        mNewPassword.setError(null);

        String oldPassword = mOldPassword.getText().toString();
        String newPassword = mNewPassword.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (!AccountUtils.isPasswordValid(oldPassword)) {
            showMessage(getString(R.string.login_password_format_error));
            focusView = mOldPassword;
            cancel = true;
        }
        if (!AccountUtils.isPasswordValid(newPassword)) {
            showMessage(getString(R.string.login_password_format_error));
            focusView = mNewPassword;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mPresenter.modifyPassword(oldPassword, newPassword);
        }
    }

    @OnTextChanged({R.id.old_password, R.id.new_password})
    void checkEmpty() {
        boolean enabled = !TextUtils.isEmpty(mOldPassword.getText().toString()) &&
                !TextUtils.isEmpty(mNewPassword.getText().toString());
        mButton.setEnabled(enabled);
    }

    @Override
    public void passwordModified() {
        showMessage("密码修改成功！");
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
