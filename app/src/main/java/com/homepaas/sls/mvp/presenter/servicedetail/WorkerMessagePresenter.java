package com.homepaas.sls.mvp.presenter.servicedetail;

import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.WorkerInfo;
import com.homepaas.sls.domain.interactor.AvatarsInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerInfoInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerTagsInfoInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.mvp.model.mapper.ServiceInfoModelMapper;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.servicedetail.WorkerMessageView;

import java.util.List;

import javax.inject.Inject;

/**
 * on 2016/3/2 0002
 *
 * @author zhudongjie .
 */
public class WorkerMessagePresenter implements Presenter{
    private ServiceInfoModelMapper serviceInfoModelMapper;

    private GetWorkerInfoInteractor getWorkerInfoInteractor;

    private GetWorkerTagsInfoInteractor getWorkerTagsInfoInteractor;

    private WorkerMessageView workerMessageView;

    private WorkerCollectionEntity infoModel;
    private AvatarsInteractor avatarsInteractor;

    @Inject
    public WorkerMessagePresenter(ServiceInfoModelMapper serviceInfoModelMapper, GetWorkerInfoInteractor getWorkerInfoInteractor,
                                  GetWorkerTagsInfoInteractor getWorkerTagsInfoInteractor,AvatarsInteractor avatarsInteractor) {
        this.serviceInfoModelMapper = serviceInfoModelMapper;
        this.getWorkerInfoInteractor = getWorkerInfoInteractor;
        this.getWorkerTagsInfoInteractor = getWorkerTagsInfoInteractor;
        this.avatarsInteractor = avatarsInteractor;
    }

    public void setView(WorkerMessageView view){
        workerMessageView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getWorkerInfoInteractor.unsubscribe();
        getWorkerTagsInfoInteractor.unsubscribe();
        avatarsInteractor.unsubscribe();
    }
    public void getWorkerAvatars(String id){
        avatarsInteractor.setType("1");
        avatarsInteractor.setId(id);
        avatarsInteractor.execute(new OldBaseObserver<AvatarsEntity>(workerMessageView) {
            @Override
            public void onNext(AvatarsEntity avatarsEntity) {
                workerMessageView.renderAvatars(avatarsEntity);
            }
        });
    }
    public void getWorkerTagsInfo(String workerId){
        getWorkerTagsInfoInteractor.setWorkerId(workerId);
        getWorkerTagsInfoInteractor.execute(new OldBaseObserver<GetWorkerTagsInfo>(workerMessageView) {
            @Override
            public void onNext(GetWorkerTagsInfo getWorkerTagsInfo) {
                workerMessageView.render(getWorkerTagsInfo);
            }
        });

    }
    public void getWorkerInfo(String workerId){
        workerMessageView.showLoading();
        getWorkerInfoInteractor.setWorkerId(workerId);
        getWorkerInfoInteractor.execute(new OldBaseObserver<WorkerInfo>(workerMessageView) {
            @Override
            public void onNext(WorkerInfo workerInfo) {
                infoModel = serviceInfoModelMapper.transformWorker(workerInfo);
                workerMessageView.render(infoModel);
            }

        });
    }

    public List<IServiceInfo.SystemCertification> getSystemCertificationList() {
        return infoModel.getSystemCertifications();
    }
}
