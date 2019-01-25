package com.homepaas.sls.newmvp.model.impl;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.dataformat.CollectedBusinessBody;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.CollectBusinessRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.httputils.BaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.newmvp.base.IBaseModel;
import com.homepaas.sls.newmvp.contract.ColleactionMerchantContract;

import rx.Subscription;

/**
 * Created by mhy on 2017/7/24.
 */


public class IColleactionMerchantModel extends IBaseModel implements ColleactionMerchantContract.Model {

    PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();


    @Override
    public void getListData(BaseView baseView, RetrofitRequestCallBack<CollectedBusinessBody> collectedBusinessBody) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            TokenRequest tokenRequest = new TokenRequest(token);
            Subscription subscribe = apiHelper.restApi()
                    .mewGCollectedBusinesses(tokenRequest)
                    .compose(RxUtil.<ResponseWrapper<CollectedBusinessBody>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<CollectedBusinessBody>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<CollectedBusinessBody>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<CollectedBusinessBody>(collectedBusinessBody, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelCollectMerchant(BaseView baseView, String merchantId, boolean collect, RetrofitRequestCallBack<String> callBack) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            CollectBusinessRequest collectedBusinessBody = new CollectBusinessRequest(token, merchantId, collect);
            Subscription subscribe = apiHelper.restApi()
                    .newCollectBusiness(collectedBusinessBody)
                    .compose(RxUtil.<ResponseWrapper<String>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<String>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<String>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<String>(callBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }
}
