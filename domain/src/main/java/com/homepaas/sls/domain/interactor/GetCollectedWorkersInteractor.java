package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.WorkerCollectionEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.WorkerInfoRepo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/1/23 0023
 *
 * @author zhudongjie .
 */
public class GetCollectedWorkersInteractor extends Interactor<List<WorkerCollectionEntity>> {

    private WorkerInfoRepo workerInfoRepo;

    public GetCollectedWorkersInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread
            , WorkerInfoRepo workerInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.workerInfoRepo = workerInfoRepo;
    }

    @Override
    protected Observable<List<WorkerCollectionEntity>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<WorkerCollectionEntity>>() {
            @Override
            public void call(Subscriber<? super List<WorkerCollectionEntity>> subscriber) {
                try {
                    List<WorkerCollectionEntity> collectedWorkerList = workerInfoRepo.getCollectedWorkerList();
                    subscriber.onNext(collectedWorkerList);
                    subscriber.onCompleted();
                } catch (GetDataException |AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
