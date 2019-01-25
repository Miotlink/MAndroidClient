package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.OrderPayAliSign;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetPaySignException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.param.GetOrderPayAliSignParams;
import com.homepaas.sls.domain.repository.OrderInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CMJ on 2016/6/25.
 */

public class GetOrderPayAliSignInteractor extends Interactor<String> {

    @Inject
    OrderInfoRepo orderInfoRepo;

    GetOrderPayAliSignParams params;


    @Inject
    public GetOrderPayAliSignInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    public void setParams(GetOrderPayAliSignParams params) {
        this.params = params;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<java.lang.String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                OrderPayAliSign orderPayAliSign = null;
                try {
                    orderPayAliSign = orderInfoRepo.getOrderPayAliSign(params);
                    subscriber.onNext(orderPayAliSign.sign);
                    subscriber.onCompleted();
                } catch (GetPaySignException e) {
                   subscriber.onError(e);
                }
            }
        });
    }
}
