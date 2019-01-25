package com.homepaas.sls.mvp.view.account;

import com.homepaas.sls.domain.entity.NewAccountInfo;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/11/28.
 */

public interface WalletBalanceView extends BaseView {
    void renderAccountInfo(NewAccountInfo info);
}
