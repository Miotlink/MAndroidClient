package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.CaptchaBody;
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
 * @author zhudongjie .
 */
public class SendCaptchaInteractor extends Interactor<CaptchaBody> {

    private PersonalInfoRepo personalInfoRepo;

    private String phone;

    private int type;

    @Inject
    public SendCaptchaInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                 PersonalInfoRepo personalInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.personalInfoRepo = personalInfoRepo;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected Observable<CaptchaBody> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<CaptchaBody>() {
            @Override
            public void call(Subscriber<? super CaptchaBody> subscriber) {
                try {
                    CaptchaBody captchaBody = personalInfoRepo.sendCaptcha(phone, type);
                    subscriber.onNext(captchaBody);
                    subscriber.onCompleted();
                } catch (GetDataException | AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
