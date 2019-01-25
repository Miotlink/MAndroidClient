package com.homepaas.sls.mvp.view;

import com.homepaas.sls.domain.entity.ServiceItem;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by Sherily on 2017/3/24.
 */

public interface CategoryView extends BaseView {
    void render(List<ServiceItem> serviceItems);
    void renderTitle(String title);
}
