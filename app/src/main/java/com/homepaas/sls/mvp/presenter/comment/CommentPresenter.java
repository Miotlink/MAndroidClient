package com.homepaas.sls.mvp.presenter.comment;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.interactor.GetMyEvaluationsInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.comment.CommentView;

import java.util.List;

import javax.inject.Inject;

/**
 * on 2016/4/18 0018
 *
 * @author zhudongjie .
 */
@ActivityScope
public class CommentPresenter implements Presenter{

    private GetMyEvaluationsInteractor evaluationsInteractor;

    private CommentView commentView;

    private static int DEFAULT_SIZE = 10;

    private int pageIndex = 1;

    @Inject
    public CommentPresenter(GetMyEvaluationsInteractor evaluationsInteractor) {
        this.evaluationsInteractor = evaluationsInteractor;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        evaluationsInteractor.unsubscribe();
    }

    public void getEvaluations(){
        pageIndex = 1;
        evaluationsInteractor.setRange(pageIndex,DEFAULT_SIZE);
        evaluationsInteractor.execute(new OldBaseObserver<List<Evaluation>>(commentView) {
            @Override
            public void onNext(List<Evaluation> evaluations) {
                commentView.render(evaluations);
            }
        });
    }

    public void loadMore(){
        evaluationsInteractor.setRange(++pageIndex,DEFAULT_SIZE);
        evaluationsInteractor.execute(new OldBaseObserver<List<Evaluation>>(commentView) {
            @Override
            public void onNext(List<Evaluation> evaluations) {
                commentView.renderMore(evaluations);
            }
        });
    }

    public void setCommentView(CommentView commentView) {
        this.commentView = commentView;
    }
}
