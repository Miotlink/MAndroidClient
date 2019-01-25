package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.ServiceRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CJJ on 2016/9/14.
 */

public class GetServiceScheduleInteractor extends Interactor<List<ServiceScheduleEntity>>{

    @Inject
    ServiceRepo serviceRepo;

    String serviceTypeId;

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    @Inject
    public GetServiceScheduleInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    @Override
    protected Observable<List<ServiceScheduleEntity>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<ServiceScheduleEntity>>() {
            @Override
            public void call(Subscriber<? super List<ServiceScheduleEntity>> subscriber) {
                try {
                    List<ServiceScheduleEntity> serviceDSchedule = serviceRepo.getServiceDSchedule(serviceTypeId);
                    subscriber.onNext(serviceDSchedule);
                } catch (AuthException e) {
                   subscriber.onError(e);
                }
            }
        });
    }
}
