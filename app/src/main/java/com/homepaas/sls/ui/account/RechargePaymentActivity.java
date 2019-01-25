package com.homepaas.sls.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.homepaas.sls.R;
import com.homepaas.sls.di.component.DaggerRechargeComponent;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.domain.entity.RechargeListExEntity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

/**
 * Created by JWC on 2016/11/30.
 */

public class RechargePaymentActivity extends CommonBaseActivity {

    private static final String RECHARGE = "recharge";

    public static void start(Context context, RechargeListExEntity.RechargeItem recharge) {
        Intent intent = new Intent(context, RechargePaymentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(RECHARGE, recharge);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_list;
    }

    @Override
    protected void initView() {
        RechargeListExEntity.RechargeItem recharge = (RechargeListExEntity.RechargeItem) getIntent().getSerializableExtra(RECHARGE);
        RechargeFragment.RechargeParam param = new RechargeFragment.RechargeParam(
                recharge.getActivityId(), recharge.getNeedPay(), recharge.getTotalMoney(), recharge.getActivity());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, RechargeFragment.newInstance(param))
                .commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerRechargeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cameraModule(new CameraModule(this))
                .build()
                .inject(this);
    }


}
