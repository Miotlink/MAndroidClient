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
 * on 2016/6/24 0024
 *
 * @author zhudongjie
 */
public class AddEvaluationInteractor extends Interactor<String>{

    private CommentRepo commentRepo;

    private String type;
    private String ownerId;
    private String orderId;

    private String score;
    private String content;
    private List<String> pictures;

    public AddEvaluationInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, CommentRepo commentRepo) {
        super(jobExecutor, postExecutionThread);
        this.commentRepo = commentRepo;
    }



    public void setType(String type) {
        this.type = type;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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
                    String msg = commentRepo.evaluate(new EvaluateParam(ownerId, type, score, content, orderId, pictures));
                    subscriber.onNext(msg);
                    subscriber.onCompleted();
                } catch (GetDataException | AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
