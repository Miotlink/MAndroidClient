package com.homepaas.sls.mvp.view;

import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2016/1/16.
 */

public interface ServiceListView extends BaseView {
    void render(List<ServiceTypeEx> serviceTypeExList);
}
