package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.param.DirectOrderParam;
import com.homepaas.sls.domain.repository.OrderInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CJJ on 2016/9/19.
 *
 */
public class PlaceDirectInteractor extends Interactor<String>{

    @Inject
    OrderInfoRepo orderInfoRepo;
    private DirectOrderParam param;


    @Inject
    public PlaceDirectInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String s = orderInfoRepo.placeDirectOrder(param);
                    subscriber.onNext(s);
                } catch (AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public void setParam(DirectOrderParam param) {
        this.param = param;
    }
}
