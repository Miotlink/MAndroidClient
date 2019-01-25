package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.WorkerInfoRepo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/6/17 0017
 *
 * @author zhudongjie
 */
public class GetWorkerEvaluationsInteractor extends Interactor<List<Evaluation>> {

    private WorkerInfoRepo workerInfoRepo;
    private String workerId;
    private int pageIndex;
    private int pageSize;

    public GetWorkerEvaluationsInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, WorkerInfoRepo workerInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.workerInfoRepo = workerInfoRepo;
    }

    public void setRange(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    @Override
    protected Observable<List<Evaluation>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<Evaluation>>() {
            @Override
            public void call(Subscriber<? super List<Evaluation>> subscriber) {
                try {
                    List<Evaluation> evaluationList = workerInfoRepo.getEvaluationList(workerId, pageIndex, pageSize);
                    subscriber.onNext(evaluationList);
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
