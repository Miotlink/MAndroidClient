package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.AddressRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CJJ on 2016/9/13.
 */

public class AddServiceAddressInteractor extends Interactor<String>{

    @Inject
    AddressRepo addressRepo;
    AddressEntity param;

    public void setParam(AddressEntity param) {
        this.param = param;
    }

    @Inject
    public AddServiceAddressInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String s = addressRepo.addServiceAddress(param);
                        subscriber.onNext(s);
                } catch (AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
