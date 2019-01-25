package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.ServiceContent;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.BusinessInfoRepo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/6/20 0020
 *
 * @author zhudongjie
 */
public class GetBusinessServiceContentsInteractor extends Interactor<List<ServiceContent>>{

    private BusinessInfoRepo businessInfoRepo;
    private String businessId;

    public GetBusinessServiceContentsInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, BusinessInfoRepo businessInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.businessInfoRepo = businessInfoRepo;
    }

    @Override
    protected Observable<List<ServiceContent>> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<List<ServiceContent>>() {
            @Override
            public void call(Subscriber<? super List<ServiceContent>> subscriber) {
                try {
                    List<ServiceContent> list = businessInfoRepo.getServiceContentList(businessId);
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}
