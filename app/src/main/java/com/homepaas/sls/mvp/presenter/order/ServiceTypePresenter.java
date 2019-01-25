package com.homepaas.sls.mvp.presenter.order;

import android.util.Log;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;
import com.homepaas.sls.domain.interactor.BusinessServiceTypeListInteractor;
import com.homepaas.sls.domain.interactor.WorkerServiceTypeListInteractor;
import com.homepaas.sls.domain.repository.BusinessServiceListRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.order.ServiceTypeListView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/4/14.
 */
@ActivityScope
public class ServiceTypePresenter implements Presenter {
    private static final String TAG = "ServiceTypePresenter";

    private ServiceTypeListView serviceTypeListView;

    public void setServiceTypeListView(ServiceTypeListView serviceTypeListView) {
        this.serviceTypeListView = serviceTypeListView;
    }

    @Inject
    public ServiceTypePresenter() {

    }

    @Inject
    WorkerServiceTypeListInteractor workerServiceTypeListInteractor;
    @Inject
    BusinessServiceTypeListInteractor businessServiceTypeListInteractor;

    @Inject
    BusinessServiceListRepo businessServiceListRepo;


    public void getWorkerServiceTypeList(String id){
//        serviceTypeListView.showLoading();
        workerServiceTypeListInteractor.setId(id);
        workerServiceTypeListInteractor.execute(new OldBaseObserver<WorkerServiceTypeInfo>(serviceTypeListView) {
            @Override
            public void onNext(WorkerServiceTypeInfo workerServiceTypeInfo) {
                Log.i(TAG, "onNext: "+workerServiceTypeInfo);
                serviceTypeListView.renderWorker(workerServiceTypeInfo);
            }
        });
    }

    public void getBusinessServiceTypeList(String id){
        serviceTypeListView.showLoading();
        businessServiceTypeListInteractor.setId(id);
        businessServiceTypeListInteractor.execute(new OldBaseObserver<BusinessServiceTypeInfo>(serviceTypeListView) {
            @Override
            public void onNext(BusinessServiceTypeInfo businessServiceTypeInfo) {
                serviceTypeListView.renderBusiness(businessServiceTypeInfo);
            }
        });

    }


    public void getBusinessServiceList(String id){
        businessServiceListRepo.getBusinessServiceList(id)
               .compose(RxUtil.<BusinessServiceTypeInfo>applySchedulers())
                .subscribe(new OldBaseObserver<BusinessServiceTypeInfo>(serviceTypeListView) {
                    @Override
                    public void onNext(BusinessServiceTypeInfo businessServiceTypeInfo) {
                        serviceTypeListView.hideLoading();
                        serviceTypeListView.renderBusiness(businessServiceTypeInfo);
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
        businessServiceTypeListInteractor.unsubscribe();
        workerServiceTypeListInteractor.unsubscribe();
    }
}
