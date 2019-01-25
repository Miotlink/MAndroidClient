package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.WorkerTagsRepo;

import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Sherily on 2016/9/8.
 */
public class GetWorkerTagsInfoInteractor extends Interactor<GetWorkerTagsInfo> {

    private WorkerTagsRepo workerTagsRepo;
    private String workerId;

    public GetWorkerTagsInfoInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, WorkerTagsRepo workerTagsRepo) {
        super(jobExecutor, postExecutionThread);
        this.workerTagsRepo = workerTagsRepo;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    @Override
    protected Observable<GetWorkerTagsInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<GetWorkerTagsInfo>() {
            @Override
            public void call(Subscriber<? super GetWorkerTagsInfo> subscriber) {
                GetWorkerTagsInfo getWorkerTagsInfo = null;
                try {
                    getWorkerTagsInfo = workerTagsRepo.getWorkerTagsInfo(workerId);
                    subscriber.onNext(getWorkerTagsInfo);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }

            }
        });
    }
}
