package com.homepaas.sls.mvp.view;

import com.homepaas.sls.domain.entity.SystemConfigEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by CJJ on 2016/9/20.
 */

public interface GetSystemConfigView extends BaseView {
    void render(SystemConfigEntity configEntity);
}
