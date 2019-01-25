package com.homepaas.sls.mvp.presenter;

import android.util.Log;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.domain.interactor.CheckUpdateInteractor;
import com.homepaas.sls.domain.interactor.GetPersonalInfoInteractor;
import com.homepaas.sls.domain.interactor.GetUnreadMessageCountInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.event.RefreshPersonalCenterFragmentEvent;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.model.PersonalInfoModel;
import com.homepaas.sls.mvp.model.mapper.PersonalInfoModelMapper;
import com.homepaas.sls.mvp.view.PersonalCenterView;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;
import javax.inject.Named;

import static com.homepaas.sls.event.EventPersonalInfo.LOGIN_STATE;
import static com.homepaas.sls.event.EventPersonalInfo.NEW_MESSAGE;
import static com.homepaas.sls.event.EventPersonalInfo.PERSONAL_INFO;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
@ActivityScope
public class PersonalCenterPresenter implements Presenter {

    private static final String TAG = "PersonalCenterPresenter";

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private GetPersonalInfoInteractor getPersonalInfoInteractor;

    private GetUnreadMessageCountInteractor messageCountInteractor;

    private Interactor autoLoginInteractor;

    @Inject
    PersonalInfoModelMapper mapper;

    @Inject
    CheckUpdateInteractor mCheckUpdateInteractor;

    private PersonalCenterView personalCenterView;

//    private static boolean loggedIn;

    @Inject
    public PersonalCenterPresenter(GetPersonalInfoInteractor getPersonalInfoInteractor,
                                   GetUnreadMessageCountInteractor messageCountInteractor, @Named("AutoLoginInteractor") Interactor autoLoginInteractor) {
        this.getPersonalInfoInteractor = getPersonalInfoInteractor;
        this.messageCountInteractor = messageCountInteractor;
        this.autoLoginInteractor = autoLoginInteractor;
//        EventBus.getDefault().register(this);
    }

    public void setPersonalCenterView(PersonalCenterView personalCenterView) {
        this.personalCenterView = personalCenterView;
    }

    @Override
    public void resume() {
        // checkLoggedInAddRefresh();
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getPersonalInfoInteractor.unsubscribe();
        messageCountInteractor.unsubscribe();
        autoLoginInteractor.unsubscribe();
        mCheckUpdateInteractor.unsubscribe();
        EventBus.getDefault().unregister(this);
    }

    public void loadPersonalInfo() {
        if (DEBUG)
            Log.d(TAG, "loadPersonalInfo: ");
        getPersonalInfoInteractor.execute(new OldBaseObserver<PersonalInfo>(personalCenterView) {
            @Override
            public void onError(Throwable t) {
                super.onError(t);
//                loggedIn = false;
                personalCenterView.noticeCount(0);
            }

            @Override
            public void onNext(PersonalInfo personalInfo) {
                if (DEBUG)
                    Log.d(TAG, "onNext: " + personalInfo);
                PersonalInfoModel model = mapper.transformToModel(personalInfo);
                personalCenterView.render(model);
                if (model != null) {
//                    loggedIn = true;
                    getUnreadMessageCount();
                } else {
                    personalCenterView.noticeCount(0);
//                    loggedIn = false;
                }
            }
        });
    }

    public void getUnreadMessageCount() {
        messageCountInteractor.execute(new OldBaseObserver<Integer>(personalCenterView) {
            @Override
            public void onError(Throwable t) {
                super.onError(t);
//                loggedIn = false;
            }

            @Override
            public void onNext(Integer count) {
//                loggedIn = true;
                personalCenterView.noticeCount(count);
            }
        });
    }

    public boolean isLoggedIn() {
        return PreferencesUtil.getBoolean(StaticData.USER_LOGIN_STATUE);
    }


    /**
     * 因服务器端没有token是否过期的接口，通过尝试获取信息返回2004代码来判定
     */
    @Deprecated
    public void checkLoggedInAddRefresh() {
        autoLoginInteractor.execute(new OldBaseObserver<Boolean>(personalCenterView) {

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
//                loggedIn = false;
                personalCenterView.render(null);
            }

            @Override
            public void onNext(Boolean aBoolean) {
//                loggedIn = aBoolean;
                if (aBoolean) {
                    loadPersonalInfo();
                } else {
                    personalCenterView.render(null);
                }
            }
        });
    }

    public void showQrCode() {
        getPersonalInfoInteractor.execute(new OldBaseObserver<PersonalInfo>(personalCenterView) {
            @Override
            public void onNext(PersonalInfo personalInfo) {
                personalCenterView.showQrCode(mapper.transformToModel(personalInfo));
            }
        });
    }

    @Subscribe
    public void refresh(RefreshPersonalCenterFragmentEvent event) {
        loadPersonalInfo();
    }

    @Subscribe(sticky = true)
    public void onEvent(EventPersonalInfo event) {
        if (DEBUG)
            Log.d(TAG, "onEvent: " + event.changeType);

        boolean shouldReloadInfo = false;

        if ((event.changeType & NEW_MESSAGE) != 0) {
            getUnreadMessageCount();
        }
        if ((event.changeType & LOGIN_STATE) != 0) {
//            loggedIn = event.isLogin();
            if (event.isLogin()) {
                shouldReloadInfo = true;
            } else {
                personalCenterView.render(null);
                personalCenterView.noticeCount(0);
            }
        }
        if ((event.changeType & PERSONAL_INFO) != 0) {
            shouldReloadInfo = true;
        }
        if (shouldReloadInfo) {
            loadPersonalInfo();
        }
    }


}
