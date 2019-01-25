package com.homepaas.sls.mvp.presenter.newdetail;

import com.homepaas.sls.domain.entity.MerchantServicePriceListEntity;
import com.homepaas.sls.domain.repository.MerchantServicePriceListRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.newdetail.MerchantServicePriceListView;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/2/13.
 */

public class MerchantServicePriceListPresenter implements Presenter {

    @Inject
    public MerchantServicePriceListPresenter() {
    }


    MerchantServicePriceListView merchantServicePriceListView;

    public void setMerchantServicePriceListView(MerchantServicePriceListView merchantServicePriceListView) {
        this.merchantServicePriceListView = merchantServicePriceListView;
    }

    @Inject
    MerchantServicePriceListRepo merchantServicePriceListRepo;

    public void getMerchantServicePriceLis(String id) {
        merchantServicePriceListRepo.getMerchantServicePriceList(id)
                .compose(RxUtil.<MerchantServicePriceListEntity>applySchedulers())
                .subscribe(new OldBaseObserver<MerchantServicePriceListEntity>(merchantServicePriceListView) {
                    @Override
                    public void onNext(MerchantServicePriceListEntity merchantServicePriceListEntity) {
                        merchantServicePriceListView.render(merchantServicePriceListEntity);
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
