package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.ServiceRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CJJ on 2016/9/19.
 *
 */

public class GetActivityInteractor extends Interactor<ActivityNgInfoNew> {

    @Inject
    ServiceRepo serviceRepo;
    private String serviceTypeId;


    @Inject
    public GetActivityInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    @Override
    protected Observable<ActivityNgInfoNew> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<ActivityNgInfoNew>() {
            @Override
            public void call(Subscriber<? super ActivityNgInfoNew> subscriber) {
                try {
                    ActivityNgInfoNew availableActivity = serviceRepo.getAvailableActivity(serviceTypeId);
                    subscriber.onNext(availableActivity);
                    subscriber.onCompleted();
                } catch (AuthException e) {
                   subscriber.onError(e);
                }
            }
        });
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
}
