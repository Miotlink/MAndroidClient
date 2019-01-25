package com.homepaas.sls.mvp.view.servicedetail;

import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.entity.ServiceContent;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * on 2016/1/18 0018
 *
 * @author zhudongjie .
 */
public interface BusinessView extends BaseView {

    void render(GetBusinessTagsInfo getBusinessTagsInfo);
    void render(BusinessInfoModel businessInfoModel);

    void collectBusiness(BusinessInfoModel businessInfoModel);

    void likeBusiness(BusinessInfoModel businessInfoModel);

    void callIfEnabled(boolean enabled, String phone);

    void renderEvaluations(List<Evaluation> evaluations);

    void addMoreEvaluations(List<Evaluation> evaluations);

    void renderServiceContents(List<ServiceContent> serviceContents);

    void share(ShareInfo shareInfo);

    void renderBusinessAvatars(AvatarsEntity avatarsEntity);
}
