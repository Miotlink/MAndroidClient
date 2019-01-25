package com.homepaas.sls.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.homepaas.sls.event.PayAbortEvent;
import com.homepaas.sls.event.SuccessPayEvent;
import com.homepaas.sls.event.WXSuccessPayEvent;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by CJJ on 2016/7/13.
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;
    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {//支付成功
                Log.i(TAG, "onResp: 支付成功！");
                EventBus.getDefault().post(new WXSuccessPayEvent());
                this.finish();
            } else if (resp.errCode == -2) {
                EventBus.getDefault().post(new PayAbortEvent("支付取消",-2));
                Log.d("111","执行到这里");
                finish();
            } else {
                //未知错误，重试
            }
        }
    }
}
