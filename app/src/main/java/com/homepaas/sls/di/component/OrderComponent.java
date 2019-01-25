package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.DescriptionModule;
import com.homepaas.sls.di.module.OrderInfoModule;
import com.homepaas.sls.di.module.PlaceOrderModule;
import com.homepaas.sls.di.module.ServiceModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.order.detail.DetailOrderActivity;
import com.homepaas.sls.ui.order.directOrder.OrderDetailsActivity;
import com.homepaas.sls.ui.order.manage.OrderAllFragment;
import com.homepaas.sls.ui.order.manage.OrderManageFragment;
import com.homepaas.sls.ui.order.manage.OrderToConfirmFragment;
import com.homepaas.sls.ui.order.manage.OrderToEvaluateFragment;
import com.homepaas.sls.ui.order.manage.OrderToPayFragment;
import com.homepaas.sls.ui.order.manage.OrderToTakeFragment;
import com.homepaas.sls.ui.order.pay.DirectPayActivity;
import com.homepaas.sls.ui.order.pay.PaySuccessInfoActivity;

import dagger.Component;

/**
 * Created by Administrator on 2016/5/3.
 *
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = {PlaceOrderModule.class,OrderInfoModule.class, DescriptionModule.class, ServiceModule.class})
public interface OrderComponent {

    void inject(OrderAllFragment fragment);
    void inject(OrderToConfirmFragment fragment);
    void inject(OrderToEvaluateFragment fragment);
    void inject(OrderToPayFragment fragment);
    void inject(DetailOrderActivity activity);
    void inject(DirectPayActivity activity);
    void inject(PaySuccessInfoActivity paySuccessInfoActivity);
    void inject(OrderDetailsActivity orderDetailsActivity);
    void inject(OrderToTakeFragment orderToTakeFragment);
    void inject(OrderManageFragment fragment);

}
