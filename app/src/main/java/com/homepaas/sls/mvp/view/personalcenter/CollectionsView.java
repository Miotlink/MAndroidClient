package com.homepaas.sls.mvp.view.personalcenter;

import com.homepaas.sls.domain.entity.BusinessCollectionEntity;
import com.homepaas.sls.domain.entity.WorkerCollectionEntity;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * on 2016/1/23 0023
 *
 * @author zhudongjie .
 */
public interface CollectionsView extends BaseView {

    void renderWorkers(List<WorkerCollectionEntity> collectedWorkers);

    void renderBusinesses(List<BusinessCollectionEntity> collectedBusinesses);

    void deleteCollectedWorker(int remainLength);

    void deleteCollectedBusiness(int remainLength);

}
