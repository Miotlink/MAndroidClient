package com.homepaas.sls.mvp.view;

import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by CJJ on 2016/9/14.
 *
 */

public interface ServiceView extends BaseView {

    void onPriceResult(PriceEntity price);
    void onScheduleResult(List<ServiceScheduleEntity> schedule);
}
