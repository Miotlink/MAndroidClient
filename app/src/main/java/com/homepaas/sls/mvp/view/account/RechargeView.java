package com.homepaas.sls.mvp.view.account;


import com.homepaas.sls.newmvp.base.BaseView;

public interface RechargeView extends BaseView {
    //充值失败回调
    void onRechargetFail();

    //充值成功回调
    void onRechargeSuccess(String orderId);

    //充值确认中
    void onRechargeEnsure();

    void onRechargeCancel();

    void onAppIdReceive(String appId);

    void onError();
    void returnRechargeCode(String rechargeCode);
}
