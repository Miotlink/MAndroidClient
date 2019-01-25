package com.homepaas.sls.newmvp.model.impl;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.dataformat.CollectedWorkerBody;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.CollectWorkerRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.httputils.BaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.newmvp.base.IBaseModel;
import com.homepaas.sls.newmvp.contract.ColleactionWorkerContract;

import rx.Subscription;

/**
 * Created by mhy on 2017/7/24.
 */


public class IColleactionWorkerModel extends IBaseModel implements ColleactionWorkerContract.Model {

    PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();


    @Override
    public void getListData(BaseView baseView, RetrofitRequestCallBack<CollectedWorkerBody> collectedWorkerBody) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            TokenRequest tokenRequest = new TokenRequest(token);
            Subscription subscribe = apiHelper.restApi()
                    .mewGetCollectedWorkers(tokenRequest)
                    .compose(RxUtil.<ResponseWrapper<CollectedWorkerBody>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<CollectedWorkerBody>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<CollectedWorkerBody>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<CollectedWorkerBody>(collectedWorkerBody, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void cancelCollectWorker(BaseView baseView, String workId, boolean collect, RetrofitRequestCallBack<String> callBack) {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            CollectWorkerRequest collectWorkerRequest = new CollectWorkerRequest(token, workId, collect);
            Subscription subscribe = apiHelper.restApi()
                    .newCollectWorker(collectWorkerRequest)
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
