package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.Message;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.MessageRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/1/26 0026
 *
 * @author zhudongjie .
 */
public class GetMessageListInteractor extends Interactor<List<Message>> {

    private MessageRepo messageRepo;

    @Inject
    public GetMessageListInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread
            , MessageRepo messageRepo) {
        super(jobExecutor, postExecutionThread);
        this.messageRepo = messageRepo;
    }

    @Override
    protected Observable<List<Message>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<Message>>() {
            @Override
            public void call(Subscriber<? super List<Message>> subscriber) {
                try {
                    List<Message> messageList = messageRepo.getMessageList();
                    subscriber.onNext(messageList);
                    subscriber.onCompleted();
                } catch (GetDataException |AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
