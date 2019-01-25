package com.homepaas.sls.mvp.presenter;

import android.text.TextUtils;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.WorkerInfo;
import com.homepaas.sls.domain.interactor.CheckWorkerCallableInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessInfoInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerInfoInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.view.BusinessPhoneView;
import com.homepaas.sls.mvp.view.CallView;
import com.homepaas.sls.mvp.view.WorkerPhoneView;

import javax.inject.Inject;

/**
 * Created by CJJ on 2016/9/26.
 */
@ActivityScope
public class DetailPresenter implements Presenter {

    @Inject
    public DetailPresenter() {
    }

    @Inject
    GetWorkerInfoInteractor getWorkerInfoInteractor;
    @Inject
    GetBusinessInfoInteractor getBusinessInfoInteractor;
    @Inject
    CheckWorkerCallableInteractor checkWorkerCallableInteractor;

    private WorkerPhoneView workerPhoneView;
    private BusinessPhoneView businessPhoneView;
    private CallView callView;

    public void setCallView(CallView callView) {
        this.callView = callView;
    }

    public void setWorkerPhoneView(WorkerPhoneView workerPhoneView) {
        this.workerPhoneView = workerPhoneView;
    }

    public void setBusinessPhoneView(BusinessPhoneView businessPhoneView) {
        this.businessPhoneView = businessPhoneView;
    }

    public void getWorkerInfo(final String workerId) {
        workerPhoneView.showLoading();
        getWorkerInfoInteractor.setWorkerId(workerId);
        getWorkerInfoInteractor.execute(new OldBaseObserver<WorkerInfo>(workerPhoneView) {
            @Override
            public void onNext(WorkerInfo workerInfo) {
                if (workerPhoneView != null)
                    workerPhoneView.renderWorkerPhoneNumber(workerInfo.getPhoneNumber(), TextUtils.equals(workerInfo.getIsCall(), "1"));
            }
        });
    }

    public void getBusinessInfo(String businessId) {
        businessPhoneView.showLoading();
        getBusinessInfoInteractor.setBusinessId(businessId);
        getBusinessInfoInteractor.execute(new OldBaseObserver<BusinessInfo>(businessPhoneView) {
            @Override
            public void onNext(BusinessInfo businessInfo) {
                if (businessPhoneView != null)
                    businessPhoneView.renderBusinessPhoneView(businessInfo.getPhoneNumber(), TextUtils.equals(businessInfo.getIsCall(), "1"));
            }

        });
    }

    public void attemptCall(String providerId, final String phoneNumber) {
        checkWorkerCallableInteractor.setPhoneNumber(phoneNumber);
        checkWorkerCallableInteractor.setWorkerId(providerId);
        checkWorkerCallableInteractor.execute(new OldBaseObserver<Boolean>(callView) {

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean)
                    callView.callIfEnable(phoneNumber);
            }
        });
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        checkWorkerCallableInteractor.unsubscribe();
        getBusinessInfoInteractor.unsubscribe();
        getWorkerInfoInteractor.unsubscribe();
    }
}
