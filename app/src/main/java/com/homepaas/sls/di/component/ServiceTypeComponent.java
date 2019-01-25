package com.homepaas.sls.di.component;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.ServiceTypeModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.order.orderplace.FlatServiceTypeActivity;
import com.homepaas.sls.ui.order.orderplace.NewFlatServiceTypeActivity;

import dagger.Component;

/**
 * Created by Administrator on 2016/4/27.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ServiceTypeModule.class, ActivityModule.class})
public interface ServiceTypeComponent {

    void inject(FlatServiceTypeActivity activity);
    void inject(NewFlatServiceTypeActivity activity);
}
