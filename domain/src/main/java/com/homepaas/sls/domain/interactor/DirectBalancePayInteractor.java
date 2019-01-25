package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.OrderInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/7.
 */
public class DirectBalancePayInteractor extends Interactor<String> {

    @Inject
    OrderInfoRepo orderInfoRepo;
    private String id;

    public void setParams(String id){
        this.id = id;
    }


    @Inject
    public DirectBalancePayInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String s = orderInfoRepo.directBalancePay(id);
                subscriber.onNext(s);
                subscriber.onCompleted();
            }
        });
    }
}
