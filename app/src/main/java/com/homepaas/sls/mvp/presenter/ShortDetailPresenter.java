package com.homepaas.sls.mvp.presenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.interactor.CheckBusinessCallableInteractor;
import com.homepaas.sls.domain.interactor.CheckWorkerCallableInteractor;
import com.homepaas.sls.domain.interactor.CollectBusinessInteractor;
import com.homepaas.sls.domain.interactor.CollectWorkerInteractor;
import com.homepaas.sls.domain.interactor.LikeBusinessInteractor;
import com.homepaas.sls.domain.interactor.LikeWorkerInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.WorkerBussinesModel;
import com.homepaas.sls.mvp.model.mapper.ServiceInfoModelMapper;
import com.homepaas.sls.mvp.view.ShortDetailView;

import javax.inject.Inject;

/**
 * on 2016/2/19 0019
 *
 * @author zhudongjie .
 * 首页改版后不再使用
 */
@Deprecated
@ActivityScope
public class ShortDetailPresenter {

    @Inject
    CollectBusinessInteractor collectBusinessInteractor;

    @Inject
    LikeBusinessInteractor likeBusinessInteractor;

    @Inject
    LikeWorkerInteractor likeWorkerInteractor;

    @Inject
    CollectWorkerInteractor collectWorkerInteractor;

    private CheckWorkerCallableInteractor callableWorkerInteractor;

    private CheckBusinessCallableInteractor callableBusinessInteractor;
    @Inject
    ServiceInfoModelMapper mapper;

    private ShortDetailView detailView;

    private IServiceInfo serviceInfo;



    @Inject
    public ShortDetailPresenter(CheckWorkerCallableInteractor callableWorkerInteractor, CheckBusinessCallableInteractor callableBusinessInteractor) {
        this.callableWorkerInteractor = callableWorkerInteractor;
        this.callableBusinessInteractor = callableBusinessInteractor;
    }

    public void setDetailView(ShortDetailView detailView) {
        this.detailView = detailView;
    }

    public void call(WorkerBussinesModel workerBussinesModel){
        if (workerBussinesModel.getType() == 0){
            callableWorkerInteractor.setPhoneNumber(workerBussinesModel.getPhoneNumber());
            callableWorkerInteractor.setWorkerId(workerBussinesModel.getId());
            callableWorkerInteractor.execute(new OldBaseObserver<Boolean>(detailView) {
                @Override
                public void onNext(Boolean aBoolean) {
                    detailView.callIfEnable(aBoolean);
                }
            });
        } else {
            callableBusinessInteractor.setBusinessId(workerBussinesModel.getId());
            callableBusinessInteractor.setPhoneNumber(workerBussinesModel.getPhoneNumber());
            callableBusinessInteractor.execute(new OldBaseObserver<Boolean>(detailView) {
                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    detailView.callIfEnable(false);
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    detailView.callIfEnable(aBoolean);
                }
            });
        }

    }

    public void attemptCall(){
        if (serviceInfo.type() ==IServiceInfo.TYPE_WORKER){
            callableWorkerInteractor.setPhoneNumber(serviceInfo.getPhoneNumber());
            callableWorkerInteractor.setWorkerId(serviceInfo.getId());
            callableWorkerInteractor.execute(new OldBaseObserver<Boolean>(detailView) {
                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    detailView.callIfEnable(false);
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    detailView.callIfEnable(aBoolean);
                }
            });
        }else {
            callableBusinessInteractor.setBusinessId(serviceInfo.getId());
            callableBusinessInteractor.setPhoneNumber(serviceInfo.getPhoneNumber());
            callableBusinessInteractor.execute(new OldBaseObserver<Boolean>(detailView) {
                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    detailView.callIfEnable(false);
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    detailView.callIfEnable(aBoolean);
                }
            });
        }
    }



    public void collectBusiness(final boolean collect) {
        detailView.showLoading();
        collectBusinessInteractor.setBusinessId(serviceInfo.getId());
        collectBusinessInteractor.setCollect(collect);
        collectBusinessInteractor.execute(new OldBaseObserver<Boolean>(detailView) {

            @Override
            public void showError(Throwable t) {
                if (t instanceof AuthException){
                    detailView.showLogin();
                }else {
                    super.showError(t);
                }
            }

            @Override
            public void onNext(Boolean sucess) {
//                detailView.refreshBusiness(mapper.transformBusiness(businessInfo));
                if (collect){
                    detailView.collectAnim();
                }
            }
        });
    }

    public void collectWorker( final boolean collect) {
        detailView.showLoading();
        collectWorkerInteractor.setCollect(collect);
        collectWorkerInteractor.setWorkerId(serviceInfo.getId());
        collectWorkerInteractor.execute(new OldBaseObserver<Boolean>(detailView) {
            @Override
            public void showError(Throwable t) {
                if (t instanceof AuthException){
                    detailView.showLogin();
                }else {
                    super.showError(t);
                }
            }

            @Override
            public void onNext(Boolean b) {
//                detailView.refreshWorker(mapper.transformWorker(workerInfo));
                if (collect){
                    detailView.collectAnim();
                }
            }
        });
    }



    public void likeBusiness( final boolean like) {
        detailView.showLoading();
        likeBusinessInteractor.setBusinessId(serviceInfo.getId());
        likeBusinessInteractor.setLike(like);
        likeBusinessInteractor.execute(new OldBaseObserver<Boolean>(detailView) {
            @Override
            public void showError(Throwable t) {
                if (t instanceof AuthException){
                    detailView.showLogin();
                }else {
                    super.showError(t);
                }
            }
            @Override
            public void onNext(Boolean sucess) {
//                detailView.refreshBusiness(mapper.transformBusiness(businessInfo));
                if (like){
                    detailView.likeAnim();
                }
            }
        });
    }

    public void likeWorker( final boolean like) {
        detailView.showLoading();
        likeWorkerInteractor.setLike(like);
        likeWorkerInteractor.setWorkerId(serviceInfo.getId());
        likeWorkerInteractor.execute(new OldBaseObserver<Boolean>(detailView) {

            @Override
            public void showError(Throwable t) {
                if (t instanceof AuthException){
                    detailView.showLogin();
                }else {
                    super.showError(t);
                }
            }

            @Override
            public void onNext(Boolean b) {
//                detailView.refreshWorker(mapper.transformWorker(workerInfo));
                if (like){
                    detailView.likeAnim();
                }
            }
        });
    }

    public void setServiceInfo(IServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public IServiceInfo getServiceInfo() {
        return serviceInfo;
    }
}
