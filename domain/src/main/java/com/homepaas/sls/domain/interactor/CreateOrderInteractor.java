package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.OrderInfo;
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
 * Created by Administrator on 2016/6/17.
 */
public class CreateOrderInteractor extends Interactor<OrderInfo>{


    @Inject
    OrderInfoRepo orderInfoRepo;

    OrderCreateParams params;

    public void setParams(OrderCreateParams params) {
        this.params = params;
    }

    @Inject
    public CreateOrderInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }


    @Override
    protected Observable<OrderInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<OrderInfo>() {
            @Override
            public void call(Subscriber<? super OrderInfo> subscriber) {
                try {
                    orderInfoRepo.createOrder(params);
                } catch (AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
