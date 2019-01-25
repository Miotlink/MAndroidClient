package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.exception.NotNormalDeviceException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.param.LoginParam;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * 用户登录
 *
 * @author zhudongjie .
 */
public class LoginInteractor extends Interactor<LoginBody> {

    private PersonalInfoRepo personalInfoRepo;

    private LoginParam param;

    @Inject
    public LoginInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                           PersonalInfoRepo personalInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.personalInfoRepo = personalInfoRepo;
    }

    public void setParam(LoginParam param) {
        this.param = param;
    }

    @Override
    protected Observable<LoginBody> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<LoginBody>() {
            @Override
            public void call(Subscriber<? super LoginBody> subscriber) {
                try {
                    LoginBody login = personalInfoRepo.login(param);
                    subscriber.onNext(login);
                    subscriber.onCompleted();
                } catch (GetDataException | NotNormalDeviceException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
