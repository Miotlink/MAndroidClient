package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.param.PriceParam;
import com.homepaas.sls.domain.repository.ServiceRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CJJ on 2016/9/14.
 */

public class GetServicePriceInteractor extends Interactor<PriceEntity> {

    @Inject
    ServiceRepo serviceRepo;

    PriceParam param;

    public void setParam(PriceParam param) {
        this.param = param;
    }

    @Inject
    public GetServicePriceInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    @Override
    protected Observable<PriceEntity> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<PriceEntity>() {
            @Override
            public void call(Subscriber<? super PriceEntity> subscriber) {
                try {
                    PriceEntity servicePrice = serviceRepo.getServicePrice(param);
                    subscriber.onNext(servicePrice);
                } catch (AuthException e) {
                   subscriber.onError(e);
                }
            }
        });
    }
}
