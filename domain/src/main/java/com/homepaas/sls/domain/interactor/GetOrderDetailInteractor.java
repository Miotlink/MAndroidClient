package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.DetailOrderEntity;
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
 */

public class GetOrderDetailInteractor extends Interactor<DetailOrderEntity> {

    OrderInfoRepo orderInfoRepo;
    String orderCode;


    public void setOrderId(String orderCode) {
        this.orderCode = orderCode;
    }

    @Inject
    public GetOrderDetailInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, OrderInfoRepo orderInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.orderInfoRepo = orderInfoRepo;
    }

    @Override
    protected Observable<DetailOrderEntity> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<DetailOrderEntity>() {
            @Override
            public void call(Subscriber<? super DetailOrderEntity> subscriber) {
                DetailOrderEntity detailOrder;
                try {
                    detailOrder = orderInfoRepo.getDetailOrder(orderCode);
                    subscriber.onNext(detailOrder);
                    subscriber.onCompleted();
                } catch (AuthException e) {
                   subscriber.onError(e);
                }
            }
        });
    }
}
