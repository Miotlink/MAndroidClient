package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.SaveDataException;
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
public class ReadMessageInteractor extends Interactor<String>{

    private MessageRepo messageRepo;

    private String[] ids;

    @Inject
    public ReadMessageInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                 MessageRepo messageRepo) {
        super(jobExecutor, postExecutionThread);
        this.messageRepo = messageRepo;
    }

    public void setIds(String... ids) {
        this.ids = ids;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    messageRepo.readMessages(ids);
                    subscriber.onNext("");
                    subscriber.onCompleted();
                } catch (SaveDataException |AuthException e) {
                   subscriber.onError(e);
                }
            }
        });
    }
}
