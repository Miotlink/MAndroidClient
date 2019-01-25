package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface OrderPlaceView extends BaseView {

    void onOrderCreate(String orderCode,String serviceTypeId);

}
