package com.homepaas.sls.di.component;

import com.homepaas.sls.di.module.CameraModule;
import com.homepaas.sls.di.module.DescriptionModule;
import com.homepaas.sls.di.module.OrderInfoModule;
import com.homepaas.sls.di.module.PlaceOrderModule;
import com.homepaas.sls.di.module.ServiceModule;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.ui.imchating.ImLoginActivity;
import com.homepaas.sls.ui.order.chooseservice.ChooseServiceActivity;
import com.homepaas.sls.ui.order.chooseservice.NewChooseServiceActivity;
import com.homepaas.sls.ui.order.chooseservice.TitleServiceActivity;
import com.homepaas.sls.ui.order.directOrder.ApplyCompensationActivity;
import com.homepaas.sls.ui.order.directOrder.BusinessOrderActivity;
import com.homepaas.sls.ui.order.directOrder.ClientOrderDetailActivity;
import com.homepaas.sls.ui.order.directOrder.CommonPlaceOrderActivity;
import com.homepaas.sls.ui.order.directOrder.ConfirmOrderActivity;
import com.homepaas.sls.ui.order.directOrder.ErrandServiceDetailActivity;
import com.homepaas.sls.ui.order.directOrder.EvaluationOrderActivity;
import com.homepaas.sls.ui.order.directOrder.ExpressOrderTrackingActivity;
import com.homepaas.sls.ui.order.directOrder.OrderCancelReasonsActivity;
import com.homepaas.sls.ui.order.directOrder.OrderInformationFragment;
import com.homepaas.sls.ui.order.directOrder.OrderStatusFragment;
import com.homepaas.sls.ui.order.directOrder.SubsetOrderFragment;
import com.homepaas.sls.ui.order.orderplace.PlaceOrderActivity;
import com.homepaas.sls.ui.search.AllCategoriesActivity;

import dagger.Component;

/**
 * Created by Administrator on 2016/7/1.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = {CameraModule.class,
        PlaceOrderModule.class,
        OrderInfoModule.class,
        DescriptionModule.class,
        ServiceModule.class,
})
public interface PlaceOrderComponent {

    void inject(PlaceOrderActivity activity);

    void inject(ConfirmOrderActivity confirmOrderActivity);

    void inject(com.homepaas.sls.ui.order.directOrder.PlaceOrderActivity placeOrderActivity);
    void inject(ChooseServiceActivity chooseServiceActivity);
    void inject(NewChooseServiceActivity newChooseServiceActivity);
    void inject(TitleServiceActivity titleServiceActivity);
    void inject(BusinessOrderActivity businessOrderActivity);
    void inject(AllCategoriesActivity allCategoriesActivity);
    void inject(SubsetOrderFragment subsetOrderFragment);
    void inject(CommonPlaceOrderActivity commonPlaceOrderActivity);
    void inject(ImLoginActivity loginActivity);
    void inject(OrderCancelReasonsActivity orderCancelReasonsActivity);
    void inject(ExpressOrderTrackingActivity expressOrderTrackingActivity);
    void inject(ErrandServiceDetailActivity errandServiceDetailActivity);
    void inject(ApplyCompensationActivity applyCompensationActivity);
    void inject(ClientOrderDetailActivity clientOrderDetailActivity);
    void inject(OrderInformationFragment orderInformationFragment);
    void inject(OrderStatusFragment orderStatusFragment);
    void inject(EvaluationOrderActivity evaluationOrderActivity);
}
