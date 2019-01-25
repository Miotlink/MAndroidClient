package com.homepaas.sls.mvp.view;

import com.homepaas.sls.mvp.model.PersonalInfoModel;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
public interface PersonalCenterView extends BaseView {

    void render(PersonalInfoModel infoModel);

    void noticeCount(int count);

    void showQrCode(PersonalInfoModel infoModel);
}
