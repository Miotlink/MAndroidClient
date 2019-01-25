package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.DescriptionInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by CJJ on 2016/7/13.
 */
public class GetDescriptInteractor extends Interactor<String> {

    String code;
    String serviceTypeId;

    public void setCode(String code) {
        this.code = code;
    }

    public void set(String code,String serviceTypeId)
    {
        this.code = code;
        this.serviceTypeId = serviceTypeId;
    }

    @Inject
    DescriptionInfoRepo descriptionInfoRepo;
    @Inject
    public GetDescriptInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread) {
        super(jobExecutor, postExecutionThread);
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
          /*      String description = descriptionInfoRepo.getDescription("",code);
                subscriber.onNext(description);*/
            }
        });
    }
}
