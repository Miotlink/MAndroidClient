package com.homepaas.sls.newmvp.contract;

import com.homepaas.sls.data.entity.HomeOrderStatusEntity;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.BaseModel;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2017/6/4.
 */

public interface HomeContract {

    interface Model extends BaseModel {
        void getOrderStatus(BaseView baseView, RetrofitRequestCallBack<HomeOrderStatusEntity> retrofitRequestCallBack);
    }

    interface View extends BaseView {
        void initOrderStatus(HomeOrderStatusEntity homeOrderStatusEntity);
    }

    interface Presenter extends BasePresenter<View> {
        void getOrderStatus();
    }
}
