package com.homepaas.sls.mvp.view.newdetail;

import com.homepaas.sls.domain.entity.MerchantServicePriceListEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by JWC on 2017/2/13.
 */

public interface MerchantServicePriceListView extends BaseView {
    void render(MerchantServicePriceListEntity merchantServicePriceListEntity);
}
