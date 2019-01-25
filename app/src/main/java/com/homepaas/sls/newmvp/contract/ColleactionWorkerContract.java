
package com.homepaas.sls.newmvp.contract;

import com.homepaas.sls.data.network.dataformat.CollectedWorkerBody;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.BaseModel;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2017/6/4.
 */

public interface ColleactionWorkerContract {

    interface Model extends BaseModel {
        void getListData(BaseView baseView, RetrofitRequestCallBack<CollectedWorkerBody> collectedWorkerBody);

        void cancelCollectWorker(BaseView baseView, String workId, boolean collect, RetrofitRequestCallBack<String> callBack);
    }

    interface View extends BaseView {
        void initListData(CollectedWorkerBody collectedWorkerBody);
        void cancelCollectWorkerSuccess();
    }

    interface Presenter extends BasePresenter<View> {
        void getListData();
        void cancelCollectWorker(String workId, boolean collect);
    }
}
