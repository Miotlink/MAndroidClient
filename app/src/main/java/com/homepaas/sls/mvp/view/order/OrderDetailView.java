package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.TrackOrderInfo;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by CMJ on 2016/6/23.
 */

public interface OrderDetailView extends BaseView {

    void render(DetailOrderEntity order,long requestTime);
    void renderTrackOrderStatus(TrackOrderInfo orderInfo);
}
