package com.homepaas.sls.mvp.view.SearchService;

import com.homepaas.sls.domain.entity.HotServiceInfo;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by JWC on 2016/12/20.
 */

public interface SearchServiceView extends BaseView {
    void render(ServiceSearchInfo serviceSearchInfo);
    void renderHotService(List<HotServiceInfo> hotServiceInfos);
    void renderRetrurnBack(ServiceSearchInfo serviceSearchInfo);
    void renderNoResult();
}
