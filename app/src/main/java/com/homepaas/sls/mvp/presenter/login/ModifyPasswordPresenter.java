package com.homepaas.sls.mvp.presenter.login;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.ModifyPasswordInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.login.ModifyPasswordView;

import javax.inject.Inject;

/**
 * on 2016/2/22 0022
 *
 * @author zhudongjie .
 */
@ActivityScope
public class ModifyPasswordPresenter implements Presenter{

    @Inject
    ModifyPasswordInteractor modifyPasswordInteractor;

    private ModifyPasswordView modifyPasswordView;

    @Inject
    public  ModifyPasswordPresenter(){}


    public void setView(ModifyPasswordView view){
        modifyPasswordView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        modifyPasswordInteractor.unsubscribe();
    }

    public void modifyPassword(String oldPassword,String newPassword){
        modifyPasswordView.showLoading();
        modifyPasswordInteractor.setPassword(oldPassword,newPassword);
        modifyPasswordInteractor.execute(new OldBaseObserver<String>(modifyPasswordView) {
            @Override
            public void onNext(String s) {
                modifyPasswordView.passwordModified();
            }


        });
    }

}
