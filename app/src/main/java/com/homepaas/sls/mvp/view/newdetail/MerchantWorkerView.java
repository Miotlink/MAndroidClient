package com.homepaas.sls.mvp.view.newdetail;

import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.mvp.model.mapper.TagsInfoMapper;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

public interface MerchantWorkerView extends BaseView {
    void renderTags(List<TagsInfoMapper.TagsInfo> tagsInfos);
    void renderBusinessInfo(BusinessInfoModel businessInfoModel);

    void collectBusiness(Boolean sucess,boolean status);

    void likeBusiness(Boolean sucess,boolean status);

    void renderWorkerInfo(WorkerCollectionEntity infoModel);

    void collectWorker(Boolean sucess,boolean status);

    void likeWorker(Boolean sucess,boolean status);

    void callIfEnabled(boolean enabled,String phone);

    void share(ShareInfo shareInfo);
    void renderAvatars(AvatarsEntity avatarsEntity);
}

