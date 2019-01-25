package com.homepaas.sls.mvp.view.order;

import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public interface GetOrderView extends BaseView {

    void render(List<OrderEntity> orderList);

    void renderMore(List<OrderEntity> moreList);

    void showLoading();

    void hideLoading();
}
