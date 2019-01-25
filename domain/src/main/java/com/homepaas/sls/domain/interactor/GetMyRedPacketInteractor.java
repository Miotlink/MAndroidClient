package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.RedPacketInfo;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.FavourRepo;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/4/28.
 *
 */
public class GetMyRedPacketInteractor extends Interactor<RedPacketInfo>{


    FavourRepo favourRepo;
    String status;

    @Inject
    public GetMyRedPacketInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, FavourRepo favourRepo) {
        super(jobExecutor, postExecutionThread);
        this.favourRepo = favourRepo;
    }

    @Override
    protected Observable<RedPacketInfo> buildObservable() {
        return Observable.create(new Observable.OnSubscribe<RedPacketInfo>() {

            @Override
            public void call(Subscriber<? super RedPacketInfo> subscriber) {
                try {
                    RedPacketInfo myRedPackets = favourRepo.getMyRedPackets(status);
                    subscriber.onCompleted();
                    subscriber.onNext(myRedPackets);
                } catch (IOException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }
            }
        });
    }
}
