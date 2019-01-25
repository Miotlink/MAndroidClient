package com.homepaas.sls.newmvp.model.impl;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.entity.HomeOrderStatusEntity;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.httputils.BaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.newmvp.base.IBaseModel;
import com.homepaas.sls.newmvp.contract.HomeContract;

import rx.Subscription;

/**
 * Created by mhy on 2017/7/24.
 */


public class IHomeModel extends IBaseModel implements HomeContract.Model {

    PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();

    @Override
    public void getOrderStatus(BaseView baseView, RetrofitRequestCallBack<HomeOrderStatusEntity> retrofitRequestCallBack) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            TokenRequest tokenRequest = new TokenRequest(token);
            Subscription subscribe = apiHelper.restApi()
                    .GetOrderGlobalStatus(tokenRequest)
                    .compose(RxUtil.<ResponseWrapper<HomeOrderStatusEntity>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<HomeOrderStatusEntity>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<HomeOrderStatusEntity>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<HomeOrderStatusEntity>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }
}
