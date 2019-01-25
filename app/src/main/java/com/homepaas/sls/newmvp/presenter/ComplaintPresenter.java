package com.homepaas.sls.newmvp.presenter;

import com.homepaas.sls.data.entity.ComplaintListEntity;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.IBasePresenter;
import com.homepaas.sls.newmvp.contract.ComplaintContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;

/**
 * on 2017/6/4.
 */

public class ComplaintPresenter extends IBasePresenter<ComplaintContract.View> implements ComplaintContract.Presenter {

    private ComplaintContract.Model iHomeModel = ModelFactory.getInstance().getComplaintModel();


    @Override
    public void dispose() {
        if (iHomeModel != null)
            iHomeModel.dispose();
    }

    /**
     * Indexs：必填；多个则以逗号隔开
     * Titles：必填； 多个则以逗号隔开
     * Reason：原因描述，选填；
     *
     * @param OrderId
     * @param Indexs
     * @param Titles
     * @param Reason
     */
    @Override
    public void submit(String OrderId, String Indexs, String Titles, String Reason) {
        mView.showLoading(true);
        iHomeModel.submitComplaint(mView, OrderId, Indexs, Titles, Reason, new RetrofitRequestCallBack<String>() {
            @Override
            public void successRequest(String v) {
                mView.submitSucess();//回传view
            }
        });
    }

    @Override
    public void getComplaintList() {
        mView.showLoading(true);
        iHomeModel.getComplaintList(mView, new RetrofitRequestCallBack<ComplaintListEntity>() {
            @Override
            public void successRequest(ComplaintListEntity data) {
                mView.initComplaintList(data.getList());
            }
        });
    }
}
