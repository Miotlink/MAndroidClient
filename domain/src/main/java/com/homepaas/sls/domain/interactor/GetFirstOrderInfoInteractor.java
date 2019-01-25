package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.FirstOrderInfoRepo;

import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Sherily on 2017/2/15.
 */

public class GetFirstOrderInfoInteractor extends Interactor<IsFirstOrderInfo>  {

    private FirstOrderInfoRepo firstOrderInfoRepo;

    public GetFirstOrderInfoInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                       FirstOrderInfoRepo firstOrderInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.firstOrderInfoRepo = firstOrderInfoRepo;
    }

    @Override
    protected Observable<IsFirstOrderInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<IsFirstOrderInfo>() {
            @Override
            public void call(Subscriber<? super IsFirstOrderInfo> subscriber) {
                try {
                    IsFirstOrderInfo isFirstOrderInfo = firstOrderInfoRepo.getIsFirstOrderInfo();
                    subscriber.onNext(isFirstOrderInfo);
                    subscriber.onCompleted();
                } catch (AuthException e) {
                    subscriber.onError(e);
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
