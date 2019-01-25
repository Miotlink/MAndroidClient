package com.homepaas.sls.mvp.view.share;

import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2016/7/12.
 */
public interface ShareView  extends BaseView {

    void onShareResult(ShareInfo shareInfo);
}
