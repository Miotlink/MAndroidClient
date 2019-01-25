package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by CJJ on 2016/6/20.
 */

public interface WorkerOrderPlaceView extends BaseView {

    void render(WorkerCollectionEntity workerInfoModel);
}
