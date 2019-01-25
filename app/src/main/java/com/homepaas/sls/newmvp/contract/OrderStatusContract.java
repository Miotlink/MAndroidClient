package com.homepaas.sls.newmvp.contract;

import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.BaseModel;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2017/6/4.
 */

public interface OrderStatusContract {

    interface Model extends BaseModel {
        void callPhoneBuly(BaseView baseView,String id, int callType, String phone, int isDial, String callTime, String reason, RetrofitRequestCallBack<String> retrofitRequestCallBack);
    }

    interface View extends BaseView {
        void submitSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void callPhoneBuly(String id, int callType, String phone, int isDial, String callTime,  String reason);
    }
}
