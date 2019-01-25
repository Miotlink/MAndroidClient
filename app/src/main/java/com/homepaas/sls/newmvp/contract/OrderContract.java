package com.homepaas.sls.newmvp.contract;

import com.homepaas.sls.data.entity.OrderListPopEntity;
import com.homepaas.sls.data.network.dataformat.OrderDetailBody;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.BaseModel;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2017/6/4.
 */

public interface OrderContract {

    interface Model extends BaseModel {
        void getOrderListPop (BaseView baseView, RetrofitRequestCallBack<OrderListPopEntity> retrofitRequestCallBack);
        void getOrderDetail (BaseView baseView,String orderId, RetrofitRequestCallBack<OrderDetailBody> retrofitRequestCallBack);
    }

    interface View extends BaseView {
        void initOrderStatus(OrderListPopEntity orderListPopEntity);
    }

    interface Presenter extends BasePresenter<View> {
        void getOrderListPop();
    }
}
