package com.homepaas.sls.mvp.view.SearchService;

import com.homepaas.sls.domain.entity.ServiceItemListEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by JWC on 2017/3/27.
 * 3.3.0搜索页面接口
 */

public interface CommonSearchServiceView extends BaseView {
    void renderHotService(ServiceItemListEntity serviceItemListEntity); //热门搜索结果
    void renderResultService(ServiceItemListEntity serviceItemListEntity);//edittext输入后得到的结果

}
