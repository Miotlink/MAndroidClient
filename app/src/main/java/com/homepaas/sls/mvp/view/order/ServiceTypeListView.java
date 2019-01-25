package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.domain.entity.BusinessServiceTypeInfo;
import com.homepaas.sls.domain.entity.WorkerServiceTypeInfo;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/4/27.
 */
public interface ServiceTypeListView extends BaseView {

    void renderWorker(WorkerServiceTypeInfo serviceTypeListInfo);

    void renderBusiness(BusinessServiceTypeInfo serviceTypeListInfo);

}
