package com.homepaas.sls.mvp.presenter.order;

import com.homepaas.sls.domain.entity.StarLevelInfo;
import com.homepaas.sls.domain.repository.GetStarLevelRepo;
import com.homepaas.sls.domain.repository.SubmitEvaluationRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.order.StarLevelView;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/7/21.
 */

public class EvaluationOrderPresenter implements Presenter {
    private StarLevelView starLevelView;

    public void setStarLevelView(StarLevelView starLevelView) {
        this.starLevelView = starLevelView;
    }

    @Inject
    public EvaluationOrderPresenter() {
    }

    @Inject
    GetStarLevelRepo getStarLevelRepo;

    @Inject
    SubmitEvaluationRepo submitEvaluationRepo;

    /**
     * 获取星级评价信息
     * @param serviceTypeId
     */
    public void getStarLevel(String serviceTypeId) {
        getStarLevelRepo.GetStarLevel(serviceTypeId)
               .compose(RxUtil.<StarLevelInfo>applySchedulers())
                .subscribe(new OldBaseObserver<StarLevelInfo>(starLevelView) {
                    @Override
                    public void onNext(StarLevelInfo starInfo) {
                        starLevelView.renderStarLevelInfo(starInfo);
                    }
                });
    }

    /**
     * 提交评价
     * @param orderCode
     * @param starLevel
     * @param content
     * @param evaluatedTag
     */
    public void SubmitEvaluation(String orderCode, String starLevel, String content, String evaluatedTag){
        starLevelView.showLoading();
        submitEvaluationRepo.submitEvaluation(orderCode,starLevel,content,evaluatedTag)
                .compose(RxUtil.<String>applySchedulers())
                .subscribe(new OldBaseObserver<String>(starLevelView) {
                    @Override
                    public void onNext(String s) {
                        starLevelView.SubmitEvaluationSuccess();
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
