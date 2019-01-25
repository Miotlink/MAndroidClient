package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.CommonCouponEntity;
import com.homepaas.sls.domain.entity.CommonCouponInfo;
import com.homepaas.sls.domain.entity.PlaceOrderEntry;
import com.homepaas.sls.domain.entity.QueryServicePriceEntry;
import com.homepaas.sls.domain.entity.ServiceTimeStartAtEntry;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by JWC on 2017/3/27.
 */

public interface QueryServicePriceView extends BaseView {
    void renderServices(QueryServicePriceEntry queryServicePriceEntry);
    void renderServiceTime(ServiceTimeStartAtEntry serviceTimeStartAtEntry);
    void renderActivity(ActivityNgInfoNew actionEntity);
    void renderPlaceOrder(PlaceOrderEntry placeOrderEntry);
    void renderCoupon(CommonCouponEntity commonCouponEntity);
    void renderCouponInfo(CommonCouponInfo commonCouponInfo);
    void netWrokError();
    void addressError();
}
