package com.homepaas.sls.mvp.view;

import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by CJJ on 2016/9/23.
 * 获取可用的服务数量列表，当前只是针对小时工
 * [1,1.5,2,2.5,3]类似的结构，小时工服务数量就是服务时间，可以不为整数
 */

public interface QtyView extends BaseView {
    void onQtyResult(List<String> datas);
}
