package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.di.module.RechargeModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.account.RechargeChooseActivity;
import com.homepaas.sls.ui.account.RechargeFragment;
import com.homepaas.sls.ui.account.RechargeListFragment;
import com.homepaas.sls.ui.account.RechargePaymentActivity;
import com.homepaas.sls.ui.account.RechargeSuccActivity;

import dagger.Component;

/**
 * on 2016/6/22 0022
 *
 * @author zhudongjie
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules ={CameraModule.class,RechargeModule.class} )
public interface RechargeComponent {

    void inject(RechargeListFragment fragment);

    void inject(RechargeFragment fragment);
    void inject(RechargePaymentActivity activity);

    void inject(RechargeChooseActivity activity);
    void inject(RechargeSuccActivity activity);
}
