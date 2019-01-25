package com.homepaas.sls.mvp.view.city;

import com.homepaas.sls.domain.entity.CityListEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2017/7/21.
 */

public interface CityView extends BaseView {
    void render(CityListEntity cityListEntity);
}
