package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.domain.entity.StarLevelInfo;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by JWC on 2017/7/21.
 */

public interface StarLevelView extends BaseView {
    void renderStarLevelInfo(StarLevelInfo starInfo);
    void SubmitEvaluationSuccess();

}
