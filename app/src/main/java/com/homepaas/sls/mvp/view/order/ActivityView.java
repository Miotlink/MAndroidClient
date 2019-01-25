package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by CJJ on 2016/9/19.
 */

public interface ActivityView extends BaseView {

    void renderActivity(ActivityNgInfoNew actionEntity);
}
