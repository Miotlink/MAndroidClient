package com.homepaas.sls.ui.personalcenter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.homepaas.sls.R;
import com.homepaas.sls.data.BuildConfig;
import com.homepaas.sls.di.component.DaggerPersonalInfoComponent;
import com.homepaas.sls.di.module.ActivityModule;
import com.homepaas.sls.di.module.PersonalInfoModule;
import com.homepaas.sls.domain.entity.PushInfo;
import com.homepaas.sls.mvp.model.PersonalInfoModel;
import com.homepaas.sls.mvp.presenter.TokenPresenter;
import com.homepaas.sls.mvp.presenter.personalcenter.PersonalInfoPresenter;
import com.homepaas.sls.mvp.view.TokenView;
import com.homepaas.sls.mvp.view.personalcenter.PersonalInfoView;
import com.homepaas.sls.ui.WebViewActivity;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CenterTitleToolbar;
import com.homepaas.sls.ui.widget.ScalableHintEditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

//填写邀请码
public class WriteBackInvitationActivity extends CommonBaseActivity implements PersonalInfoView, TokenView {

    private static final String TAG = "WriteBackInvitationActi";
    @Inject
    PersonalInfoPresenter personalInfoPresenter;
    @Inject
    TokenPresenter tokenPresenter;
    @Bind(R.id.invitation_input)
    ScalableHintEditText invitationInput;
    @Bind(R.id.invitation_layout)
    LinearLayout invitationLayout;
    @Bind(R.id.toolbar)
    CenterTitleToolbar toolbar;
    private PushInfo pushInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_back_invitation;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        personalInfoPresenter.setView(this);
        personalInfoPresenter.getPersonalInfo();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerPersonalInfoComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .personalInfoModule(new PersonalInfoModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.confirm)
    public void confirmInvitation() {

    }

    @OnClick({R.id.want_more, R.id.want_more_arrow})
    public void wantMoreInvitation() {
        WebViewActivity.start(this, pushInfo);
    }

    @Override
    public void render(PersonalInfoModel model) {
        pushInfo = new PushInfo();
        pushInfo.setIsShare("");
        if (!TextUtils.isEmpty(model.getRecommendationUrl_Add())) {
            invitationLayout.setVisibility(View.GONE);
        }
        tokenPresenter.getToken(this);
    }

    @Override
    public void showQrCodeView(PersonalInfoModel model) {

    }

    @Override
    public void modifyPhoto(String url) {

    }

    @Override
    public void logout() {

    }

    @Override
    public void onToken(String token) {
        pushInfo.setUrl(pushInfo.getUrl() + "?token=" + token);
        if (BuildConfig.DEBUG)
            Log.i(TAG, "onToken: " + token);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void retrieveData() {
        personalInfoPresenter.getPersonalInfo();
    }
}
