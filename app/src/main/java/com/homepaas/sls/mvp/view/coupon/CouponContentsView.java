package com.homepaas.sls.mvp.view.coupon;

import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface CouponContentsView extends BaseView {

    void render(List<CouponContents> couponContentses);
    void renderCount(int count);

}
