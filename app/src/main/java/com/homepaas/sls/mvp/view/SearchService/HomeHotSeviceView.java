package com.homepaas.sls.mvp.view.SearchService;

import com.homepaas.sls.domain.entity.HotServiceInfo;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by JWC on 2016/12/23.
 */

public interface HomeHotSeviceView extends BaseView {
    void renderHotService(List<HotServiceInfo> hotServiceInfos);
}
