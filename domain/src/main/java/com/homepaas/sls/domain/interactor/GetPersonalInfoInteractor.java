package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2015/12/23.
 *
 */

public class GetPersonalInfoInteractor extends Interactor<PersonalInfo> {

    private PersonalInfoRepo repo;

    @Inject
    public GetPersonalInfoInteractor(@Named("concurrent") JobExecutor jobExecutor,
                                     PostExecutionThread postExecutionThread,
                                     PersonalInfoRepo repo) {
        super(jobExecutor, postExecutionThread);
        this.repo = repo;
    }

    @Override
    protected Observable<PersonalInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<PersonalInfo>() {
            @Override
            public void call(Subscriber<? super PersonalInfo> subscriber) {
                try {
                    PersonalInfo info = repo.getPersonalInfo();
                    subscriber.onNext(info);
                    subscriber.onCompleted();
                } catch (GetDataException | AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
