package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.domain.entity.OrderCancelReasonEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by JWC on 2017/5/31.
 */

public interface OrderCancelReasonsView extends BaseView {
    void renderCancelReasons(OrderCancelReasonEntity orderCancelReasonEntity);
    void orderCancelSuccess();
}
