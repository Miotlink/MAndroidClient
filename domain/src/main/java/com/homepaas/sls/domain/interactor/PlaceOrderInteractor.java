package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.param.OrderCreateParams;
import com.homepaas.sls.domain.repository.OrderInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/28.
 */
public  class PlaceOrderInteractor extends Interactor<java.lang.String> {

    OrderInfoRepo orderInfoRepo;

    OrderCreateParams params;

    @Inject
    public PlaceOrderInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, OrderInfoRepo orderInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.orderInfoRepo = orderInfoRepo;
    }

    public void setParams(OrderCreateParams params) {
        this.params = params;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                String order = null;
                try {
                    order = orderInfoRepo.createOrder(params);
                    subscriber.onNext(order);
                    subscriber.onCompleted();
                } catch (AuthException e) {
                    subscriber.onError(e);
                   e.printStackTrace();
                }

            }
        });
    }
}
