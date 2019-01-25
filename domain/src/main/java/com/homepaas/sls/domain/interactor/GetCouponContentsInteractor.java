package com.homepaas.sls.domain.interactor;

import com.homepaas.sls.domain.entity.CouponContentsInfo;
import com.homepaas.sls.domain.executor.JobExecutor;
import com.homepaas.sls.domain.executor.PostExecutionThread;
import com.homepaas.sls.domain.repository.CouponContentsRepo;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Sherily on 2016/6/23.
 */
public class GetCouponContentsInteractor extends Interactor<CouponContentsInfo> {

    private CouponContentsRepo couponContentsRepo;
    private String serviceId;
    private String longitude;
    private String latitude;
    private String ispay;

    @Inject
    public GetCouponContentsInteractor(@Named("concurrent") JobExecutor jobExecutor, PostExecutionThread postExecutionThread, CouponContentsRepo couponContentsRepo) {
        super(jobExecutor, postExecutionThread);
        this.couponContentsRepo = couponContentsRepo;//实际返回的是CouponContentsRepoImpl实例
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }

    @Override
    protected Observable<CouponContentsInfo> buildObservable() {

        return Observable.create(new Observable.OnSubscribe<CouponContentsInfo>() {
            @Override
            public void call(Subscriber<? super CouponContentsInfo> subscriber) {
                CouponContentsInfo couponContentsInfo = null;
                try {
                    couponContentsInfo = couponContentsRepo.getCouponContentsInfo(serviceId, longitude, latitude, ispay);
                    subscriber.onNext(couponContentsInfo);
                } catch (Exception e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }
}
