package com.homepaas.sls.ui.account;

import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.homepaas.sls.R;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.AccountComponent;
import com.homepaas.sls.di.component.DaggerAccountComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/28.
 */

public class WalletActivity extends CommonBaseActivity implements HasComponent<AccountComponent> {

    @Bind(R.id.back_ll)
    LinearLayout backLl;
    @Bind(R.id.container)
    FrameLayout container;


    private AccountComponent mComponent;



    @Override
    protected void initializeInjector() {
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, WalletFragment.newInstance())
                .commit();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.back_ll)
    void onClick() {
        finish();
    }
}
