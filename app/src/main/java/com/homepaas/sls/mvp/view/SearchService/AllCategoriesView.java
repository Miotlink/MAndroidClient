package com.homepaas.sls.mvp.view.SearchService;

import com.homepaas.sls.domain.entity.ServiceItemListEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by JWC on 2017/3/23.
 * 获取全部分类页面数据
 */

public interface AllCategoriesView extends BaseView {
    void render(ServiceItemListEntity serviceItemListEntity);
}
