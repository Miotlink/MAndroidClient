package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.BusinessInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/1/18 0018
 *
 * @author zhudongjie .
 */
public class GetBusinessInfoInteractor extends Interactor<BusinessInfo>{

    private BusinessInfoRepo businessInfoRepo;

    private String businessId;

    @Inject
    public GetBusinessInfoInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, BusinessInfoRepo businessInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.businessInfoRepo = businessInfoRepo;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Override
    protected Observable<BusinessInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<BusinessInfo>() {
            @Override
            public void call(Subscriber<? super BusinessInfo> subscriber) {
                BusinessInfo businessInfo;
                try {
                    businessInfo = businessInfoRepo.getBusinessInfo(businessId);
                    subscriber.onNext(businessInfo);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }

            }
        });
    }
}
