package com.homepaas.sls.mvp.view.personalcenter;

import com.homepaas.sls.mvp.model.PersonalInfoModel;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * Created by Administrator on 2015/12/23.
 *
 */
public interface PersonalInfoView extends BaseView {

    void render(PersonalInfoModel model);

    void showQrCodeView(PersonalInfoModel model);

    void modifyPhoto(String url);

    void logout();
}
