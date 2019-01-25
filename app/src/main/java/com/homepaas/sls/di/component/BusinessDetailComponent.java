package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.BusinessDetailModule;
import com.homepaas.sls.di.module.CallLogModule;
import com.homepaas.sls.di.module.ShareModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.detail.BusinessDetailFragment;
import com.homepaas.sls.ui.detail.BusinessMessageFragment;
import com.homepaas.sls.ui.merchantservice.detail.MerchantDetailActivity;
import com.homepaas.sls.ui.newdetail.MerchantDetailFragment;

import dagger.Component;

/**
 * on 2016/1/20 0020
 *
 * @author zhudongjie .
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ShareModule.class,BusinessDetailModule.class, CallLogModule.class,ActivityModule.class})
public interface BusinessDetailComponent {

    void inject(BusinessMessageFragment fragment);

    void inject(BusinessDetailFragment fragment);

}
