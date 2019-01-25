package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.param.EvaluateParam;
import com.homepaas.sls.domain.repository.CommentRepo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CMJ on 2016/6/24.
 *
 */
public class EvaluateOrderInteractor extends Interactor<String> {

    private CommentRepo commentRepo;

    private String orderId;

    private String score;
    private String content;
    private List<String> pictures;

    public EvaluateOrderInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, CommentRepo commentRepo) {
        super(jobExecutor, postExecutionThread);
        this.commentRepo = commentRepo;
    }





    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String msg = commentRepo.evaluateOrder(new EvaluateParam(null, null, score, content, orderId, pictures));
                    subscriber.onNext(msg);
                    subscriber.onCompleted();
                } catch (GetDataException | AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
