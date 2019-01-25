package com.homepaas.sls.mvp.view.coupon;

import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/7/9.
 */
public interface GetShareCouponView extends BaseView {
    void share(ShareInfo shareInfo);
}
