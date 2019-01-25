package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.domain.entity.BusinessServicePricesEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by JWC on 2017/1/9.
 */

public interface BusinessServicePriceView extends BaseView {
    void onBusinessPriceResult(BusinessServicePricesEntity businessServicePricesEntity);
}
