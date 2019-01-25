package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.SaveDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.PersonalInfoRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * on 2016/1/22 0022
 *
 * @author zhudongjie .
 */
public class ModifyUserNicknameInteractor extends Interactor<String> {

    private PersonalInfoRepo personalInfoRepo;

    private String nickname;

    @Inject
    public ModifyUserNicknameInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread,
                                        PersonalInfoRepo personalInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.personalInfoRepo = personalInfoRepo;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    personalInfoRepo.modifyNickname(nickname);
                    subscriber.onCompleted();
                } catch (SaveDataException | AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
