package com.homepaas.sls.newmvp.model.impl;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.entity.OrderListPopEntity;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.dataformat.OrderDetailBody;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.GetOrderDetailRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.httputils.BaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.newmvp.base.IBaseModel;
import com.homepaas.sls.newmvp.contract.OrderContract;

import rx.Subscription;

/**
 * Created by mhy on 2017/7/24.
 */


public class IOrderModel extends IBaseModel implements OrderContract.Model {

    PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();

    @Override
    public void getOrderListPop(BaseView baseView, RetrofitRequestCallBack<OrderListPopEntity> retrofitRequestCallBack) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            TokenRequest tokenRequest = new TokenRequest(token);
            Subscription subscribe = apiHelper.restApi()
                    .GetOrderListPop(tokenRequest)
                    .compose(RxUtil.<ResponseWrapper<OrderListPopEntity>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<OrderListPopEntity>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<OrderListPopEntity>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<OrderListPopEntity>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getOrderDetail(BaseView baseView,String orderId, RetrofitRequestCallBack<OrderDetailBody> retrofitRequestCallBack) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            GetOrderDetailRequest getOrderDetailRequest=new GetOrderDetailRequest(token,orderId);
            Subscription subscribe = apiHelper.restApi().getNewOrderDetail(getOrderDetailRequest)
                    .compose(RxUtil.<ResponseWrapper<OrderDetailBody>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<OrderDetailBody>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<OrderDetailBody>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<OrderDetailBody>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }
}
