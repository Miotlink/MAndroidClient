package com.homepaas.sls.mvp.view.servicedetail;

import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * on 2016/1/19 0019
 *
 * @author zhudongjie .
 */
public interface WorkerView extends BaseView {

    void render(WorkerCollectionEntity infoModel);

    void render(GetWorkerTagsInfo getWorkerTagsInfo);

    void collectWorker(WorkerCollectionEntity infoModel);

    void likeWorker(WorkerCollectionEntity infoModel);

    void callIfEnabled(boolean enabled,String phone);

    void renderEvaluations(List<Evaluation> evaluations);

    void addMoreEvaluations(List<Evaluation> evaluations);

    void share(ShareInfo shareInfo);
    void renderAvatars(AvatarsEntity avatarsEntity);
}
