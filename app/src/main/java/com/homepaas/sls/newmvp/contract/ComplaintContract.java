package com.homepaas.sls.newmvp.contract;

import com.homepaas.sls.data.entity.ComplaintListEntity;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.BaseModel;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * on 2017/6/4.
 */

public interface ComplaintContract {

    interface Model extends BaseModel {
        void submitComplaint(BaseView baseView,String OrderId,String Indexs,String Titles,String Reason,RetrofitRequestCallBack<String> retrofitRequestCallBack);
        void getComplaintList(BaseView baseView,RetrofitRequestCallBack<ComplaintListEntity> retrofitRequestCallBack);
    }

    interface View extends BaseView {
        void initComplaintList(List<ComplaintListEntity.ListBean> listBeen);
        void submitSucess();
    }

    interface Presenter extends BasePresenter<View> {
        void submit(String OrderId,String Indexs,String Titles,String Reason);
        void getComplaintList();
    }
}
