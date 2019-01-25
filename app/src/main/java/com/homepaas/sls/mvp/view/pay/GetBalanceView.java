package com.homepaas.sls.mvp.view.pay;

import com.homepaas.sls.domain.entity.BalanceInfo;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by CMJ on 2016/6/25.
 */

public interface GetBalanceView extends BaseView {

    void render(BalanceInfo balanceInfo);
}
