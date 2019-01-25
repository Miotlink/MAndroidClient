package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.OrderPayWXSign;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetPaySignException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.param.GetOrderpayWxSignParams;
import com.homepaas.sls.domain.repository.OrderInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CMJ on 2016/6/28.
 */

public class GetOrderPayWXSignInteractor extends Interactor<OrderPayWXSign> {

    @Inject
    OrderInfoRepo orderInfoRepo;
    GetOrderpayWxSignParams params;

    @Inject
    public GetOrderPayWXSignInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    public void setParams(GetOrderpayWxSignParams params) {
        this.params = params;
    }

    @Override
    protected Observable<OrderPayWXSign> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<OrderPayWXSign>() {
            @Override
            public void call(Subscriber<? super OrderPayWXSign> subscriber) {
                OrderPayWXSign orderPayWxSign = null;
                try {
                    orderPayWxSign = orderInfoRepo.getOrderPayWxSign(params);
                    subscriber.onNext(orderPayWxSign);
                    subscriber.onCompleted();
                } catch (GetPaySignException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
