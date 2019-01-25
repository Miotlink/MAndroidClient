package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.BusinessDetailModule;
import com.homepaas.sls.di.module.CallLogModule;
import com.homepaas.sls.di.module.ShareModule;
import com.homepaas.sls.di.module.WorkerDetailModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.detail.BusinessMessageFragment;
import com.homepaas.sls.ui.detail.WorkerMessageFragment;
import com.homepaas.sls.ui.newdetail.MerchantDetailFragment;
import com.homepaas.sls.ui.newdetail.MerchantWorkerActivity;
import com.homepaas.sls.ui.newdetail.NewCustomerReviewFragment;
import com.homepaas.sls.ui.newdetail.NewTwoCustomerFragment;

import dagger.Component;

/**
 * Created by Administrator on 2017/2/8.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ShareModule.class,WorkerDetailModule.class,BusinessDetailModule.class, CallLogModule.class})
public interface MerchantWorkerComponet {
    void inject(NewCustomerReviewFragment newCustomerReviewFragment);
    void inject(MerchantWorkerActivity merchantWorkerActivity);
    void inject(MerchantDetailFragment merchantDetailFragment);
    void inject(BusinessMessageFragment fragment);
    void inject(WorkerMessageFragment fragment);
    void inject(NewTwoCustomerFragment fragment);
}
