package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/7/4.
 */
public interface OrderCancelView extends BaseView {

    void onOrderCancel(String errMsg);
}
