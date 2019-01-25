package com.homepaas.sls.mvp.view.account;

import com.homepaas.sls.domain.entity.AccountDetail;
import com.homepaas.sls.domain.entity.AccountInfo;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
public interface AccountBalanceView extends BaseView{

    void renderDetail(List<AccountDetail> details);

    void renderBalance(AccountInfo info);
}
