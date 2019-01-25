package com.homepaas.sls.newmvp.model.impl;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.CallLogRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.httputils.BaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.newmvp.base.IBaseModel;
import com.homepaas.sls.newmvp.contract.OrderStatusContract;

import rx.Subscription;

/**
 * Created by mhy on 2017/7/24.
 */


public class IOrderStatusModel extends IBaseModel implements OrderStatusContract.Model {

    PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();

    @Override
    public void callPhoneBuly(BaseView baseView, String id, int callType, String phone, int isDial, String callTime, String reason, RetrofitRequestCallBack<String> retrofitRequestCallBack) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            CallLogRequest request = new CallLogRequest();
            request.setReason(reason);

            request.setId(id);
            request.setServiceTypeId(id);
            request.setPhone(phone);
            request.setCallTime(callTime);
//            request.setCallType(callType);
            request.setIsDial(isDial);
//            Device device=new Device(SimpleTinkerInApplicationLike.getMainApplication());
//            device.getDeviceId();
//            request.setDeviceId(deviceId);
            request.setToken(token);
            Subscription subscribe = apiHelper.restApi()
                    .callPhoneBuly(request)
                    .compose(RxUtil.<ResponseWrapper<String>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<String>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<String>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<String>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
            retrofitRequestCallBack.onFinish();
        }
    }
}
