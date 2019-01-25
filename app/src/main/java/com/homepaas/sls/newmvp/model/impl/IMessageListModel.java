package com.homepaas.sls.newmvp.model.impl;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.dataformat.MessageBody;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.MessageListRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.httputils.BaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.newmvp.base.IBaseModel;
import com.homepaas.sls.newmvp.contract.MessageContract;

import rx.Subscription;

/**
 * Created by mhy on 2017/7/24.
 */


public class IMessageListModel extends IBaseModel implements MessageContract.Model {

    PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();

    @Override
    public void getListData(BaseView baseView, String IsEnablePaging, String PageIndex, String PageSize, RetrofitRequestCallBack<MessageBody> retrofitRequestCallBack) {

        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            MessageListRequest messageListRequest = new MessageListRequest(token, IsEnablePaging,  PageIndex,  PageSize);
            Subscription subscribe = apiHelper.restApi()
                    .getNewsMessageList(messageListRequest)
                    .compose(RxUtil.<ResponseWrapper<MessageBody>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<MessageBody>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<MessageBody>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<MessageBody>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMessageAll(BaseView baseView, RetrofitRequestCallBack<String> retrofitRequestCallBack) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            TokenRequest tokenRequest = new TokenRequest(token);
            Subscription subscribe = apiHelper.restApi()
                    .deleteMessageAll(tokenRequest)
                    .compose(RxUtil.<ResponseWrapper<String>>applySchedulers())//处理公共线程切换代码
                    .compose(RxUtil.<String>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                    .compose(RxUtil.<String>progressDialogObservable(baseView, true)) //处理弹框
                    .subscribe(new BaseObserver<String>(retrofitRequestCallBack, baseView));
            subscriptionArrayList.add(subscribe);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
    }
}
