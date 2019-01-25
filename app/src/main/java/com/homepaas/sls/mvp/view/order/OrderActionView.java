package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/7/1.
 *
 */
public interface OrderActionView extends BaseView {

    void onOrderCancel(String msg,int position);
    void onOrderDelete(String msg,int position);
    void onOrderConfirm(String orderId,int position);
}
