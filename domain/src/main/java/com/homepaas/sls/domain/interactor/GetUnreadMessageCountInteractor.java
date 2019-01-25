package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.MessageRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
public class GetUnreadMessageCountInteractor extends Interactor<Integer> {

    @Inject
    MessageRepo messageRepo;

    @Inject
    public GetUnreadMessageCountInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread
            , MessageRepo messageRepo) {
        super(jobExecutor, postExecutionThread);
        this.messageRepo = messageRepo;
    }

    @Override
    protected Observable<Integer> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    int count = messageRepo.getUnreadCount();
                    subscriber.onNext(count);
                    subscriber.onCompleted();
                } catch (GetDataException|AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
