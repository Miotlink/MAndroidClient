package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.domain.entity.OrderInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.OrderInfoRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CJJ on 2016/9/20.
 *
 */

public class GetToTakeOrderInteractor extends Interactor<OrderInfo> {

    @Inject
    OrderInfoRepo orderInfoRepo;

    @Inject
    public GetToTakeOrderInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    @Override
    protected Observable<OrderInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<OrderInfo>() {
            @Override
            public void call(Subscriber<? super OrderInfo> subscriber) {
                List<OrderEntity> toTakeOrder;
                try {
                    toTakeOrder = orderInfoRepo.getToTakeOrder();
                    OrderInfo orderInfo = new OrderInfo(toTakeOrder);
                    subscriber.onNext(orderInfo);
                } catch (AuthException e) {
                   subscriber.onError(e);
                }

            }
        });
    }
}
