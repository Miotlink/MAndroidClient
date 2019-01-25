package com.homepaas.sls.mvp.presenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.FirstOpenAppInfo;
import com.homepaas.sls.domain.entity.NewFirstOpenAppInfo;
import com.homepaas.sls.domain.entity.PopupVer;
import com.homepaas.sls.domain.interactor.GetFirstOpenAppInfoInteractor;
import com.homepaas.sls.domain.interactor.PopuverInteractor;
import com.homepaas.sls.domain.repository.FirstOpenAppInfoRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.view.FirstOpenAppView;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/7/12.
 */
@ActivityScope
public class FirstOpenAppPresenter implements Presenter {

    @Inject
    public FirstOpenAppPresenter(){
    }

    @Inject
    GetFirstOpenAppInfoInteractor getFirstOpenAppInfoInteractor;
    @Inject
    PopuverInteractor popuverInteractor;
    FirstOpenAppView firstOpenAppView;
    @Inject
    FirstOpenAppInfoRepo firstOpenAppInfoRepo;

    public void setFirstOpenAppView(FirstOpenAppView firstOpenAppView) {
        this.firstOpenAppView = firstOpenAppView;
    }

    public void getFirstOpenAppInfo(){
        getFirstOpenAppInfoInteractor.execute(new OldBaseObserver<FirstOpenAppInfo>(firstOpenAppView) {
            @Override
            public void onNext(FirstOpenAppInfo firstOpenAppInfo) {
                firstOpenAppView.render(firstOpenAppInfo);
            }
        });

    }

    /**
     * 首页获取活动弹框信息
     */
    public void getFirstOpenAppInfoEX(){
        firstOpenAppInfoRepo.getFirstOpenAppInfo()
                .compose(RxUtil.<NewFirstOpenAppInfo>applySchedulers())
                .subscribe(new Subscriber<NewFirstOpenAppInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(NewFirstOpenAppInfo newFirstOpenAppInfo) {
                        firstOpenAppView.renderFirstOpenAppInfo(newFirstOpenAppInfo);
                    }
                });
    }

    public void getPopuver(){
        popuverInteractor.execute(new OldBaseObserver<PopupVer>(firstOpenAppView) {
            @Override
            public void onNext(PopupVer popupVer) {
                firstOpenAppView.popuver(popupVer.getPopuValue());
            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
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
        getFirstOpenAppInfoInteractor.unsubscribe();
        popuverInteractor.unsubscribe();
    }
}
