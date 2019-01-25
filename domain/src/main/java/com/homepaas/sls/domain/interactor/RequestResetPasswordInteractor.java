package com.homepaas.sls.domain.interactor;

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
 * on 2016/1/21 0021
 *
 * @author zhudongjie .
 */
public class RequestResetPasswordInteractor extends Interactor<String> {

    private String phone;

    private String captcha;

    private PersonalInfoRepo personalInfoRepo;

    @Inject
    public RequestResetPasswordInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                          PersonalInfoRepo personalInfoRepo) {
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
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String s = personalInfoRepo.requestResetPassword(phone, captcha);
                    subscriber.onNext(s);
                    subscriber.onCompleted();
                } catch (GetDataException|AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
