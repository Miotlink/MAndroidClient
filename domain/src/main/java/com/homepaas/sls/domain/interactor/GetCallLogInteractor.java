package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.CallLog;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.CallLogRepo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
public class GetCallLogInteractor extends Interactor<List<CallLog>> {

    private CallLogRepo callLogRepo;
    @Inject
    public GetCallLogInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread
            , CallLogRepo callLogRepo) {
        super(jobExecutor, postExecutionThread);
        this.callLogRepo = callLogRepo;
    }

    @Override
    protected Observable<List<CallLog>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<CallLog>>() {
            @Override
            public void call(Subscriber<? super List<CallLog>> subscriber) {
                try {
                    List<CallLog> callLogs = callLogRepo.getCallLogs();
                    subscriber.onNext(callLogs);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
