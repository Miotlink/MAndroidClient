package com.homepaas.sls.mvp.presenter.newdetail;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.interactor.GetBusinessEvaluationsInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerEvaluationsInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.newdetail.MerchantWorkerEvaluationsView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/2/8.
 */
@ActivityScope
public class MerchantWorkerEvaluationsPresenter implements Presenter {

    @Inject
    GetWorkerEvaluationsInteractor getWorkerEvaluationsInteractor;
    @Inject
    GetBusinessEvaluationsInteractor getBusinessEvaluationsInteractor;
    private MerchantWorkerEvaluationsView merchantWorkerEvaluationsView;

    @Inject
    public MerchantWorkerEvaluationsPresenter() {
    }

    public void setMerchantWorkerEvaluationsView(MerchantWorkerEvaluationsView merchantWorkerEvaluationsView) {
        this.merchantWorkerEvaluationsView = merchantWorkerEvaluationsView;
    }
    private int pageBusinessIndex = 1;

    private static final int BUSINESS_DEFAULT_SIZE = 10;
    /**
     * 获取商户评价列表
     */
    public void getBusinessEvaluations(String businessId) {
        pageBusinessIndex = 1;
//        merchantWorkerEvaluationsView.showLoading();
        getBusinessEvaluationsInteractor.setBusinessId(businessId);
        getBusinessEvaluationsInteractor.setRange(pageBusinessIndex, BUSINESS_DEFAULT_SIZE);
        getBusinessEvaluationsInteractor.execute(new OldBaseObserver<List<Evaluation>>(merchantWorkerEvaluationsView) {
            @Override
            public void onNext(List<Evaluation> evaluations) {
                merchantWorkerEvaluationsView.hideLoading();
                merchantWorkerEvaluationsView.render(evaluations);
            }
        });
    }

    public void addBusinessMoreEvaluations() {
        merchantWorkerEvaluationsView.showLoading();
        getBusinessEvaluationsInteractor.setRange(++pageBusinessIndex, BUSINESS_DEFAULT_SIZE);
        getBusinessEvaluationsInteractor.execute(new OldBaseObserver<List<Evaluation>>(merchantWorkerEvaluationsView) {
            @Override
            public void onNext(List<Evaluation> evaluations) {
                merchantWorkerEvaluationsView.hideLoading();
                merchantWorkerEvaluationsView.renderMore(evaluations);
            }
        });
    }
    private int pageIndex = 1;

    private static final int DEFAULT_SIZE = 10;

    /**
     * 获取工人评价列表
     */
    public void getWorkerEvaluations(String workerId) {
        pageIndex = 1;
        getWorkerEvaluationsInteractor.setWorkerId(workerId);
        getWorkerEvaluationsInteractor.setRange(pageIndex,DEFAULT_SIZE);
        getWorkerEvaluationsInteractor.execute(new OldBaseObserver<List<Evaluation>>(merchantWorkerEvaluationsView) {
            @Override
            public void onNext(List<Evaluation> evaluations) {
                merchantWorkerEvaluationsView.render(evaluations);
            }
        });
    }


    public void addWorkerMoreEvaluations(){
        merchantWorkerEvaluationsView.showLoading();
        getWorkerEvaluationsInteractor.setRange(++pageIndex,DEFAULT_SIZE);
        getWorkerEvaluationsInteractor.execute(new OldBaseObserver<List<Evaluation>>(merchantWorkerEvaluationsView) {
            @Override
            public void onNext(List<Evaluation> evaluations) {
                merchantWorkerEvaluationsView.hideLoading();
                merchantWorkerEvaluationsView.renderMore(evaluations);
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
        getWorkerEvaluationsInteractor.unsubscribe();
        getBusinessEvaluationsInteractor.unsubscribe();
    }

}
