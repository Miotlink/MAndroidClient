package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.WorkerInfoRepo;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/6/20 0020
 *
 * @author zhudongjie
 */
public class ReportWorkerInteractor extends Interactor<String>{

    private WorkerInfoRepo workerInfoRepo;
    private String workerId;

    public ReportWorkerInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, WorkerInfoRepo workerInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.workerInfoRepo = workerInfoRepo;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String msg = workerInfoRepo.reportWorker(workerId);
                    subscriber.onNext(msg);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }
}
