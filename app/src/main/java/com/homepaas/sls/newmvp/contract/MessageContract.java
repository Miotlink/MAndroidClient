package com.homepaas.sls.newmvp.contract;

import com.homepaas.sls.data.network.dataformat.MessageBody;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.BaseModel;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2017/6/4.
 */

public interface MessageContract {

//    IsEnablePaging：0：不启用分页，1：启用分页
//    PageIndex：第几页
//    PageSize：每一页多少条

    interface Model extends BaseModel {
        void getListData(BaseView baseView,String IsEnablePaging,String PageIndex, String PageSize, RetrofitRequestCallBack<MessageBody> retrofitRequestCallBack);
        void deleteMessageAll(BaseView baseView, RetrofitRequestCallBack<String> retrofitRequestCallBack);
    }

    interface View extends BaseView {
      }

    interface Presenter extends BasePresenter<View> {
     }
}
