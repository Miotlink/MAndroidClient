package com.homepaas.sls.mvp.view.servicedetail;

import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/9/9.
 */
public interface TagView extends BaseView {

    void render(GetBusinessTagsInfo getBusinessTagsInfo);

    void render(GetWorkerTagsInfo getWorkerTagsInfo);
}
