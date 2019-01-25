package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.BusinessInfoRepo;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/6/20 0020
 *
 * @author zhudongjie
 */
public class ReportBusinessInteractor extends Interactor<String>{

    private BusinessInfoRepo businessInfoRepo;
    private String businessId;

    public ReportBusinessInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, BusinessInfoRepo businessInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.businessInfoRepo = businessInfoRepo;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String msg = businessInfoRepo.reportBusiness(businessId);
                    subscriber.onNext(msg);
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
