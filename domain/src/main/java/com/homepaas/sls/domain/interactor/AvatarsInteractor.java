package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.AvatarRepo;

import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Sherily on 2016/9/18.
 */
public class AvatarsInteractor extends Interactor<AvatarsEntity>  {

    private AvatarRepo avatarRepo;
    private String type;
    private String id;

    public AvatarsInteractor(@Named("concurrent")JobExecutor jobExecutor, PostExecutionThread postExecutionThread, AvatarRepo avatarRepo) {
        super(jobExecutor, postExecutionThread);
        this.avatarRepo = avatarRepo;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected Observable<AvatarsEntity> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<AvatarsEntity>() {
            @Override
            public void call(Subscriber<? super AvatarsEntity> subscriber) {
                try {
                    AvatarsEntity avatarsEntity = avatarRepo.geAvatars(type, id);
                    subscriber.onNext(avatarsEntity);
                    subscriber.onCompleted();
                } catch (GetDataException e) {
                    subscriber.onError(e);
                } catch (AuthException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
