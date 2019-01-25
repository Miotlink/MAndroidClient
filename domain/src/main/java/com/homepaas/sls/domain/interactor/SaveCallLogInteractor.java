package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.CallLog;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.SaveDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.CallLogRepo;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
public class SaveCallLogInteractor extends Interactor<Boolean> {

    private CallLogRepo callLogRepo;

    private CallLog callLog;

    public SaveCallLogInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, CallLogRepo callLogRepo) {
        super(jobExecutor, postExecutionThread);
        this.callLogRepo = callLogRepo;
    }


    @Override
    protected Observable<Boolean> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    boolean result = callLogRepo.saveCallLog(callLog);
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (SaveDataException e) {
                    subscriber.onError(e);
                } catch (AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public void setCallLog(CallLog callLog) {
        this.callLog = callLog;
    }
}
