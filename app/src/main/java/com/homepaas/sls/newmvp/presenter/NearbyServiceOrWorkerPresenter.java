package com.homepaas.sls.newmvp.presenter;

import com.homepaas.sls.domain.entity.SelectServiceOrWorkerEntity;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.IBasePresenter;
import com.homepaas.sls.newmvp.contract.NearbyServiceOrWorkerContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;

/**
 * on 2017/6/4.
 */

public class NearbyServiceOrWorkerPresenter extends IBasePresenter<NearbyServiceOrWorkerContract.View> implements NearbyServiceOrWorkerContract.Presenter {

    private NearbyServiceOrWorkerContract.Model nearbyServiceOrWorkerModel = ModelFactory.getInstance().getNearbyServiceOrWorkerModel();


    @Override
    public void dispose() {
        if (nearbyServiceOrWorkerModel != null)
            nearbyServiceOrWorkerModel.dispose();
    }


    //    TabType：1：最近Tab，2：附近Tab；必填
//    ServiceId：新服务体系的四级服务品类的Id；必填
//    AddressId：地址Id；必填
//    IsEnablePaging：是否启用分页；0：不启用，1：启用，默认启用
//    PageIndex：第几页；，默认第1页
//    PageSize：每页显示多少条；默认10条
    @Override
    public void getListData(String tabType, String serviceId, String addressId, String isEnablePaging, String pageIndex, String pageSize) {
        mView.showLoading(true);
        nearbyServiceOrWorkerModel.getListData(mView, tabType, serviceId, addressId, isEnablePaging, pageIndex, pageSize, new RetrofitRequestCallBack<SelectServiceOrWorkerEntity>() {
            @Override
            public void successRequest(SelectServiceOrWorkerEntity data) {
                mView.initListData(data);
            }
        });
    }
}
