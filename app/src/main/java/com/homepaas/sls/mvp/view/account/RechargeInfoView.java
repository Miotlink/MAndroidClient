package com.homepaas.sls.mvp.view.account;

import com.homepaas.sls.domain.entity.RechargeInfoResponse;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/12/7.
 */

public interface RechargeInfoView extends BaseView {
    void renderRechargeInfo(RechargeInfoResponse rechargeInfoResponse);
}
