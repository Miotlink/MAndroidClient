package com.homepaas.sls.newmvp.model.impl;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.entity.ComplaintListEntity;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.ComplaintRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.httputils.BaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.newmvp.base.IBaseModel;
import com.homepaas.sls.newmvp.contract.ComplaintContract;

import rx.Subscription;

/**
 * Created by mhy on 2017/7/24.
 */


public class IComplaintModel extends IBaseModel implements ComplaintContract.Model {

    PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();

    @Override
    public void submitComplaint(BaseView baseView, String OrderId, String Indexs, String Titles, String Reason, RetrofitRequestCallBack<String> retrofitRequestCallBack) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            ComplaintRequest complaintRequest = new ComplaintRequest(token, OrderId, Indexs, Titles, Reason);
            Subscription subscribe = apiHelper.restApi()
                    .submitApplyComplaints(complaintRequest)
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
    public void getComplaintList(BaseView baseView, RetrofitRequestCallBack<ComplaintListEntity> retrofitRequestCallBack) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            TokenRequest tokenRequest = new TokenRequest(token);
            Subscription subscribe = apiHelper.restApi()
                    .getOrderComplaintsReasonList(tokenRequest)
                    .compose(RxUtil.<ResponseWrapper<ComplaintListEntity>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<ComplaintListEntity>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<ComplaintListEntity>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<ComplaintListEntity>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }

}
