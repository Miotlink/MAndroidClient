package com.homepaas.sls.mvp.presenter.servicedetail;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.interactor.GetBusinessTagsInfoInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerTagsInfoInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.servicedetail.TagView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/9/9.
 */
@ActivityScope
public class TagDetailPresenter implements Presenter {

    private TagView tagView;
    private GetBusinessTagsInfoInteractor getBusinessTagsInfoInteractor;
    private GetWorkerTagsInfoInteractor getWorkerTagsInfoInteractor;

    @Inject
    public TagDetailPresenter(GetBusinessTagsInfoInteractor getBusinessTagsInfoInteractor, GetWorkerTagsInfoInteractor getWorkerTagsInfoInteractor) {
        this.getBusinessTagsInfoInteractor = getBusinessTagsInfoInteractor;
        this.getWorkerTagsInfoInteractor = getWorkerTagsInfoInteractor;
    }

    public void setTagView(TagView tagView) {
        this.tagView = tagView;
    }

    public void getBusinessTagsInfo(final String businessId){
        tagView.showLoading();
        getBusinessTagsInfoInteractor.setBusinessId(businessId);
        getBusinessTagsInfoInteractor.execute(new OldBaseObserver<GetBusinessTagsInfo>(tagView) {
            @Override
            public void onNext(GetBusinessTagsInfo getBusinessTagsInfo) {
                tagView.render(getBusinessTagsInfo);
            }
        });
    }

    public void getWorkerTagsInfo(String workerId){
        tagView.showLoading();
        getWorkerTagsInfoInteractor.setWorkerId(workerId);
        getWorkerTagsInfoInteractor.execute(new OldBaseObserver<GetWorkerTagsInfo>(tagView) {
            @Override
            public void onNext(GetWorkerTagsInfo getWorkerTagsInfo) {
                tagView.render(getWorkerTagsInfo);
            }
        });

    }
    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getBusinessTagsInfoInteractor.unsubscribe();
        getWorkerTagsInfoInteractor.unsubscribe();
    }
}
