package com.homepaas.sls.mvp.view.pay;

import com.homepaas.sls.domain.entity.WxSign;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/7/6.
 */
public interface GetDirectPaySignView extends BaseView {

    void onAliSign(String aliSign);
    void onWxSign(WxSign sign);
    void onBalancePayResult(String string);
}
