package com.homepaas.sls.mvp.view;

import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by CJJ on 2016/9/26.
 *
 */

public interface BusinessPhoneView extends BaseView {

    void renderBusinessPhoneView(String phoneNumber,boolean callable);
}
