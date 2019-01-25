package com.homepaas.sls.mvp.view;

import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2016/2/19 0019
 *
 * @author zhudongjie .
 */
public interface ShortDetailView extends BaseView {

    void bindWorker(WorkerCollectionEntity infoModel);

    void bindBusiness(BusinessInfoModel infoModel);

    void refreshWorker(WorkerCollectionEntity infoModel);

    void refreshBusiness(BusinessInfoModel infoModel);

    void callIfEnable(boolean enable);

    void likeAnim();

    void collectAnim();
}
