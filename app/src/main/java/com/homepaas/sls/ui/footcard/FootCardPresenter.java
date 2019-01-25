package com.homepaas.sls.ui.footcard;

import android.util.Log;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.AppViewPage;
import com.homepaas.sls.domain.entity.BusinessEntity;
import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.domain.entity.WorkerEntity;
import com.homepaas.sls.domain.interactor.CheckBusinessCallableInteractor;
import com.homepaas.sls.domain.interactor.CheckWorkerCallableInteractor;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.event.EventPhoneInfo;
import com.homepaas.sls.mvp.model.WorkerBussinesModel;
import com.homepaas.sls.mvp.presenter.Presenter;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/15.
 */
@ActivityScope
public class FootCardPresenter implements Presenter {

    private CheckWorkerCallableInteractor callableWorkerInteractor;

    private CheckBusinessCallableInteractor callableBusinessInteractor;

    private FootCardView footCardView;
    private IService curService;

    @Inject
    public FootCardPresenter(CheckWorkerCallableInteractor callableWorkerInteractor, CheckBusinessCallableInteractor callableBusinessInteractor) {
        this.callableWorkerInteractor = callableWorkerInteractor;
        this.callableBusinessInteractor = callableBusinessInteractor;
    }

    public void setFootCardView(FootCardView footCardView) {
        this.footCardView = footCardView;
    }

    public void call(IService service){
        curService =service;
        if (curService instanceof WorkerEntity){
            WorkerEntity workerEntity = (WorkerEntity)curService;
            callableWorkerInteractor.setPhoneNumber(workerEntity.getPhoneNumber());
            callableWorkerInteractor.setWorkerId(workerEntity.getId());
            callableWorkerInteractor.execute(new Subscriber<Boolean>() {

                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    footCardView.showError(e);
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    footCardView.call(curService,aBoolean);
                }
            });
        } else {
            BusinessEntity businessEntity = (BusinessEntity)curService;
            callableBusinessInteractor.setBusinessId(businessEntity.getId());
            callableBusinessInteractor.setPhoneNumber(businessEntity.getPhoneNumber());
            callableBusinessInteractor.execute(new Subscriber<Boolean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    footCardView.showError(e);
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    footCardView.call(curService,aBoolean);
                }
            });
        }

    }
    /**
     * 通知将要拨打电话
     */
    public void startDial() {
        EventBus.getDefault().post(new EventPhoneInfo(curService));
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        callableBusinessInteractor.unsubscribe();
        callableWorkerInteractor.unsubscribe();
    }
}
