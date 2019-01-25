package com.homepaas.sls.mvp.presenter.push;

import android.util.Log;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.UploadClientIdInteractor;
import com.homepaas.sls.mvp.presenter.Presenter;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by CJJ on 2016/7/29.
 */
@ActivityScope
public class PushPresenter implements Presenter {


    @Inject
    public PushPresenter() {
    }

    private static final String TAG = "PushPresenter";
    @Inject
    UploadClientIdInteractor uploadClientIdInteractor;

    public void uploadClientId(String cid){
        Log.i(TAG, "uploadClientId: ");
        uploadClientIdInteractor.setClientId(cid);
        uploadClientIdInteractor.execute(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                if (BuildConfig.DEBUG)
                Log.i(TAG, "上传推送ClientId成功:---- ");
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
        uploadClientIdInteractor.unsubscribe();
    }
}
