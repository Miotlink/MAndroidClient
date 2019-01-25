package com.homepaas.sls.mvp.presenter.order;

import com.homepaas.sls.domain.entity.BusinessServicePricesEntity;
import com.homepaas.sls.domain.repository.BusinessServicePriceRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.order.BusinessServicePriceView;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/1/9.
 */

public class BusinessServicePricePresenter implements Presenter {

    @Inject
    public BusinessServicePricePresenter() {
    }
    private BusinessServicePriceView businessServicePriceView;
    public void setBusinessServicePriceView(BusinessServicePriceView businessServicePriceView){
        this.businessServicePriceView=businessServicePriceView;
    }

    @Inject
    BusinessServicePriceRepo businessServicePriceRepo;

    public void getServicePriceList(String merchantId, String serviceProviderType, String serviceAddressId){
        businessServicePriceRepo.getBusinessServicePriceList(merchantId,serviceProviderType,serviceAddressId)
               .compose(RxUtil.<BusinessServicePricesEntity>applySchedulers())
                .subscribe(new OldBaseObserver<BusinessServicePricesEntity>(businessServicePriceView) {
                    @Override
                    public void onNext(BusinessServicePricesEntity businessServicePricesEntity) {
                        businessServicePriceView.onBusinessPriceResult(businessServicePricesEntity);
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
