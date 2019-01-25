package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Sherily on 2017/5/27.
 */

public class QuickLoginInteractor extends Interactor<LoginBody> {
    private PersonalInfoRepo personalInfoRepo;
    private String phone;
    private String captcha;

    @Inject
    public QuickLoginInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, PersonalInfoRepo personalInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.personalInfoRepo = personalInfoRepo;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    protected Observable<LoginBody> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<LoginBody>() {
            @Override
            public void call(Subscriber<? super LoginBody> subscriber) {
                try {
                    LoginBody login = personalInfoRepo.quickLogin(phone,captcha);
                    subscriber.onNext(login);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
