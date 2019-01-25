
package com.homepaas.sls.newmvp.contract;

import com.homepaas.sls.domain.entity.SelectServiceOrWorkerEntity;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.BaseModel;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2017/6/4.
 */

public interface RecentlyServiceOrWorkerContract {

    interface Model extends BaseModel {
        void getListData(BaseView baseView,String tabType, String serviceId, String addressId, String isEnablePaging, String pageIndex, String pageSize, RetrofitRequestCallBack<SelectServiceOrWorkerEntity> retrofitRequestCallBack);
    }

    interface View extends BaseView {
        void initListData(SelectServiceOrWorkerEntity selectServiceOrWorkerEntity);
    }

    interface Presenter extends BasePresenter<View> {
        void getListData(String tabType, String serviceId, String addressId, String isEnablePaging, String pageIndex, String pageSize);
    }
}
