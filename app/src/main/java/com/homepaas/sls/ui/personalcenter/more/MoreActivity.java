package com.homepaas.sls.ui.personalcenter.more;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.growingio.android.sdk.collection.GrowingIO;
import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.R;
import com.homepaas.sls.data.network.Url;
import com.homepaas.sls.di.component.DaggerMoreActivityComponent;
import com.homepaas.sls.domain.interactor.CheckUpdateInteractor;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.mvp.presenter.MoreActivityPresenter;
import com.homepaas.sls.mvp.view.MoreActivityView;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.ui.widget.CommonDialog;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class MoreActivity extends CommonBaseActivity implements MoreActivityView {

    private static final int PERMISSION_UPDATE = 1;

    @Bind(R.id.app_version)
    TextView mVersion;
    @Bind(R.id.logout)
    Button logout;

    @Inject
    CheckUpdateInteractor mInteractor;
    @Inject
    MoreActivityPresenter moreActivityPresenter;
    @Inject
    Navigator mNavigator;

    private CommonDialog dialog;
    private CommonAppPreferences commonAppPreferences;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_more;
    }

    @Override
    protected void initView() {
        commonAppPreferences = new CommonAppPreferences(this);
        moreActivityPresenter.setMoreActivityView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String version = "助家生活v" + BuildConfig.VERSION_NAME;
        mVersion.setText(version);

        dialog = new CommonDialog.Builder()
                .setContent("是否电话联系客服" + getString(R.string.service_phone_number))
                .setConfirmButton("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNavigator.dial(MoreActivity.this, getString(R.string.service_phone_number));
                    }
                }).setCancelButton("否", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }).showTitle(false).create();
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerMoreActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_UPDATE) {
            for (int gra : grantResults) {
                if (gra != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //用户登陆状态下显示退出登陆按钮
        logout.setVisibility(PreferencesUtil.getBoolean(StaticData.USER_LOGIN_STATUE) ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        moreActivityPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void logout() {
        PreferencesUtil.saveBoolean(StaticData.USER_LOGIN_STATUE, false);
//        ActivityCompat.finishAfterTransition(this);
        GrowingIO growingIO = GrowingIO.getInstance();
        growingIO.setCS1("user_id", "");
        EventPersonalInfo info = new EventPersonalInfo(EventPersonalInfo.LOGIN_STATE);
        info.setLogin(false);
        EventBus.getDefault().post(info);
        imLogout();
        PreferencesUtil.cleanValues();
        mNavigator.login(MoreActivity.this, 0);
        //IM 欢迎语打开
        PreferencesUtil.saveBoolean(StaticData.IM_WELCOME, false);
    }


    private void imLogout() {
        commonAppPreferences.setHeadPortrait("");
        commonAppPreferences.setImInfo("", "");
        ChatClient.getInstance().logout(true, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(int i, String s) {
                ChatClient.getInstance().logout(false, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(int i, String s) {
                    }

                    @Override
                    public void onProgress(int i, String s) {
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


    @OnClick({R.id.tv_feedback, R.id.tv_zj_live, R.id.tv_item_service_call, R.id.tv_item_user_agreement, R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_feedback://问题反馈
                mNavigator.feedback(this);
                break;
            case R.id.tv_zj_live: //关于助家生活
                mNavigator.loadWebView(this, Url.HTM_BASE_URL_DEFAULT + "gyss.htm", "关于助家生活");
                break;
            case R.id.tv_item_service_call://联系我们
                mNavigator.loadWebView(this, Url.HTM_BASE_URL_DEFAULT + "abouus/aboutus.html", "联系我们");
                break;
            case R.id.tv_item_user_agreement://用户服务协议
                mNavigator.loadWebView(this, Url.HTM_BASE_URL_DEFAULT + "yhfwxy.htm", "用户协议");
                break;
            case R.id.logout://退出登录
                CommonDialog commonDialog = new CommonDialog.Builder()
                        .showTitle(false)
                        .setContent("是否确认退出登录？")
                        .setCancelButton("否", null)
                        .setConfirmButton("是", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moreActivityPresenter.logout();
                            }
                        })
                        .create();

                commonDialog.show(getSupportFragmentManager(), "");
                break;
            default:
                break;
        }
    }
}
