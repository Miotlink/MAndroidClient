package com.homepaas.sls.mvp.view.account;

import com.homepaas.sls.domain.entity.AccountListEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/11/29.
 */

public interface AccountListView extends BaseView {
    void renderSettlementList(AccountListEntity accountListEntity);
    void renderSettlenmentMoreList(AccountListEntity accountListEntity);
}
