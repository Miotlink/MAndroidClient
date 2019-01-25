package com.homepaas.sls.di.module;

import android.support.v7.widget.RecyclerView;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.AvatarsInteractor;
import com.homepaas.sls.domain.interactor.CompleteOrderInteractor;
import com.homepaas.sls.domain.interactor.CancelOrderInteractor;
import com.homepaas.sls.domain.interactor.DeleteOrderInteractor;
import com.homepaas.sls.domain.interactor.GetDirectPayAliSignInteractor;
import com.homepaas.sls.domain.interactor.GetDirectPayIdInteractor;
import com.homepaas.sls.domain.interactor.GetDirectPayWxSignInteractor;
import com.homepaas.sls.domain.interactor.GetOrderDetailInteractor;
import com.homepaas.sls.domain.interactor.GetOrderToConfirmInteractor;
import com.homepaas.sls.domain.interactor.GetOrderToEvaluateInteractor;
import com.homepaas.sls.domain.interactor.GetOrderToPayInteractor;
import com.homepaas.sls.domain.interactor.GetToTakeOrderInteractor;
import com.homepaas.sls.domain.interactor.PlaceOrderInteractor;
import com.homepaas.sls.domain.interactor.GetAllOrderInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.mvp.view.order.GetOrderView;
import com.homepaas.sls.mvp.view.order.OrderDetailView;
import com.homepaas.sls.mvp.view.order.OrderPlaceView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/4/28.
 */
@Module
public class OrderInfoModule {

    private GetOrderView getOrderView;
    private OrderDetailView orderDetailView;

    public OrderInfoModule() {
    }

    public OrderInfoModule(GetOrderView view) {
        this.getOrderView = view;
    }

    public OrderDetailView getOrderDetailView() {
        return orderDetailView;
    }

    public OrderInfoModule(OrderDetailView orderDetailView) {
        this.orderDetailView = orderDetailView;
    }

    @ActivityScope
    @Provides
    @Named("PlaceOrderInteractor")
    Interactor provideOrderCreateInteractor(PlaceOrderInteractor interactor) {

        return interactor;
    }

    @ActivityScope
    @Provides
    @Named("GetAllOrderInteractor")
    Interactor provideGetOrderInteractor(GetAllOrderInteractor interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    @Named("GetOrderToConfirmInteractor")
    Interactor provideGetToConfirmOrderInteractor(GetOrderToConfirmInteractor interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    @Named("GetOrderToPayInteractor")
    Interactor provideGetToPayOrderInteractor(GetOrderToPayInteractor interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    @Named("GetOrderToEvaluateInteractor")
    Interactor provideGetToEvaluateOrderInteractor(GetOrderToEvaluateInteractor interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    @Named("DeleteOrderInteractor")
    Interactor provideDeleteOrderInteractor(DeleteOrderInteractor interactor){
        return interactor;
    }

    @Provides
    @ActivityScope
    @Named("GetOrderDetailInteractor")
    Interactor provideGetOrderDetailInteractor(GetOrderDetailInteractor interactor){
        return interactor;
    }

    @Provides
    @ActivityScope
    @Named("CancelOrderInteractor")
    Interactor provideCancelOrderInteractor(CancelOrderInteractor interactor){
        return interactor;
    }


    @Provides
    @ActivityScope
    @Named("CompleteOrderInteractor")
    Interactor provideConfirmOrderInteractor(CompleteOrderInteractor interactor){
        return interactor;
    }

    @Provides
    @ActivityScope
    Interactor provideGetToTakeOrderInteractor(GetToTakeOrderInteractor interactor){
        return interactor;
    }

    @Provides
    @ActivityScope
    @Named("GetDirectPayIdInteractor")
    Interactor provideGetDirectPayIdInteractor(GetDirectPayIdInteractor interactor){
        return interactor;
    }

    @Provides
    @ActivityScope
    @Named("GetDirectPayWxSignInteractor")
    Interactor provideGetDirectPayWXSignInteractor(GetDirectPayWxSignInteractor interactor){
        return interactor;
    }

    @Provides
    @ActivityScope
    @Named("GetDirectPayWxSignInteractor")
    Interactor provideGetDirectPayAliSignInteractor(GetDirectPayAliSignInteractor interactor){
        return interactor;
    }
    /******************
     * provide view
     ********************************/
    @Provides
    @ActivityScope
    GetOrderView provideGetOrderView() {
        return getOrderView;
    }

    @Provides
    @ActivityScope
    OrderPlaceView provideOrderPlaceView() {
        return (OrderPlaceView) getOrderView;
    }

    @Provides
    @ActivityScope
    OrderDetailView provideOrderDetailView(){return orderDetailView;}

}
