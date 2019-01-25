package com.homepaas.sls.mvp.presenter.comment;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.AddEvaluationInteractor;
import com.homepaas.sls.domain.interactor.EvaluateOrderInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.comment.AddCommentView;

import java.util.List;

import javax.inject.Inject;

/**
 * on 2016/4/18 0018
 *
 * @author zhudongjie .
 */
@ActivityScope
public class AddCommentPresenter implements Presenter {

    private static final String TAG = "AddCommentPresenter";
    private AddCommentView view;

    private AddEvaluationInteractor interactor;

    private EvaluateOrderInteractor orderInteractor;

    @Inject
    public AddCommentPresenter(AddEvaluationInteractor interactor, EvaluateOrderInteractor orderInteractor) {
        this.interactor = interactor;
        this.orderInteractor = orderInteractor;
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
        orderInteractor.unsubscribe();
    }


    public void submit(String score, String content, List<String> pictures) {
        view.showLoading();
        interactor.setScore(score);
        interactor.setContent(content);
        interactor.setPictures(pictures);
        interactor.execute(new OldBaseObserver<String>(view) {
            @Override
            public void onNext(String s) {
                view.onSubmit(s);
            }
        });
    }

    public void submitOrder(String score, String content, List<String> pictures) {
        view.showLoading();
        orderInteractor.setScore(score);
        orderInteractor.setContent(content);
        orderInteractor.setPictures(pictures);
        orderInteractor.execute(new OldBaseObserver<String>(view) {
            @Override
            public void onNext(String s) {
                view.onSubmit(s);
            }
        });
    }

    public void setView(AddCommentView view) {
        this.view = view;
    }
}
