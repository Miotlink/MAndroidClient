package com.homepaas.sls.mvp.view;

import com.homepaas.sls.data.entity.HomeOrderStatusEntity;
import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.entity.RecommendServiceEntity;
import com.homepaas.sls.domain.entity.ShortCutEntity;
import com.homepaas.sls.domain.entity.SuperDiscountEntity;
import com.homepaas.sls.mvp.model.HomeListData;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2017/3/22.
 */

public interface HomePageView extends BaseView {
    void renderShortCut(ShortCutEntity shortCutEntity);

    void renderRecommend(RecommendServiceEntity recommendServiceEntity);

    void renderFirstOrder(boolean isFirst, IsFirstOrderInfo.Service lastOrderServiceInfo);

    void renderSuperDiscount(SuperDiscountEntity superDiscountEntity);

    void setHomeListData(HomeListData homeListData);

    void getHomeListDataError();//获取首页信息失败，显示失败提示布局

    void renderSuperDiscountError();//获取超级优惠信息失败，显示缓存
    void initOrderStatus(HomeOrderStatusEntity homeOrderStatusEntity);

}
