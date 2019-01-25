package com.homepaas.sls.mvp.presenter.order;

import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.interactor.GetBusinessInfoInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.mapper.ServiceInfoModelMapper;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.order.BusinessOrderPlaceView;

import javax.inject.Inject;

/**
 * Created by CJJ on 2016/6/20.
 */
public class BusinessInfoPresenter implements Presenter{

    BusinessOrderPlaceView businessOrderPlaceView;

    GetBusinessInfoInteractor getBusinessInfoInteractor;

    ServiceInfoModelMapper modelMapper;

    public void setBusinessOrderPlaceView(BusinessOrderPlaceView businessOrderPlaceView) {
        this.businessOrderPlaceView = businessOrderPlaceView;
    }

    @Inject
    public BusinessInfoPresenter(GetBusinessInfoInteractor getBusinessInfoInteractor,ServiceInfoModelMapper mapper) {
        this.getBusinessInfoInteractor = getBusinessInfoInteractor;
        this.modelMapper = mapper;
    }

    public void getBusinessInfo(String businessId){
        getBusinessInfoInteractor.setBusinessId(businessId);
        getBusinessInfoInteractor.execute(new OldBaseObserver<BusinessInfo>(businessOrderPlaceView) {
            @Override
            public void onNext(BusinessInfo businessInfo) {
                BusinessInfoModel businessInfoModel = modelMapper.transformBusiness(businessInfo);
                businessOrderPlaceView.render(businessInfoModel);
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
        getBusinessInfoInteractor.unsubscribe();
    }
}
