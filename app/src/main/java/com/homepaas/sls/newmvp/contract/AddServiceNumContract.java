package com.homepaas.sls.newmvp.contract;

import com.homepaas.sls.data.entity.AddServiceAlipayEntity;
import com.homepaas.sls.data.entity.AddServiceWxpayEntity;
import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.BaseModel;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2017/6/4.
 */

public interface AddServiceNumContract {

    interface Model extends BaseModel {
        void getBalanceInfo(BaseView baseView, RetrofitRequestCallBack<NewAccountInfo> retrofitRequestCallBack);

        void iodOfBalancePay(BaseView baseView, String orderId, String balancePay, RetrofitRequestCallBack<String> retrofitRequestCallBack);

        void iodOfWxpay(BaseView baseView, String orderId, String balancePay, String wxpay, RetrofitRequestCallBack<AddServiceWxpayEntity> retrofitRequestCallBack);

        void iodOfAlipay(BaseView baseView, String orderId, String balancePay, String alipay, RetrofitRequestCallBack<AddServiceAlipayEntity> retrofitRequestCallBack);

    }

    interface View extends BaseView {
        void initBalanceInfo(NewAccountInfo newAccountInfo);
        void onBalancePayResult(String s);

        void iodOfWxpay(AddServiceWxpayEntity addServiceWxpayEntity);

        void iodOfAlipay(AddServiceAlipayEntity addServiceAlipayEntity);
    }

    interface Presenter extends BasePresenter<View> {
        void getBalanceInfo();

        void iodOfBalancePay(String orderId, String balancePay); //余额支付

        void iodOfWxpay(String orderId, String balancePay, String wxpay); //微信支付或混合

        void iodOfAlipay(String orderId, String balancePay, String alipay);//支付宝支付或混合
    }
}
