package com.homepaas.sls.mvp.presenter.personalcenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.interactor.ModifyUserNicknameInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.personalcenter.ModifyNicknameView;

import javax.inject.Inject;

/**
 * on 2016/1/22 0022
 *
 * @author zhudongjie .
 */
@ActivityScope
public class ModifyNicknamePresenter implements Presenter{

    private ModifyNicknameView modifyNicknameView;

    @Inject
    ModifyUserNicknameInteractor modifyUserNicknameInteractor;

    @Inject
    public ModifyNicknamePresenter() {
    }

    public void setView(ModifyNicknameView modifyNicknameView){
        this.modifyNicknameView = modifyNicknameView;
    }
    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        modifyUserNicknameInteractor.unsubscribe();
    }

    public void modifyNickName(final String newNickName){
        modifyUserNicknameInteractor.setNickname(newNickName);
        modifyUserNicknameInteractor.execute(new OldBaseObserver<String>(modifyNicknameView) {
            @Override
            public void onCompleted() {
                super.onCompleted();
                modifyNicknameView.afterNicknameModified(newNickName);
            }

            @Override
            public void showError(Throwable t) {
                super.showError(t);
                if (t instanceof AuthException){
                    modifyNicknameView.showLogin();
                }
            }

            @Override
            public void onNext(String s) {
               // modifyNicknameView.showMessage("昵称修改成功！");
            }
        });
    }
}
