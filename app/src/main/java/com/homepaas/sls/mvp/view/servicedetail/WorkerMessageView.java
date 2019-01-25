package com.homepaas.sls.mvp.view.servicedetail;

import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * on 2016/3/2 0002
 *
 * @author zhudongjie .
 */
public interface WorkerMessageView extends BaseView {

    void render(WorkerCollectionEntity infoModel);

    void render(GetWorkerTagsInfo getWorkerTagsInfo);

    void renderAvatars(AvatarsEntity avatarsEntity);
}
