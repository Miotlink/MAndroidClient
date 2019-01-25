package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.RegisterBody;
import com.homepaas.sls.domain.exception.SaveDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.param.RegisterParam;
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
public class RegisterInteractor extends Interactor<RegisterBody> {

    private PersonalInfoRepo personalInfoRepo;

    private RegisterParam registerParam;

    @Inject
    public RegisterInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                              PersonalInfoRepo personalInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.personalInfoRepo = personalInfoRepo;
    }

    public void setRegisterParam(RegisterParam registerParam) {
        this.registerParam = registerParam;
    }

    @Override
    protected Observable<RegisterBody> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<RegisterBody>() {
            @Override
            public void call(Subscriber<? super RegisterBody> subscriber) {
                try {
                    RegisterBody result = personalInfoRepo.register(registerParam);
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                } catch (SaveDataException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
