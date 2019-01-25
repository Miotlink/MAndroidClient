package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.WorkerInfo;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.WorkerInfoRepo;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/1/22 0022
 *
 * @author zhudongjie .
 */
public class LikeWorkerInteractor extends Interactor<Boolean>{

    private String workerId;

    private boolean like;

    private WorkerInfoRepo workerInfoRepo;

    public LikeWorkerInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread,WorkerInfoRepo workerInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.workerInfoRepo = workerInfoRepo;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    @Override
    protected Observable<Boolean> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    boolean sucess = workerInfoRepo.likeWorker(workerId, like);
                    subscriber.onNext(sucess);
                    subscriber.onCompleted();
                } catch (GetDataException|AuthException e) {
                   subscriber.onError(e);
                }
            }
        });
    }
}
