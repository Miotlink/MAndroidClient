package com.homepaas.sls.mvp.presenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.domain.interactor.UploadQrCodeInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.view.QrScanView;

import javax.inject.Inject;

/**
 * on 2016/7/12 0012
 *
 * @author zhudongjie
 */
@ActivityScope
public class QrScanPresenter implements Presenter {

    private QrScanView view;

    private UploadQrCodeInteractor interactor;

    @Inject
    public QrScanPresenter(UploadQrCodeInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        interactor.unsubscribe();
    }

    public void setView(QrScanView view) {
        this.view = view;
    }



    public void uploadUrl(String url){
        interactor.setUrl(url);
        interactor.execute(new OldBaseObserver<PushInfo>(view) {
            @Override
            public void onNext(PushInfo pushInfo) {
                view.onResult(pushInfo);
            }
        });
    }
}
