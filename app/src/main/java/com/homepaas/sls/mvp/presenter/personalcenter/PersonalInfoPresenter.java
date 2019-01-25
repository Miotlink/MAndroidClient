package com.homepaas.sls.mvp.presenter.personalcenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.domain.interactor.GetPersonalInfoInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.domain.interactor.ModifyPhotoInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.model.PersonalInfoModel;
import com.homepaas.sls.mvp.model.mapper.PersonalInfoModelMapper;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.personalcenter.PersonalInfoView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2015/12/23.
 *
 */

@ActivityScope
public class PersonalInfoPresenter implements Presenter {

    private PersonalInfoView view;

    private GetPersonalInfoInteractor getPersonalInfoInteractor;

    private Interactor logoutInteractor;

    private ModifyPhotoInteractor modifyPhotoInteractor;

    private PersonalInfoModelMapper mapper;

    @Inject
    public PersonalInfoPresenter(GetPersonalInfoInteractor getPersonalInfoInteractor,
                                 @Named("LogoutInteractor") Interactor logoutInteractor, ModifyPhotoInteractor modifyPhotoInteractor, PersonalInfoModelMapper mapper) {
        this.getPersonalInfoInteractor = getPersonalInfoInteractor;
        this.logoutInteractor = logoutInteractor;
        this.modifyPhotoInteractor = modifyPhotoInteractor;
        this.mapper = mapper;
    }

    public void setView(PersonalInfoView view) {
        this.view = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getPersonalInfoInteractor.unsubscribe();
        logoutInteractor.unsubscribe();
        modifyPhotoInteractor.unsubscribe();

    }

    public void getPersonalInfo() {
        // this.view.showLoading();
        this.getPersonalInfoInteractor.execute(new OldBaseObserver<PersonalInfo>(view) {
            @Override
            public void onNext(PersonalInfo personalInfo) {
                PersonalInfoModel model = PersonalInfoPresenter.this.mapper.transformToModel(personalInfo);
                PersonalInfoPresenter.this.view.render(model);
            }
        });
    }

    public void logout() {
        logoutInteractor.execute(new OldBaseObserver<Boolean>(view) {
            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    view.logout();
                } else {
                    view.showMessage("登出失败");
                    view.logout();
                }
            }
        });
    }

    public void modifyPhoto(String filePath) {
        modifyPhotoInteractor.setFilePath(filePath);
        modifyPhotoInteractor.execute(new OldBaseObserver<String>(view) {
            @Override
            public void onNext(String url) {
                view.modifyPhoto(url);
            }
        });
    }

    public void showQrCode() {
        getPersonalInfoInteractor.execute(new OldBaseObserver<PersonalInfo>(view) {

            @Override
            public void onNext(PersonalInfo personalInfo) {
                view.showQrCodeView(mapper.transformToModel(personalInfo));
            }
        });
    }
}
