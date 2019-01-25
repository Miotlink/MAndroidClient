package com.homepaas.sls.mvp.view.account;

import com.homepaas.sls.domain.entity.RechargeListExEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2016/6/22 0022
 *
 * @author zhudongjie
 */
public interface RechargeListView extends BaseView {

    void render(RechargeListExEntity recharges);
}
