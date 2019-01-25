package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.SystemConfigEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.ServiceRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CJJ on 2016/9/20.
 *
 */

public class GetSystemConfigInteractor extends Interactor<SystemConfigEntity> {

    @Inject
    ServiceRepo serviceRepo;

    @Inject
    public GetSystemConfigInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    @Override
    protected Observable<SystemConfigEntity> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<SystemConfigEntity>() {
            @Override
            public void call(Subscriber<? super SystemConfigEntity> subscriber) {
                try {
                    SystemConfigEntity configEntity = serviceRepo.getConfig();
                    subscriber.onNext(configEntity);
                } catch (AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
