package com.homepaas.sls.domain.interactor;


import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.ServiceTypeListRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * @author CJJ
 * @time 2016/4/27    17:09
 */
public class WorkerServiceTypeListInteractor extends Interactor<WorkerServiceTypeInfo> {

    ServiceTypeListRepo serviceTypeListRepo;
    private String id;

    @Inject
    public WorkerServiceTypeListInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, ServiceTypeListRepo serviceTypeListRepo) {
        super(jobExecutor, postExecutionThread);
        this.serviceTypeListRepo = serviceTypeListRepo;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected Observable<WorkerServiceTypeInfo> buildObservable() {

        return Observable.create(new Observable.OnSubscribe<WorkerServiceTypeInfo>() {
            @Override
            public void call(Subscriber<? super WorkerServiceTypeInfo> subscriber) {
                WorkerServiceTypeInfo workerServiceTypeInfo;
                workerServiceTypeInfo = serviceTypeListRepo.getWorkerServiceTypeInfo(id);
                subscriber.onNext(workerServiceTypeInfo);
                subscriber.onCompleted();
            }

        });
    }

}
