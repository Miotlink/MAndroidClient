package com.homepaas.sls.newmvp.model.impl;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.entity.AddServiceAlipayEntity;
import com.homepaas.sls.data.entity.AddServiceWxpayEntity;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.IodOfAlipayRequest;
import com.homepaas.sls.data.network.dataformat.request.IodOfBalancePayRequest;
import com.homepaas.sls.data.network.dataformat.request.IodOfWxpayRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.httputils.BaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.newmvp.base.IBaseModel;
import com.homepaas.sls.newmvp.contract.AddServiceNumContract;

import rx.Subscription;

/**
 * Created by mhy on 2017/7/24.
 */


public class IAddServiceNumModel extends IBaseModel implements AddServiceNumContract.Model {

    PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();

    @Override
    public void getBalanceInfo(BaseView baseView, RetrofitRequestCallBack<NewAccountInfo> retrofitRequestCallBack) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        TokenRequest request = new TokenRequest(token);
        Subscription subscribe = apiHelper.restApi()
                .getAccountInfo(request)
                .compose(RxUtil.<ResponseWrapper<NewAccountInfo>>applySchedulers())//处理公共线程切换代码
                .compose(RxUtil.<NewAccountInfo>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                .compose(RxUtil.<NewAccountInfo>progressDialogObservable(baseView, true)) //处理弹框
                .subscribe(new BaseObserver<NewAccountInfo>(retrofitRequestCallBack, baseView));
        subscriptionArrayList.add(subscribe);
    }

    @Override
    public void iodOfBalancePay(BaseView baseView, String orderId, String balancePay, RetrofitRequestCallBack<String> retrofitRequestCallBack) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            IodOfBalancePayRequest iodOfBalancePayRequest = new IodOfBalancePayRequest();
            iodOfBalancePayRequest.setToken(token);
            iodOfBalancePayRequest.setOrderId(orderId);
            iodOfBalancePayRequest.setBalancePay(balancePay);
            Subscription subscribe = apiHelper.restApi()
                    .iodOfBalancePay(iodOfBalancePayRequest)
                    .compose(RxUtil.<ResponseWrapper<String>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<String>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<String>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<String>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void iodOfWxpay(BaseView baseView, String orderId, String balancePay, String wxpay, RetrofitRequestCallBack<AddServiceWxpayEntity> retrofitRequestCallBack) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            IodOfWxpayRequest iodOfWxpayRequest = new IodOfWxpayRequest();
            iodOfWxpayRequest.setToken(token);
            iodOfWxpayRequest.setOrderId(orderId);
            iodOfWxpayRequest.setBalancePay(balancePay);
            iodOfWxpayRequest.setWxpay(wxpay);

            Subscription subscribe = apiHelper.restApi()
                    .IodOfWxpay(iodOfWxpayRequest)
                    .compose(RxUtil.<ResponseWrapper<AddServiceWxpayEntity>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<AddServiceWxpayEntity>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<AddServiceWxpayEntity>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<AddServiceWxpayEntity>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void iodOfAlipay(BaseView baseView, String orderId, String balancePay, String alipay, RetrofitRequestCallBack<AddServiceAlipayEntity> retrofitRequestCallBack) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            IodOfAlipayRequest iodOfAlipayRequest = new IodOfAlipayRequest();
            iodOfAlipayRequest.setToken(token);
            iodOfAlipayRequest.setOrderId(orderId);
            iodOfAlipayRequest.setBalancePay(balancePay);
            iodOfAlipayRequest.setAlipay(alipay);

            Subscription subscribe = apiHelper.restApi()
                    .IodOfAlipay(iodOfAlipayRequest)
                    .compose(RxUtil.<ResponseWrapper<AddServiceAlipayEntity>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<AddServiceAlipayEntity>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<AddServiceAlipayEntity>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<AddServiceAlipayEntity>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }


}
