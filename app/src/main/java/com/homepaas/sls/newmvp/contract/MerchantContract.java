package com.homepaas.sls.newmvp.contract;

import com.homepaas.sls.data.entity.BusinessExInfoOutput;
import com.homepaas.sls.data.entity.BusinessOrderServiceListEntity;
import com.homepaas.sls.data.entity.BusinessSecServiceEntity;
import com.homepaas.sls.data.entity.BusinessServiceListEntity;
import com.homepaas.sls.domain.entity.BusinessCommentListOutput;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.BaseModel;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2017/6/4.
 */

public interface MerchantContract {

    interface Model extends BaseModel {
        void getBusinessComment(BaseView baseView, String serviceId, String addressId, String isEnablePaging, String pageIndex, String pageSize, RetrofitRequestCallBack<BusinessCommentListOutput> retrofitRequestCallBack);

        void getBusinessSecService(BaseView baseView, String UserType, String MerchantOrWorkerId, RetrofitRequestCallBack<BusinessSecServiceEntity> retrofitRequestCallBack);

        void getBusinessServiceList(BaseView baseView, String serviceId, String UserType, String MerchantOrWorkerId,double Latitude, double Longitude, String isEnablePaging, String pageIndex, String pageSize, RetrofitRequestCallBack<BusinessServiceListEntity> retrofitRequestCallBack);

        void getMerchantOrWorkerInfo(BaseView baseView, String userType, String merchantOrWorkerId, RetrofitRequestCallBack<BusinessExInfoOutput> retrofitRequestCallBack);

        void getBusinessOrderServiceList(BaseView baseView, String serviceId, String Latitude, String Longitude, String pageIndex, String pageSize,String tagId, RetrofitRequestCallBack<BusinessOrderServiceListEntity> retrofitRequestCallBack);
    }

    interface View extends BaseView {
        void initBusinessOrderServiceList(BusinessOrderServiceListEntity businessOrderServiceListEntity);

        void initMerchantOrWorkerInfo(BusinessExInfoOutput businessExInfoOutput);

        void initBusinessSecService(BusinessSecServiceEntity businessSecServiceEntity);

        void initBusinessServiceList(BusinessServiceListEntity businessServiceListEntity);

        void initBusinessCommentList(BusinessCommentListOutput businessCommentListOutput);
    }

    interface Presenter extends BasePresenter<View> {
        void getBusinessOrderServiceList(String serviceId, String Latitude, String Longitude, String pageIndex, String pageSize,String tagId);

        void getBusinessComment(String UserType, String MerchantOrWorkerId, String isEnablePaging, String pageIndex, String pageSize);

        void getBusinessServiceList(String serviceId, String UserType, String MerchantOrWorkerId,double Latitude, double Longitude, String isEnablePaging, String pageIndex, String pageSize);

        void getBusinessSecService(String UserType, String MerchantOrWorkerId);

        void getMerchantOrWorkerInfo(String userType, String merchantOrWorkerId);
    }
}
