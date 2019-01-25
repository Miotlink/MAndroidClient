package com.homepaas.sls.mvp.view.newdetail;

import com.homepaas.sls.domain.entity.WorkerServicePriceListEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by JWC on 2017/2/13.
 */

public interface WorkerServicePriceListView extends BaseView {
    void render(WorkerServicePriceListEntity workerServicePriceListEntity);
}
