
package com.homepaas.sls.newmvp.contract;

import com.homepaas.sls.data.network.dataformat.CollectedBusinessBody;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.BaseModel;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2017/6/4.
 */

public interface ColleactionMerchantContract {

    interface Model extends BaseModel {
        void getListData(BaseView baseView, RetrofitRequestCallBack<CollectedBusinessBody> collectedBusinessBody);

        void cancelCollectMerchant(BaseView baseView, String merchantId, boolean collect, RetrofitRequestCallBack<String> callBack);

    }

    interface View extends BaseView {
        void initListData(CollectedBusinessBody cllectedBusinessBody);

        void cancelCollectBusinessSuccess();

    }

    interface Presenter extends BasePresenter<View> {
        void getListData();

        void cancelCollectWorker(String merchantId, boolean collect);

    }
}
