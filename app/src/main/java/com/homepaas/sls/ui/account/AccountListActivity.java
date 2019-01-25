package com.homepaas.sls.ui.account;

import android.content.Context;
import android.content.Intent;

import com.homepaas.sls.R;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.AccountComponent;
import com.homepaas.sls.di.component.DaggerAccountComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

/**
 * Created by Administrator on 2016/11/28.
 */

public class AccountListActivity extends CommonBaseActivity implements HasComponent<AccountComponent> {

    public static void start(Context context) {
        Intent intent = new Intent(context, AccountListActivity.class);
        context.startActivity(intent);
    }

    private AccountComponent mComponent;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_list;
    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, AccountListFragment.newInstance())
                .commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerAccountComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build()
                .inject(this);
    }


    @Override
    public AccountComponent getComponent() {
        return mComponent;
    }
}
