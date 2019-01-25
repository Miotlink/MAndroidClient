package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.domain.entity.ExpressDetailOutputEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by JWC on 2017/7/6.
 */

public interface ErrandServiceDetailView extends BaseView {
    void renderDetail(ExpressDetailOutputEntity expressDetailOutputEntity);
}
