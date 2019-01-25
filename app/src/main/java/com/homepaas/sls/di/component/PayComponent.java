package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.DescriptionModule;
import com.homepaas.sls.di.module.PayModule;
import com.homepaas.sls.di.module.ServiceModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.order.orderplace.OrderPayActivity;
import com.homepaas.sls.ui.order.pay.PayActivity;

import dagger.Component;

/**
 * Created by CMJ on 2016/6/25.
 *
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = {PayModule.class, DescriptionModule.class, ServiceModule.class})
public interface PayComponent{
//订单支付页面
    void inject(PayActivity activity);
//下单并填写金额后的订单支付页
    void inject(OrderPayActivity orderPayActivity);
}
