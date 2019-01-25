package com.homepaas.sls.mvp.view.account;

import com.homepaas.sls.domain.entity.AccountDetailItemEntry;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/12/6.
 */

public interface AccountDetailsView extends BaseView {
    void renderAccountDetails(AccountDetailItemEntry accountDetailItemEntry);
}
