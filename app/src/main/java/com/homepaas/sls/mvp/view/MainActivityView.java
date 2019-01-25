package com.homepaas.sls.mvp.view;

import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.entity.UpdateCheck;
import com.homepaas.sls.domain.entity.VerifyTokenBody;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2016/12/23.
 */

public interface MainActivityView extends BaseView {
    void renderBanner(List<BannerInfo> bannersInfo);
    void version(int count,String version);
    void renderFirstOrder(boolean isFirst, IsFirstOrderInfo.Service lastOrderServiceInfo);
    void send(VerifyTokenBody verifyTokenBody);
    void update(UpdateCheck updateCheck);
}
