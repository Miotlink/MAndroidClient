package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.DescriptionModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.order.orderplace.PlaceOrderActivity;

import dagger.Component;

/**
 * Created by CJJ on 2016/7/13.
 */
@Component(dependencies = ApplicationComponent.class,modules = {DescriptionModule.class})
@ActivityScope
public interface DescriptionComponent {

//    void inject(PlaceOrderActivity currentAction);
}
