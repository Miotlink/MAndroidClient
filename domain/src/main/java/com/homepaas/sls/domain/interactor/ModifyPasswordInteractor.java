package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.SaveDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/2/17 0017
 *
 * @author zhudongjie .
 */
public class ModifyPasswordInteractor extends Interactor<String> {

    private PersonalInfoRepo personalInfoRepo;

    private String oldPassword;

    private String newPassword;

    public ModifyPasswordInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                    PersonalInfoRepo personalInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.personalInfoRepo = personalInfoRepo;
    }

    public void setPassword(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String s = personalInfoRepo.modifyPassword(oldPassword, newPassword);
                    subscriber.onNext(s);
                    subscriber.onCompleted();
                } catch (SaveDataException | AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
