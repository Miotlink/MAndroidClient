package com.homepaas.sls.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.homepaas.sls.R;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.AccountComponent;
import com.homepaas.sls.di.component.DaggerAccountComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by Administrator on 2016/11/28.
 */

public class AccountDetailsActivity extends CommonBaseActivity implements HasComponent<AccountComponent> {

    private static final String USER_ID = "user_id";
    private AccountComponent mComponent;

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, AccountDetailsActivity.class);
        intent.putExtra(USER_ID, id);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_list;
    }

    @Override
    protected void initView() {
        int userId = getIntent().getIntExtra(USER_ID, 0);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, AccountDetailsFragment.newInstance(userId))
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
