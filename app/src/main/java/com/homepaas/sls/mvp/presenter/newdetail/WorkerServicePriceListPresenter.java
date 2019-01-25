package com.homepaas.sls.mvp.presenter.newdetail;

import com.homepaas.sls.domain.entity.WorkerServicePriceListEntity;
import com.homepaas.sls.domain.repository.WorkerServicePriceListRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.newdetail.WorkerServicePriceListView;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/2/13.
 */

public class WorkerServicePriceListPresenter implements Presenter {

    @Inject
    public WorkerServicePriceListPresenter() {
    }

    @Inject
    WorkerServicePriceListRepo workerServicePriceListRepo;

    WorkerServicePriceListView workerServicePriceListView;

    public void setWorkerServicePriceListView(WorkerServicePriceListView workerServicePriceListView) {
        this.workerServicePriceListView = workerServicePriceListView;
    }

    public void getWorkerServicePriceList(String id) {
        workerServicePriceListRepo.getWorkerServicePriceList(id)
               .compose(RxUtil.<WorkerServicePriceListEntity>applySchedulers())
                .subscribe(new OldBaseObserver<WorkerServicePriceListEntity>(workerServicePriceListView) {
                    @Override
                    public void onNext(WorkerServicePriceListEntity workerServicePriceListEntity) {
                        workerServicePriceListView.render(workerServicePriceListEntity);
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

    }
}
