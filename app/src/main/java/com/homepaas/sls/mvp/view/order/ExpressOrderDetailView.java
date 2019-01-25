package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.KdTrackInfoResponse;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by JWC on 2017/6/5.
 */

public interface ExpressOrderDetailView extends BaseView {
    void renderOrderDetail(DetailOrderEntity detailOrderEntity);
    void renderKdTrackInfo(KdTrackInfoResponse kdTrackInfoResponse);
}
