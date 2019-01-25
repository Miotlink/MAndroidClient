package com.homepaas.sls.ui.footcard;

import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/12/15.
 */

public interface FootCardView extends BaseView {
    void call(IService service,boolean enable);
}
