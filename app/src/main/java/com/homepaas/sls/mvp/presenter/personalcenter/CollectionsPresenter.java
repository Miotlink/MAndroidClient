package com.homepaas.sls.mvp.presenter.personalcenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.BusinessCollectionEntity;
import com.homepaas.sls.domain.entity.WorkerCollectionEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.interactor.CollectBusinessInteractor;
import com.homepaas.sls.domain.interactor.CollectWorkerInteractor;
import com.homepaas.sls.domain.interactor.GetCollectedBusinessesInteractor;
import com.homepaas.sls.domain.interactor.GetCollectedWorkersInteractor;
import com.homepaas.sls.domain.interactor.LikeBusinessInteractor;
import com.homepaas.sls.domain.interactor.LikeWorkerInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.model.mapper.ServiceInfoModelMapper;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.personalcenter.CollectionsView;

import java.util.List;

import javax.inject.Inject;

/**
 * on 2016/1/23 0023
 *
 * @author zhudongjie .
 */
@ActivityScope
public class CollectionsPresenter implements Presenter {

    private static final String TAG = "CollectionsPresenter";
    @Inject
    GetCollectedBusinessesInteractor getCollectedBusinessesInteractor;

    @Inject
    GetCollectedWorkersInteractor getCollectedWorkersInteractor;

    @Inject
    LikeBusinessInteractor likeBusinessInteractor;

    @Inject
    LikeWorkerInteractor likeWorkerInteractor;

    @Inject
    CollectBusinessInteractor collectBusinessInteractor;

    @Inject
    CollectWorkerInteractor collectWorkerInteractor;

    @Inject
    ServiceInfoModelMapper serviceInfoModelMapper;

    private CollectionsView collectionsView;

    private List<WorkerCollectionEntity> collectedWorkers;

    private List<BusinessCollectionEntity> collectedBusinesses;

    @Inject
    public CollectionsPresenter() {

    }

    public void setCollectionsView(CollectionsView collectionsView) {
        this.collectionsView = collectionsView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getCollectedBusinessesInteractor.unsubscribe();
        getCollectedWorkersInteractor.unsubscribe();
        likeBusinessInteractor.unsubscribe();
        likeWorkerInteractor.unsubscribe();
        collectBusinessInteractor.unsubscribe();
        collectWorkerInteractor.unsubscribe();

    }

    public void getCollections() {
        collectionsView.showLoading();
        getCollectedWorkersInteractor.execute(new OldBaseObserver<List<com.homepaas.sls.domain.entity.WorkerCollectionEntity>>(collectionsView) {
            @Override
            public void onNext(List<com.homepaas.sls.domain.entity.WorkerCollectionEntity> workerInfoList) {
                collectedWorkers = workerInfoList;
                collectionsView.renderWorkers(workerInfoList);
            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
            }
        });

        getCollectedBusinessesInteractor.execute(new OldBaseObserver<List<BusinessCollectionEntity>>(collectionsView) {
            @Override
            public void onNext(List<BusinessCollectionEntity> businessInfoList) {
//                collectedBusinesses = serviceInfoModelMapper.transformBusinessList(businessInfoList);
                collectedBusinesses = businessInfoList;
                collectionsView.renderBusinesses(businessInfoList);
            }
        });

    }


    public void refreshWorker() {
        collectionsView.showLoading();
        getCollectedWorkersInteractor.execute(new OldBaseObserver<List<WorkerCollectionEntity>>(collectionsView) {
            @Override
            public void onNext(List<WorkerCollectionEntity> workerInfoList) {
                collectionsView.renderWorkers(workerInfoList);
            }
        });
    }


    public void refreshBusiness() {
        collectionsView.showLoading();
        getCollectedBusinessesInteractor.execute(new OldBaseObserver<List<BusinessCollectionEntity>>(collectionsView) {
            @Override
            public void onNext(List<BusinessCollectionEntity> businessInfoList) {
//                collectedBusinesses = serviceInfoModelMapper.transformBusinessList(businessInfoList);
                collectionsView.renderBusinesses(businessInfoList);
            }
        });
    }


    /**
     * 对工人点赞
     *
     * @param infoModel 要点赞的工人
     * @param like      true表示点赞，false表示取消点赞
     * @deprecated 此处不提供点赞功能
     */
    @Deprecated
    public void likeWorker(WorkerCollectionEntity infoModel, boolean like) {
        likeWorkerInteractor.setWorkerId(infoModel.getId());
        likeWorkerInteractor.setLike(like);
        likeWorkerInteractor.execute(new OldBaseObserver<Boolean>(collectionsView) {
            @Override
            public void onNext(Boolean s) {

            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
            }
        });
    }

    /**
     * 对商户点赞
     *
     * @param infoModel 要点赞的商户
     * @param like      true表示点赞，false表示取消点赞
     * @deprecated 此处不提供点赞功能
     */
    @Deprecated
    public void likeBusiness(BusinessCollectionEntity infoModel, boolean like) {
        collectionsView.showLoading();
        likeBusinessInteractor.setLike(like);
        likeBusinessInteractor.setBusinessId(infoModel.getId());
        likeBusinessInteractor.execute(new OldBaseObserver<Boolean>(collectionsView) {
            @Override
            public void onNext(Boolean sucess) {

            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
            }
        });
    }

    /**
     * 删除收藏工人
     *
     *
     */
    public void deleteWorker(String id) {
        collectionsView.showLoading();
        collectWorkerInteractor.setWorkerId(id);
        collectWorkerInteractor.setCollect(false);
        //collectedWorkers.remove(infoModel);
        collectWorkerInteractor.execute(new OldBaseObserver<Boolean>(collectionsView) {
            @Override
            public void onError(Throwable t) {
                if (t instanceof AuthException){
                    collectionsView.showLogin();
                }
                super.onError(t);
            }

            @Override
            public void onNext(Boolean b) {
                collectionsView.deleteCollectedWorker(collectedWorkers.size());
            }
        });
    }

    /**
     * 取消商户收藏
     *
     *
     */
    public void deleteBusiness(String id) {
        collectionsView.showLoading();
        collectBusinessInteractor.setCollect(false);
        collectBusinessInteractor.setBusinessId(id);
        //collectedBusinesses.remove(infoModel);
        collectBusinessInteractor.execute(new OldBaseObserver<Boolean>(collectionsView) {
            @Override
            public void showError(Throwable t) {

                if (t instanceof AuthException){
                    collectionsView.showLogin();
                }else
                super.showError(t);
            }

            @Override
            public void onNext(Boolean sucess) {
                collectionsView.deleteCollectedBusiness(collectedBusinesses.size());
            }

        });
    }

}
