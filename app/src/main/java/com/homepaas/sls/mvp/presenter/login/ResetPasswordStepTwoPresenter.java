package com.homepaas.sls.mvp.presenter.login;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.ResetPasswordInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.login.ResetPasswordStepTwoView;

import javax.inject.Inject;

/**
 * on 2016/1/21 0021
 *
 * @author zhudongjie .
 */
@ActivityScope
public class ResetPasswordStepTwoPresenter implements Presenter{

    ResetPasswordStepTwoView resetPasswordStepTwoView;

    @Inject
    ResetPasswordInteractor resetPasswordInteractor;

    @Inject
    public ResetPasswordStepTwoPresenter() {
    }

    public void setResetPasswordStepTwoView(ResetPasswordStepTwoView resetPasswordStepTwoView) {
        this.resetPasswordStepTwoView = resetPasswordStepTwoView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        resetPasswordInteractor.unsubscribe();
    }

    public void confirmNewPassword(String phone,String captcha,String password){
        resetPasswordStepTwoView.showLoading();
        resetPasswordInteractor.setParameters(phone,captcha,password);
        resetPasswordInteractor.execute(new OldBaseObserver<String>(resetPasswordStepTwoView) {
            @Override
            public void onNext(String s) {
                resetPasswordStepTwoView.passwordReset();
            }
        });
    }
}
