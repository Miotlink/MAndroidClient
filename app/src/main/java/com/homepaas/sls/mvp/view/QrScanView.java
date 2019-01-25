package com.homepaas.sls.mvp.view;

import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2016/7/12 0012
 *
 * @author zhudongjie
 */
public interface QrScanView extends BaseView {

    void onResult(PushInfo pushInfo);
}
