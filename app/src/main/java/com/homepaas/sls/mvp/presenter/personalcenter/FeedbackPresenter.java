package com.homepaas.sls.mvp.presenter.personalcenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.interactor.FeedbackInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.personalcenter.FeedbackView;

import javax.inject.Inject;

/**
 * on 2016/1/23 0023
 *
 * @author zhudongjie .
 */
@ActivityScope
public class FeedbackPresenter implements Presenter {

    FeedbackInteractor feedbackInteractor;

    private FeedbackView feedbackView;

    @Inject
    public FeedbackPresenter(FeedbackInteractor feedbackInteractor) {
        this.feedbackInteractor = feedbackInteractor;
    }

    public void setFeedbackView(FeedbackView feedbackView) {
        this.feedbackView = feedbackView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        feedbackInteractor.unsubscribe();
    }

    public void submitFeedback(String content) {
        feedbackInteractor.setContent(content);
        feedbackInteractor.execute(new OldBaseObserver<String>(feedbackView) {
            @Override
            public void showError(Throwable t) {
                super.showError(t);
                if (t instanceof AuthException){
                    feedbackView.showLogin();
                }
            }

            @Override
            public void onNext(String s) {

                feedbackView.afterFeedbackSubmitted();
            }
        });
    }
}
