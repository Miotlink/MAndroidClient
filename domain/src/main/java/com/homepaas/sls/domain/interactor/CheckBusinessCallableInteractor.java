package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.BusinessInfoRepo;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/2/25 0025
 *
 * @author zhudongjie .
 */
public class CheckBusinessCallableInteractor extends Interactor<Boolean>{

    private BusinessInfoRepo businessInfoRepo;

    private String phoneNumber;

    private String businessId;

    public CheckBusinessCallableInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, BusinessInfoRepo businessInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.businessInfoRepo = businessInfoRepo;
    }

    @Override
    protected Observable<Boolean> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    boolean result = businessInfoRepo.checkCallable(businessId, phoneNumber);
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

}
