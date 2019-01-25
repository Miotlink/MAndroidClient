package com.homepaas.sls.mvp.presenter;

import android.util.Log;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.AddShareRecordInteractor;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * on 2016/7/13 0013
 *
 * @author zhudongjie
 */
@ActivityScope
public class MyQrCodePresenter implements Presenter {

    private static final String TAG = "MyQrCodePresenter";

    private AddShareRecordInteractor interactor;

    @Inject
    public MyQrCodePresenter(AddShareRecordInteractor interactor) {
        this.interactor = interactor;
    }

    public void uploadRecord() {
        interactor.execute(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "uploadRecord: ", e);
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "uploadRecord: " + s);
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

    }
}
