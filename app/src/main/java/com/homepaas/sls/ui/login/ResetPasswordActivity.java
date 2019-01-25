package com.homepaas.sls.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.homepaas.sls.R;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.DaggerResetPasswordComponent;
import com.homepaas.sls.di.component.ResetPasswordComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.ui.MainActivity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

/**
 * 进行密码修改和密码重置相关功能
 *
 * @author zhudongjie
 */
public class ResetPasswordActivity extends CommonBaseActivity implements HasComponent<ResetPasswordComponent>
        , ResetPasswordStepTwoFragment.OnPasswordResetListener, ResetPasswordStepOneFragment.OnResetPasswordListener {

    private ResetPasswordComponent mOperatorComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password_operate;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Fragment fragment = new ResetPasswordStepOneFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commitAllowingStateLoss();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        mOperatorComponent = DaggerResetPasswordComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }


    @Override
    public ResetPasswordComponent getComponent() {
        return mOperatorComponent;
    }

    @Override
    public void onPasswordReset() {
        showMessage(R.string.login_modify_password_success);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void afterRequestResetPasswordSucceed(String phone,String authCode) {
        Fragment fragment =  ResetPasswordStepTwoFragment.newInstance(phone,authCode);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
//                    .addSharedElement()
//                    .setCustomAnimations()
                .commitAllowingStateLoss();
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
