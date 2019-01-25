package com.homepaas.sls.wxapi;

import com.homepaas.sls.event.PayAbortEvent;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/7/5.
 */
public class WXPayResultActivity extends WXCallbackActivity {

    @Override
    public void onResp(BaseResp resp) {
        super.onResp(resp);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {//支付成功
                EventBus.getDefault().post(new PayAbortEvent("支付成功",0));
                this.finish();
            } else if (resp.errCode == -2) {
                EventBus.getDefault().post(new PayAbortEvent("支付成功",-2));
                finish();
            } else {
                //未知错误，重试
            }
        }
    }
}
