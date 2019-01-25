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
 * on 2016/2/24 0024
 *
 * @author zhudongjie .
 */
public class ModifyPhotoInteractor extends Interactor<String>{

    private PersonalInfoRepo personalInfoRepo;

    private String filePath;

    public ModifyPhotoInteractor(JobExecutor jobExecutor, PostExecutionThread postExecutionThread, PersonalInfoRepo personalInfoRepo) {
        super(jobExecutor, postExecutionThread);
        this.personalInfoRepo = personalInfoRepo;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected Observable<String> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String url = personalInfoRepo.modifyPhoto(filePath);
                    subscriber.onNext(url);
                    subscriber.onCompleted();
                } catch (SaveDataException | AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
