package com.homepaas.sls.mvp.presenter.account;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.common.util.PayResult;
import com.homepaas.sls.domain.entity.AliPaySignEntry;
import com.homepaas.sls.domain.entity.WXPaySignEntry;
import com.homepaas.sls.domain.repository.AlipaySignExRepo;
import com.homepaas.sls.domain.repository.WxpaySignExRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.account.RechargeView;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/12/6.
 */

public class NewRechargePresenter implements Presenter {
    private static final String TAG = "NewRechargePresenter";
    private RechargeView rechargeView = null;
    private Context context;
    private IWXAPI WXAPI;
    private static final int SDK_PAY_FLAG = 1;
    private String outTradeNo;


    public void setWXAPI(IWXAPI WXAPI) {
        this.WXAPI = WXAPI;
    }

    @Inject
    public NewRechargePresenter() {
    }

    public void setRechargeView(RechargeView rechargeView, Context context) {
        this.rechargeView = rechargeView;
        this.context = context;
        WXAPI = WXAPIFactory.createWXAPI(context, null);
    }

    @Inject
    AlipaySignExRepo alipaySignExRepo;

    @Inject
    WxpaySignExRepo wxpaySignExRepo;

    public void payByAli(String activityId, String neddPay, String totalMoney, String activity) {
        alipaySignExRepo.getAlipaySign(activityId, neddPay, totalMoney, activity)
               .compose(RxUtil.<AliPaySignEntry>applySchedulers())
                .subscribe(new OldBaseObserver<AliPaySignEntry>(rechargeView) {
                    @Override
                    public void showError(Throwable t) {
//                        super.showError(t);
                        rechargeView.onRechargetFail();
                    }

                    @Override
                    public void onNext(AliPaySignEntry aliPaySignEntry) {
                        final String sign = aliPaySignEntry.getAlipaysign();
                        outTradeNo = aliPaySignEntry.getOutTradeNo();
                        startAliPay(sign);
                    }
                });

    }

    private void startAliPay(final String sign) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                PayTask payTask = new PayTask((Activity) context);
                String result = payTask.pay(sign, true);

                Message message = Message.obtain();
                message.what = SDK_PAY_FLAG;
                message.obj = result;
                handler.sendMessage(message);
            }
        };

        new Thread(runnable).start();
    }

    public void payByWx(String activityId, String neddPay, String totalMoney, String activity) {
        wxpaySignExRepo.getWxpaySign(activityId, neddPay, totalMoney, activity)
                .compose(RxUtil.<WXPaySignEntry>applySchedulers())
                .subscribe(new OldBaseObserver<WXPaySignEntry>(rechargeView) {

                    @Override
                    public void showError(Throwable t) {
//                        super.showError(t);
                        rechargeView.onError();
                    }

                    @Override
                    public void onNext(WXPaySignEntry wxPaySignEntry) {
                        //服务器获取订单地址
                        WXPaySignEntry.WxpaySign wxpaySign = wxPaySignEntry.getWxpaySign();
                        outTradeNo = wxPaySignEntry.getOutTradeNo();
                        rechargeView.returnRechargeCode(outTradeNo);
                        if (null != wxpaySign) {
                            PayReq req = new PayReq();
                            req.appId = wxpaySign.getAppid();
                            req.partnerId = wxpaySign.getPartnerid();
                            req.prepayId = wxpaySign.getPrepayid();
                            req.nonceStr = wxpaySign.getNoncestr();
                            req.timeStamp = wxpaySign.getTimestamp();
                            req.packageValue = wxpaySign.getPackageX();
                            req.sign = wxpaySign.getSign();
                            req.extData = "WxPay";
                            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                            if (BuildConfig.DEBUG)
                                Log.i(TAG, "onSucceed: " + wxpaySign.getAppid());
                            rechargeView.onAppIdReceive(req.appId);
                            WXAPI.sendReq(req);
                        } else {
                            if (BuildConfig.DEBUG)
                                Log.d("PAY_GET", "返回错误");
                        }
                    }
                });

    }


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {

                        rechargeView.onRechargeSuccess(outTradeNo);

                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            rechargeView.onRechargetFail();

                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            rechargeView.onRechargeCancel();
                        } else {
                            rechargeView.onRechargetFail();
                        }
                    }
                    break;
            }
        }
    };


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
