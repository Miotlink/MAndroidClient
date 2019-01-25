package com.homepaas.sls.newmvp.contract;

import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.BaseModel;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * on 2017/6/4.
 */

public interface HistoryContract {

    interface Model extends BaseModel {
        void getHistoryOrder(BaseView baseView, RetrofitRequestCallBack<OrderEntity> retrofitRequestCallBack);
    }

    interface View extends BaseView {
        void initHistoryData(List<OrderEntity> orderEntities);
    }

    interface Presenter extends BasePresenter<View> {
        void getHistoryOrder();
    }
}
