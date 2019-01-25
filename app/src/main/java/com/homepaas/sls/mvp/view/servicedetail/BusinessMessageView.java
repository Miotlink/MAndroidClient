package com.homepaas.sls.mvp.view.servicedetail;

import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2016/1/20 0020
 *
 * @author zhudongjie .
 */
public interface BusinessMessageView extends BaseView {

    void render(BusinessInfoModel businessInfoModel);

    void render(GetBusinessTagsInfo getBusinessTagsInfo);

    void renderAvatars(AvatarsEntity avatarsEntity);
}
