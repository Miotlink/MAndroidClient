package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.BusinessDetailModule;
import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.di.module.PayModule;
import com.homepaas.sls.di.module.RechargeModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.newdetail.MerchantServiceFragment;
import com.homepaas.sls.ui.newdetail.PayInStoreActivity;
import com.homepaas.sls.ui.newdetail.PayOrderActivity;
import com.homepaas.sls.ui.newdetail.WorkerServiceFragment;

import dagger.Component;

/**
 * Created by JWC on 2017/2/9.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules ={CameraModule.class,PayModule.class} )
public interface BusWorkerServiceComponent {
    void inject(WorkerServiceFragment workerServiceFragment);
    void inject(MerchantServiceFragment merchantServiceFragment);
    void inject(PayOrderActivity payOrderActivity);
    void inject(PayInStoreActivity payInStoreActivity);
}
