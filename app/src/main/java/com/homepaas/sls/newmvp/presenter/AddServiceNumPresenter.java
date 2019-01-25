package com.homepaas.sls.newmvp.presenter;

import com.homepaas.sls.data.entity.AddServiceAlipayEntity;
import com.homepaas.sls.data.entity.AddServiceWxpayEntity;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.IBasePresenter;
import com.homepaas.sls.newmvp.contract.AddServiceNumContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;

/**
 * on 2017/6/4.
 */

public class AddServiceNumPresenter extends IBasePresenter<AddServiceNumContract.View> implements AddServiceNumContract.Presenter {

    private AddServiceNumContract.Model iHomeModel = ModelFactory.getInstance().getAddServiceNumModel();


    @Override
    public void dispose() {
        if (iHomeModel != null)
            iHomeModel.dispose();
    }

    @Override
    public void getBalanceInfo() {
        mView.showLoading(true);
        iHomeModel.getBalanceInfo(mView, new RetrofitRequestCallBack<NewAccountInfo>() {
            @Override
            public void successRequest(NewAccountInfo data) {
                mView.initBalanceInfo(data);
            }
        });
    }

    @Override
    public void iodOfBalancePay(String orderId, String balancePay) {
        iHomeModel.iodOfBalancePay(mView, orderId, balancePay, new RetrofitRequestCallBack<String>() {
            @Override
            public void successRequest(String data) {
               mView.onBalancePayResult(data);
            }
        });
    }

    @Override
    public void iodOfWxpay(String orderId, String balancePay, String wxpay) {
        iHomeModel.iodOfWxpay(mView, orderId, balancePay, wxpay, new RetrofitRequestCallBack<AddServiceWxpayEntity>() {
            @Override
            public void successRequest(AddServiceWxpayEntity data) {
                mView.iodOfWxpay(data);
            }
        });
    }

    @Override
    public void iodOfAlipay(String orderId, String balancePay, String alipay) {
        iHomeModel.iodOfAlipay(mView, orderId, balancePay, alipay, new RetrofitRequestCallBack<AddServiceAlipayEntity>() {
            @Override
            public void successRequest(AddServiceAlipayEntity data) {
                mView.iodOfAlipay(data);
            }
        });
    }

}
