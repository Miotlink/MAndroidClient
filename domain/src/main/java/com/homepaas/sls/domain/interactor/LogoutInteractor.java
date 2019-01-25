package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.SaveDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/1/27 0027
 *
 * @author zhudongjie .
 */
public class LogoutInteractor extends Interactor<Boolean> {

    private PersonalInfoRepo personalInfoRepo;

    @Inject
    public LogoutInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, PersonalInfoRepo personalInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.personalInfoRepo = personalInfoRepo;
    }

    @Override
    protected Observable<Boolean> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    boolean result = personalInfoRepo.logout();
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (SaveDataException  e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
