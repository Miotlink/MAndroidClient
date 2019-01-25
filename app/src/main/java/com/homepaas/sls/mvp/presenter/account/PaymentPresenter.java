package com.homepaas.sls.mvp.presenter.account;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.PayDetail;
import com.homepaas.sls.domain.interactor.GetPayDetailsInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.model.mapper.PaymentMapper;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.account.PaymentView;

import java.util.List;

import javax.inject.Inject;

/**
 * on 2016/6/22 0022
 *
 * @author zhudongjie
 */
@ActivityScope
public class PaymentPresenter implements Presenter {

    private static final String TAG = "PaymentPresent";

    private GetPayDetailsInteractor getPayDetailsInteractor;

    private PaymentView view;

    @Inject
    PaymentMapper mapper;

    @Inject
    public PaymentPresenter(GetPayDetailsInteractor getPayDetailsInteractor) {
        this.getPayDetailsInteractor = getPayDetailsInteractor;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getPayDetailsInteractor.unsubscribe();
    }


    public void obtainPayDetails() {
        getPayDetailsInteractor.execute(new OldBaseObserver<List<PayDetail>>(view) {
            @Override
            public void onNext(List<PayDetail> payDetails) {
                view.render(mapper.transform(payDetails));
            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
            }
        });
    }

    public void setView(PaymentView view) {
        this.view = view;
    }
}

