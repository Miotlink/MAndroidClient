package com.homepaas.sls.mvp.view.newdetail;

import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

public interface MerchantWorkerEvaluationsView extends BaseView {

    void render(List<Evaluation> evaluations);
    void renderMore(List<Evaluation> evaluations);

}
