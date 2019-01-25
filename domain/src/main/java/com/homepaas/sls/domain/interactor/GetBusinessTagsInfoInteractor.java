package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.BusinessTagsRepo;


import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Sherily on 2016/9/9.
 */
public class GetBusinessTagsInfoInteractor extends Interactor<GetBusinessTagsInfo>{
    private BusinessTagsRepo businessTagsRepo;
    private String businessId;

    public GetBusinessTagsInfoInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, BusinessTagsRepo businessTagsRepo) {
        super(jobExecutor, postExecutionThread);
        this.businessTagsRepo = businessTagsRepo;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Override
    protected Observable<GetBusinessTagsInfo> buildObservable(){
        return Observable.create(new Observable.OnSubscribe<GetBusinessTagsInfo>() {
            @Override
            public void call(Subscriber<? super GetBusinessTagsInfo> subscriber) {
                GetBusinessTagsInfo getBusinessTagsInfo = null;
                try {
                    getBusinessTagsInfo = businessTagsRepo.getBusinessTagsInfo(businessId);
                    subscriber.onNext(getBusinessTagsInfo);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }

            }
        });
    }
}
