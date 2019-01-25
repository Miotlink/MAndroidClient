package com.homepaas.sls.mvp.presenter.order;

import com.homepaas.sls.domain.entity.WorkerInfo;
import com.homepaas.sls.domain.interactor.GetWorkerInfoInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.mvp.model.mapper.ServiceInfoModelMapper;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.order.WorkerOrderPlaceView;

import javax.inject.Inject;

/**
 * Created by CJJ on 2016/6/20.
 */

public class WorkerInfoPresenter implements Presenter{

    WorkerOrderPlaceView workerOrderPlaceView;
    GetWorkerInfoInteractor getWorkerInfoInteractor;
    ServiceInfoModelMapper modelMapper;

    public void setWorkerOrderPlaceView(WorkerOrderPlaceView workerOrderPlaceView) {
        this.workerOrderPlaceView = workerOrderPlaceView;
    }

    @Inject
    public WorkerInfoPresenter(GetWorkerInfoInteractor getWorkerInfoInteractor,ServiceInfoModelMapper modelMapper) {
        this.getWorkerInfoInteractor = getWorkerInfoInteractor;
        this.modelMapper = modelMapper;
    }

    public void getWorkerInfo(String WokerId){
        getWorkerInfoInteractor.setWorkerId(WokerId);
        getWorkerInfoInteractor.execute(new OldBaseObserver<WorkerInfo>(workerOrderPlaceView) {
            @Override
            public void onNext(WorkerInfo workerInfo) {
                WorkerCollectionEntity workerInfoModel = modelMapper.transformWorker(workerInfo);
                workerOrderPlaceView.render(workerInfoModel);
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
        getWorkerInfoInteractor.unsubscribe();
    }
}
