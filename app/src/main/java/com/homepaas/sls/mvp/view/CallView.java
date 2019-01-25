package com.homepaas.sls.mvp.view;

import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by CJJ on 2016/9/26.
 */

public interface CallView extends BaseView {

     void callIfEnable(String phoneNumber);
}
