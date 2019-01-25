package com.homepaas.sls.newmvp.model.impl;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.httputils.RequestCallBack;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.newmvp.base.IBaseModel;
import com.homepaas.sls.newmvp.contract.ServiceMerchantContract;

/**
 * Created by mhy on 2017/7/24.
 */


public class IServiceMerchantModel extends IBaseModel implements ServiceMerchantContract.Model {

    PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();



    @Override
    public void loadPersonalInfo(BaseView baseView, RequestCallBack requestCallBack) {
        String token = null;
//        try {
//            token = personalInfoPDataSource.getToken();
//            IodOfAlipayRequest iodOfAlipayRequest = new IodOfAlipayRequest();
//            iodOfAlipayRequest.setToken(token);
//            iodOfAlipayRequest.setOrderId(orderId);
//            iodOfAlipayRequest.setBalancePay(balancePay);
//            iodOfAlipayRequest.setAlipay(alipay);
//
//            Subscription subscribe = apiHelper.restApi()
//                    .IodOfAlipay(iodOfAlipayRequest)
//                    .compose(RxUtil.<ResponseWrapper<AddServiceAlipayEntity>>applySchedulers())//处理公共线程切换代码
//                    .compose(RxUtil.<AddServiceAlipayEntity>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
//                    .compose(RxUtil.<AddServiceAlipayEntity>progressDialogObservable(baseView, true)) //处理弹框
//                    .subscribe(new BaseObserver<AddServiceAlipayEntity>(retrofitRequestCallBack, baseView));
//            subscriptionArrayList.add(subscribe);
//        } catch (GetPersistenceDataException e) {
//            e.printStackTrace();
//        }
    }
}
