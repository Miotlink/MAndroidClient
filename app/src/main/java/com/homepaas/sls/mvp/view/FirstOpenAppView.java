package com.homepaas.sls.mvp.view;

import com.homepaas.sls.domain.entity.FirstOpenAppInfo;
import com.homepaas.sls.domain.entity.NewFirstOpenAppInfo;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface FirstOpenAppView extends BaseView {

    void render(FirstOpenAppInfo firstOpenAppInfo);
    void popuver(String version);
    void renderFirstOpenAppInfo(NewFirstOpenAppInfo newFirstOpenAppInfo);
}
