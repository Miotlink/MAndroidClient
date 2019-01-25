package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.UpdateCheck;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/5/24 0024
 *
 * @author zhudongjie
 */
@Singleton
public class CheckUpdateInteractor extends Interactor<UpdateCheck>{

    private PersonalInfoRepo personalInfoRepo;
    @Inject
    public CheckUpdateInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, PersonalInfoRepo personalInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.personalInfoRepo = personalInfoRepo;
    }

    @Override
    protected Observable<UpdateCheck> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<UpdateCheck>() {
            @Override
            public void call(Subscriber<? super UpdateCheck> subscriber) {
                try {
                    UpdateCheck updateCheck = personalInfoRepo.checkUpdate();
                    subscriber.onNext(updateCheck);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                   subscriber.onError(e);
                }
            }
        });
    }
}
