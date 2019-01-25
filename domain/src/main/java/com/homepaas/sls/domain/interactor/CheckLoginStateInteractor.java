package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/2/27 0027
 *
 * @author zhudongjie .
 */
public class CheckLoginStateInteractor extends Interactor<Boolean>{

    private PersonalInfoRepo personalInfoRepo;

    @Inject
    public CheckLoginStateInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, PersonalInfoRepo personalInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.personalInfoRepo = personalInfoRepo;
    }

    @Override
    protected Observable<Boolean> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    boolean result = personalInfoRepo.isLoggedIn();
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
