package com.homepaas.sls.newmvp.model.impl;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.SelectServiceOrWorkerRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.SelectServiceOrWorkerEntity;
import com.homepaas.sls.httputils.BaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.newmvp.base.IBaseModel;
import com.homepaas.sls.newmvp.contract.RecentlyServiceOrWorkerContract;

import rx.Subscription;

/**
 * Created by mhy on 2017/7/24.
 */


public class IRecentlyServiceOrWorkerModel extends IBaseModel implements RecentlyServiceOrWorkerContract.Model {

    PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();


    @Override
    public void getListData(BaseView baseView, String tabType, String serviceId, String addressId, String isEnablePaging, String pageIndex, String pageSize, RetrofitRequestCallBack<SelectServiceOrWorkerEntity> retrofitRequestCallBack) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            SelectServiceOrWorkerRequest iodOfWxpayRequest = new SelectServiceOrWorkerRequest(token, tabType, serviceId, addressId, isEnablePaging, pageIndex, pageSize);
            Subscription subscribe = apiHelper.restApi()
                    .getChooseWorkerOrMerchantList(iodOfWxpayRequest)
                    .compose(RxUtil.<ResponseWrapper<SelectServiceOrWorkerEntity>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<SelectServiceOrWorkerEntity>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<SelectServiceOrWorkerEntity>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<SelectServiceOrWorkerEntity>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }
}
