package com.homepaas.sls.newmvp.presenter;

import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.IBasePresenter;
import com.homepaas.sls.newmvp.contract.OrderStatusContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;

/**
 * on 2017/6/4.
 */

public class OrderStatusPresenter extends IBasePresenter<OrderStatusContract.View> implements OrderStatusContract.Presenter {

    private OrderStatusContract.Model orderStatusModel = ModelFactory.getInstance().getOrderStatusModel();


    @Override
    public void dispose() {
        if (orderStatusModel != null)
            orderStatusModel.dispose();
    }

    @Override
    public void callPhoneBuly(String id, int callType, String phone, int isDial, String callTime, String reason) {
        orderStatusModel.callPhoneBuly(mView, id, callType, phone, isDial, callTime, reason, new RetrofitRequestCallBack<String>() {
            @Override
            public void successRequest(String orderCancelReasonEntity) {
                mView.submitSuccess();
            }
        });
    }
}
