package com.homepaas.sls.mvp.presenter.order;

import com.homepaas.sls.domain.entity.ExpressDetailOutputEntity;
import com.homepaas.sls.domain.repository.GetErrandServiceDetailRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.order.ErrandServiceDetailView;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/7/6.
 */

public class ErrandServiceDetailPresenter implements Presenter {

    private ErrandServiceDetailView errandServiceDetailView;
    public void setErrandServiceDetailView(ErrandServiceDetailView errandServiceDetailView){
        this.errandServiceDetailView=errandServiceDetailView;
    }

    @Inject
    public ErrandServiceDetailPresenter() {
    }
    @Inject
    GetErrandServiceDetailRepo getExpressDetailRepo;

    /**
     * 获取跑腿详情
     * @param orderId
     */
    public void getErrandServiceDetail(String orderId){
        errandServiceDetailView.showLoading();
        getExpressDetailRepo.getErrandServiceDetail(orderId)
                .compose(RxUtil.<ExpressDetailOutputEntity>applySchedulers())
                .subscribe(new OldBaseObserver<ExpressDetailOutputEntity>(errandServiceDetailView) {
                    @Override
                    public void onNext(ExpressDetailOutputEntity expressDetailOutputEntity) {
                        errandServiceDetailView.hideLoading();
                        errandServiceDetailView.renderDetail(expressDetailOutputEntity);
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
