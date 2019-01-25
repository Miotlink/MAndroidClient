package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.WorkerInfoRepo;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/2/25 0025
 *
 * @author zhudongjie .
 */
public class CheckWorkerCallableInteractor extends Interactor<Boolean>{

    private WorkerInfoRepo workerInfoRepo;

    private String phoneNumber;

    private String workerId;

    public CheckWorkerCallableInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, WorkerInfoRepo workerInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.workerInfoRepo = workerInfoRepo;
    }

    @Override
    protected Observable<Boolean> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    boolean result = workerInfoRepo.checkCallable(workerId, phoneNumber);
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }
}
