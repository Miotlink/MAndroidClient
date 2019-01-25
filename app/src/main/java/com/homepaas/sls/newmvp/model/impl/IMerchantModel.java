package com.homepaas.sls.newmvp.model.impl;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.entity.BusinessExInfoOutput;
import com.homepaas.sls.data.entity.BusinessOrderServiceListEntity;
import com.homepaas.sls.data.entity.BusinessSecServiceEntity;
import com.homepaas.sls.data.entity.BusinessServiceListEntity;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.BusinessOrderServiceListRequest;
import com.homepaas.sls.data.network.dataformat.request.GetBusinessCommentRequest;
import com.homepaas.sls.data.network.dataformat.request.GetBusinessInfoRequest;
import com.homepaas.sls.data.network.dataformat.request.GetBusinessSecServiceRequest;
import com.homepaas.sls.data.network.dataformat.request.GetBusinessServiceListRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.BusinessCommentListOutput;
import com.homepaas.sls.httputils.BaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.newmvp.base.IBaseModel;
import com.homepaas.sls.newmvp.contract.MerchantContract;

import rx.Subscription;

/**
 * Created by mhy on 2017/7/24.
 */


public class IMerchantModel extends IBaseModel implements MerchantContract.Model {

    PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();

    @Override
    public void getBusinessComment(BaseView baseView, String userType, String merchantOrWorkerId, String isEnablePaging, String pageIndex, String pageSize, RetrofitRequestCallBack<BusinessCommentListOutput> retrofitRequestCallBack) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            GetBusinessCommentRequest getBusinessCommentRequest = new GetBusinessCommentRequest(token, userType, merchantOrWorkerId, pageIndex, pageSize, isEnablePaging);
            Subscription subscribe = apiHelper.restApi()
                    .getBusinessCommentList(getBusinessCommentRequest)
                    .compose(RxUtil.<ResponseWrapper<BusinessCommentListOutput>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<BusinessCommentListOutput>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<BusinessCommentListOutput>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<BusinessCommentListOutput>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBusinessSecService(BaseView baseView, String userType, String merchantOrWorkerId, RetrofitRequestCallBack<BusinessSecServiceEntity> retrofitRequestCallBack) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            GetBusinessSecServiceRequest getBusinessSecServiceRequest = new GetBusinessSecServiceRequest(token, userType, merchantOrWorkerId);
            Subscription subscribe = apiHelper.restApi()
                    .getBusinessSecService(getBusinessSecServiceRequest)
                    .compose(RxUtil.<ResponseWrapper<BusinessSecServiceEntity>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<BusinessSecServiceEntity>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<BusinessSecServiceEntity>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<BusinessSecServiceEntity>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBusinessServiceList(BaseView baseView, String serviceId, String userType, String merchantOrWorkerId,double Latitude, double Longitude, String isEnablePaging, String pageIndex, String pageSize, RetrofitRequestCallBack<BusinessServiceListEntity> retrofitRequestCallBack) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            GetBusinessServiceListRequest getBusinessServiceListRequest = new GetBusinessServiceListRequest(token, serviceId, userType, merchantOrWorkerId, Latitude,  Longitude);
            Subscription subscribe = apiHelper.restApi()
                    .getBusinessServiceList(getBusinessServiceListRequest)
                    .compose(RxUtil.<ResponseWrapper<BusinessServiceListEntity>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<BusinessServiceListEntity>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<BusinessServiceListEntity>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<BusinessServiceListEntity>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMerchantOrWorkerInfo(BaseView baseView, String userType, String merchantOrWorkerId, RetrofitRequestCallBack<BusinessExInfoOutput> retrofitRequestCallBack) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            GetBusinessInfoRequest getBusinessInfoRequest = new GetBusinessInfoRequest(token, userType, merchantOrWorkerId);
            Subscription subscribe = apiHelper.restApi()
                    .getBusinessInfo(getBusinessInfoRequest)
                    .compose(RxUtil.<ResponseWrapper<BusinessExInfoOutput>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<BusinessExInfoOutput>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<BusinessExInfoOutput>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<BusinessExInfoOutput>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBusinessOrderServiceList(BaseView baseView, String serviceId, String Latitude, String Longitude, String pageIndex, String pageSize, String tagId, RetrofitRequestCallBack<BusinessOrderServiceListEntity> retrofitRequestCallBack) {
        BusinessOrderServiceListRequest businessOrderServiceListRequest = new BusinessOrderServiceListRequest(serviceId, Latitude, Longitude, pageIndex, pageSize, tagId);
        Subscription subscribe = apiHelper.restApi()
                .getBusinessOrderServiceList(businessOrderServiceListRequest)
                .compose(RxUtil.<ResponseWrapper<BusinessOrderServiceListEntity>>applySchedulers())//处理公共线程切换代码
                .compose(RxUtil.<BusinessOrderServiceListEntity>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                .compose(RxUtil.<BusinessOrderServiceListEntity>progressDialogObservable(baseView, true)) //处理弹框
                .subscribe(new BaseObserver<BusinessOrderServiceListEntity>(retrofitRequestCallBack, baseView));
        subscriptionArrayList.add(subscribe);
    }
}
