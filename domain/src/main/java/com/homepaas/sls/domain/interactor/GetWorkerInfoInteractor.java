package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.WorkerInfo;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.WorkerInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/1/19 0019
 *
 * @author zhudongjie .
 */
public class GetWorkerInfoInteractor extends Interactor<WorkerInfo> {

    private WorkerInfoRepo workerInfoRepo;

    private String workerId;

    @Inject
    public GetWorkerInfoInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, WorkerInfoRepo workerInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.workerInfoRepo = workerInfoRepo;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    @Override
    protected Observable<WorkerInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<WorkerInfo>() {
            @Override
            public void call(Subscriber<? super WorkerInfo> subscriber) {
                try {
                    WorkerInfo workerInfo = workerInfoRepo.getWorkerInfo(workerId);
                    subscriber.onNext(workerInfo);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
