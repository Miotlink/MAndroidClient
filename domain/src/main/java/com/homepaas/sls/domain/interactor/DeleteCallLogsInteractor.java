package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.SaveDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.CallLogRepo;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/3/1 0001
 *
 * @author zhudongjie .
 */
public class DeleteCallLogsInteractor extends Interactor<String>{

    private CallLogRepo callLogRepo;

    private String phoneNumber;

    public DeleteCallLogsInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, CallLogRepo callLogRepo) {
        super(jobExecutor, postExecutionThread);
        this.callLogRepo = callLogRepo;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    callLogRepo.deleteCallLogs(phoneNumber);
                    subscriber.onNext("");
                    subscriber.onCompleted();
                } catch (SaveDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
