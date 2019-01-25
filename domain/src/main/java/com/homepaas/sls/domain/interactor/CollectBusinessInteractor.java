package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.BusinessInfoRepo;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/1/22 0022
 *
 * @author zhudongjie .
 */
public class CollectBusinessInteractor extends Interactor<Boolean> {

    private String businessId;

    private boolean collect;

    private BusinessInfoRepo businessInfoRepo;

    public CollectBusinessInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, BusinessInfoRepo businessInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.businessInfoRepo = businessInfoRepo;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    @Override
    protected Observable<Boolean> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    boolean sucess = businessInfoRepo.collectBusiness(businessId, collect);
                    subscriber.onNext(sucess);
                    subscriber.onCompleted();
                } catch (GetDataException|AuthException e) {
                   subscriber.onError(e);
                }
            }
        });
    }
}
