package com.homepaas.sls.newmvp.presenter;

import com.homepaas.sls.data.entity.BusinessExInfoOutput;
import com.homepaas.sls.data.entity.BusinessOrderServiceListEntity;
import com.homepaas.sls.data.entity.BusinessSecServiceEntity;
import com.homepaas.sls.data.entity.BusinessServiceListEntity;
import com.homepaas.sls.domain.entity.BusinessCommentListOutput;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.IBasePresenter;
import com.homepaas.sls.newmvp.contract.MerchantContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;

/**
 * on 2017/6/4.
 */

public class MerchantPresenter extends IBasePresenter<MerchantContract.View> implements MerchantContract.Presenter {

    private MerchantContract.Model merchantModel = ModelFactory.getInstance().getMerchantContract();

    @Override
    public void dispose() {
        if (merchantModel != null)
            merchantModel.dispose();
    }

    @Override
    public void getBusinessOrderServiceList(String serviceId, String Latitude, String Longitude, String pageIndex, String pageSize,String tagId) {
        merchantModel.getBusinessOrderServiceList(mView, serviceId, Latitude, Longitude, pageIndex, pageSize, tagId,new RetrofitRequestCallBack<BusinessOrderServiceListEntity>() {
            @Override
            public void successRequest(BusinessOrderServiceListEntity data) {
                mView.initBusinessOrderServiceList(data);
            }
        });
    }

    @Override
    public void getBusinessComment(String userType, String merchantOrWorkerId, String isEnablePaging, String pageIndex, String pageSize) {

        merchantModel.getBusinessComment(mView, userType, merchantOrWorkerId, isEnablePaging, pageIndex, pageSize, new RetrofitRequestCallBack<BusinessCommentListOutput>() {
            @Override
            public void successRequest(BusinessCommentListOutput data) {
                mView.initBusinessCommentList(data);
            }
        });
    }

    @Override
    public void getBusinessServiceList(String serviceId, String userType, String merchantOrWorkerId,double Latitude, double Longitude, String isEnablePaging, String pageIndex, String pageSize) {
        merchantModel.getBusinessServiceList(mView,serviceId, userType, merchantOrWorkerId, Latitude,  Longitude, isEnablePaging, pageIndex, pageSize, new RetrofitRequestCallBack<BusinessServiceListEntity>() {
            @Override
            public void successRequest(BusinessServiceListEntity data) {
                mView.initBusinessServiceList(data);
            }
        });
    }

    @Override
    public void getBusinessSecService(String UserType, String MerchantOrWorkerId) {
        merchantModel.getBusinessSecService(mView, UserType, MerchantOrWorkerId, new RetrofitRequestCallBack<BusinessSecServiceEntity>() {
            @Override
            public void successRequest(BusinessSecServiceEntity data) {
                mView.initBusinessSecService(data);
            }
        });
    }

    @Override
    public void getMerchantOrWorkerInfo(String userType, String merchantOrWorkerId) {
        merchantModel.getMerchantOrWorkerInfo(mView, userType, merchantOrWorkerId, new RetrofitRequestCallBack<BusinessExInfoOutput>() {
            @Override
            public void successRequest(BusinessExInfoOutput businessExInfoOutput) {
                mView.initMerchantOrWorkerInfo(businessExInfoOutput);
            }
        });
    }
}
