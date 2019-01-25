package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.OrderInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CMJ on 2016/6/24.
 * 取消订单
 */
public class CancelOrderInteractor extends Interactor<String> {

    OrderInfoRepo orderInfoRepo;
    private String orderId;
    private String cancelReason;

    @Inject
    public CancelOrderInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, OrderInfoRepo orderInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.orderInfoRepo = orderInfoRepo;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String s = null;
                try {
                    s = orderInfoRepo.cancelOrder(orderId,cancelReason);
                    subscriber.onNext(s);
                    subscriber.onCompleted();
                } catch (AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public void setorderId(String orderId,String cancelReason) {
        this.orderId = orderId;
        this.cancelReason=cancelReason;
    }
}
