package com.homepaas.sls.newmvp.model.impl;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.OrderCancelReasonEntity;
import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.httputils.BaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.newmvp.base.IBaseModel;
import com.homepaas.sls.newmvp.contract.HistoryContract;

import rx.Subscription;

/**
 * Created by mhy on 2017/7/24.
 */


public class IHistoryModel extends IBaseModel implements HistoryContract.Model {

    PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();

    @Override
    public void getHistoryOrder(BaseView baseView, RetrofitRequestCallBack<OrderEntity> retrofitRequestCallBack) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        TokenRequest request = new TokenRequest(token);
        Subscription subscribe = apiHelper.restApi()
                .getOrderCancelReasonList(request)
                .compose(RxUtil.<ResponseWrapper<OrderCancelReasonEntity>>applySchedulers())//处理公共线程切换代码
                .compose(RxUtil.<OrderCancelReasonEntity>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                .compose(RxUtil.<OrderCancelReasonEntity>progressDialogObservable(baseView, true)) //处理弹框
                .subscribe(new BaseObserver<OrderCancelReasonEntity>(retrofitRequestCallBack, baseView));
        subscriptionArrayList.add(subscribe);
    }
}
