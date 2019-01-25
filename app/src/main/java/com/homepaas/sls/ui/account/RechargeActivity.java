package com.homepaas.sls.ui.account;

import android.support.v7.widget.Toolbar;

import com.homepaas.sls.R;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.DaggerRechargeComponent;
import com.homepaas.sls.di.component.RechargeComponent;
import com.homepaas.sls.domain.entity.Recharge;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;

/**
 * 充值页面
 */
public class RechargeActivity extends CommonBaseActivity implements RechargeListFragment.OnFragmentItemClickListener, HasComponent<RechargeComponent> {

    private RechargeComponent component;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
            RechargeListFragment fragment = new RechargeListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        DaggerRechargeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemClick(Recharge recharge) {
//        RechargeFragment.RechargeParam param = new RechargeFragment.RechargeParam(
//                recharge.getTotalMoney(), recharge.getNeedPay(), recharge.getDescription(), recharge.getActivity());
//        RechargeFragment fragment = RechargeFragment.newInstance(param);
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.container, fragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .addToBackStack(null)
//                .commit();
    }

    @Override
    public RechargeComponent getComponent() {
        return component;
    }
}
