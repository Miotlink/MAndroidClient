package com.homepaas.sls.mvp.view;

import com.homepaas.sls.domain.entity.OrderPayWXSign;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/6/30.
 */
public interface GetWXSignView extends BaseView {

    void render(OrderPayWXSign wxSign);
}
