package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.CommentRepo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/6/23 0023
 *
 * @author zhudongjie
 */
public class GetMyEvaluationsInteractor extends Interactor<List<Evaluation>>{

    private CommentRepo commentRepo;
    private int pageIndex;
    private int pageSize;

    public GetMyEvaluationsInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, CommentRepo commentRepo) {
        super(jobExecutor, postExecutionThread);
        this.commentRepo = commentRepo;
    }

    public void setRange(int pageIndex,int pageSize){
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    @Override
    protected Observable<List<Evaluation>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<Evaluation>>() {
            @Override
            public void call(Subscriber<? super List<Evaluation>> subscriber) {
                try {
                    List<Evaluation> evaluations = commentRepo.getMyEvaluations(pageIndex,pageSize);
                    subscriber.onNext(evaluations);
                    subscriber.onCompleted();
                } catch (GetDataException | AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
