package com.homepaas.sls.mvp.presenter.order;

import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.KdTrackInfoResponse;
import com.homepaas.sls.domain.interactor.GetOrderDetailInteractor;
import com.homepaas.sls.domain.repository.GetKdTrackInfoRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.order.ExpressOrderDetailView;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/6/5.
 */

public class ExpressOrderDetailPresenter implements Presenter {
    private ExpressOrderDetailView expressOrderDetailView;
    public void setExpressOrderDetailView(ExpressOrderDetailView expressOrderDetailView){
        this.expressOrderDetailView=expressOrderDetailView;
    }
    @Inject
    public ExpressOrderDetailPresenter() {
    }
    @Inject
    GetOrderDetailInteractor getOrderDetailInteractor;//订单详情
    @Inject
    GetKdTrackInfoRepo getKdTrackInfoRepo;


    /**
     * 获取订单详情
     *
     * @param orderId
     */
    public void getExpressOrderDetail(String orderId) {
        expressOrderDetailView.showLoading();
        getOrderDetailInteractor.setOrderId(orderId);
        getOrderDetailInteractor.execute(new OldBaseObserver<DetailOrderEntity>(expressOrderDetailView) {
            @Override
            public void onNext(DetailOrderEntity orderInfo) {
                expressOrderDetailView.renderOrderDetail(orderInfo);
            }
        });
    }

    /**
     * 物流信息
     * @param logisticCode
     * @param shipperCode
     */
    public void getKdTrackInfo(String logisticCode, String shipperCode){
        getKdTrackInfoRepo.getKdTrackInfo(logisticCode,shipperCode)
               .compose(RxUtil.<KdTrackInfoResponse>applySchedulers())
                .subscribe(new OldBaseObserver<KdTrackInfoResponse>(expressOrderDetailView) {
                    @Override
                    public void onNext(KdTrackInfoResponse kdTrackInfoResponse) {
                        expressOrderDetailView.renderKdTrackInfo(kdTrackInfoResponse);
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
        getOrderDetailInteractor.unsubscribe();
    }
}
