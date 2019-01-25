package com.homepaas.sls.newmvp.contract;

import com.homepaas.sls.httputils.RequestCallBack;
import com.homepaas.sls.newmvp.base.BaseModel;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 *  on 2017/6/4.
 */

public interface ServiceMerchantContract {

    interface Model extends BaseModel {
        void loadPersonalInfo(BaseView baseView, final RequestCallBack requestCallBack);
    }

    interface View extends BaseView {
        void pushTelephone (String number);
    }

    interface Presenter extends BasePresenter<View> {
        void loadPersonalInfo();
    }
}
