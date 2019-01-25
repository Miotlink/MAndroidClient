package com.homepaas.sls.mvp.presenter.order;

import android.text.TextUtils;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.OrderCancelReasonEntity;
import com.homepaas.sls.domain.interactor.CancelOrderInteractor;
import com.homepaas.sls.domain.repository.OrderCancelReasonRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.order.OrderCancelReasonsView;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/5/31.
 */

@ActivityScope
public class OrderCancelReasonsPresenter implements Presenter {
    private OrderCancelReasonsView orderCancelReasonsView;

    public void setOrderCancelReasonsView(OrderCancelReasonsView orderCancelReasonsView) {
        this.orderCancelReasonsView = orderCancelReasonsView;
    }

    @Inject
    public OrderCancelReasonsPresenter(CancelOrderInteractor cancelOrderInteractor) {
        this.cancelOrderInteractor = cancelOrderInteractor;
    }

    @Inject
    CancelOrderInteractor cancelOrderInteractor;//取消订单
    @Inject
    OrderCancelReasonRepo orderCancelReasonRepo;//取消订单原因

    /**
     * 获取取消原因
     */
    public void getOrderCancelReasonList() {
        orderCancelReasonRepo.getOrderCancelReasonList()
                .compose(RxUtil.<OrderCancelReasonEntity>applySchedulers())
                .subscribe(new OldBaseObserver<OrderCancelReasonEntity>(orderCancelReasonsView) {
                    @Override
                    public void onNext(OrderCancelReasonEntity orderCancelReasonEntity) {
                        orderCancelReasonsView.renderCancelReasons(orderCancelReasonEntity);
                    }
                });
    }


    /**
     * @param orderId 。。
     */
    public void orderCancel(String orderId, String cancelReason) {
        orderCancelReasonsView.showLoading();
        cancelOrderInteractor.setorderId(orderId, cancelReason);
        cancelOrderInteractor.execute(new OldBaseObserver<String>(orderCancelReasonsView) {
            @Override
            public void onNext(String s) {
                String[] split = s.split(" ");
                if (TextUtils.equals("0", split[1]))
                    orderCancelReasonsView.orderCancelSuccess();
                else orderCancelReasonsView.showMessage(split[0]);
            }
        });
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        cancelOrderInteractor.unsubscribe();
    }
}
