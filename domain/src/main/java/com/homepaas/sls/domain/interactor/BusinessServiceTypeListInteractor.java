package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.ServiceTypeListRepo;

import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/27.
 */
public class BusinessServiceTypeListInteractor extends Interactor<BusinessServiceTypeInfo>{

    ServiceTypeListRepo serviceTypeListRepo;
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @Inject
    public BusinessServiceTypeListInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                            ServiceTypeListRepo serviceTypeListRepo) {
        super(jobExecutor, postExecutionThread);
        this.serviceTypeListRepo = serviceTypeListRepo;
    }

    @Override
    protected Observable<BusinessServiceTypeInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<BusinessServiceTypeInfo>() {
            @Override
            public void call(Subscriber<? super BusinessServiceTypeInfo> subscriber) {
                BusinessServiceTypeInfo businessServiceTypeInfo = serviceTypeListRepo.getBusinessServiceTypeInfo(id);
                subscriber.onNext(businessServiceTypeInfo);
                subscriber.onCompleted();

            }
        });
    }
}
