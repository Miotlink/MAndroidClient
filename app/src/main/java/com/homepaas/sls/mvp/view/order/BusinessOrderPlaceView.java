package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by CJJ on 2016/6/20.
 */

public interface BusinessOrderPlaceView extends BaseView {
    void render(BusinessInfoModel businessInfoModel);
}
