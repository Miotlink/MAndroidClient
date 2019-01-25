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
 * Created by CMJ on 2016/6/21.
 */

public class GetOrderToEvaluateInteractor extends Interactor<OrderInfo> {

    OrderInfoRepo orderInfoRepo;

    @Inject
    public GetOrderToEvaluateInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,OrderInfoRepo orderInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.orderInfoRepo = orderInfoRepo;
    }

    @Override
    protected Observable<OrderInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<OrderInfo>() {
            @Override
            public void call(Subscriber<? super OrderInfo> subscriber) {
                List<OrderEntity> ordes = null;
                try {
                    ordes = orderInfoRepo.getToEvaluateOrder();
                    subscriber.onNext(new OrderInfo(ordes));
                    subscriber.onCompleted();
                } catch (AuthException e) {
                    subscriber.onError(e);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
