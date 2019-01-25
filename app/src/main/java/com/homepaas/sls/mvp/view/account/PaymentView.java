package com.homepaas.sls.mvp.view.account;

import com.homepaas.sls.mvp.model.PayItem;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * on 2016/6/22 0022
 *
 * @author zhudongjie
 */
public interface PaymentView extends BaseView {

    void render(List<PayItem> payItems);
}
