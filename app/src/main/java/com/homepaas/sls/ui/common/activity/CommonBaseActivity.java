package com.homepaas.sls.ui.common.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.JsonParseException;
import com.growingio.android.sdk.collection.GrowingIO;
import com.growingio.android.sdk.utils.LogUtil;
import com.homepaas.sls.R;
import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.common.NetUtils;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.di.component.ApplicationComponent;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.event.EventPersonalInfo;
import com.homepaas.sls.httputils.ApiException;
import com.homepaas.sls.httputils.Constants;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.pushservice.PushUtil;
import com.homepaas.sls.ui.login.LoginDialogFragment;
import com.homepaas.sls.ui.widget.CommonAppPreferences;
import com.homepaas.sls.util.CommonDialogUtils;
import com.homepaas.sls.util.LogUtils;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;
import com.homepaas.sls.util.ToastUtil;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import retrofit.HttpException;

//import com.umeng.analytics.MobclickAgent;


public abstract class CommonBaseActivity extends AppCompatActivity implements BaseView {

    private static final String TAG = "BaseActivity";
    public static final int REQUEST_LOGIN_BASE_ACTIVITY = 9999;
    protected String token;
    private boolean islogin = false;
    private boolean isShow = false;


    protected Context mContext;
    private LoginDialogFragment loginDialogFragment;
    private CommonDialogUtils commonDialogUtils = CommonDialogUtils.getInstance();
    protected List<Fragment> fragmentList;
    @Inject
    protected Navigator mNavigator;
    @Inject
    protected RestApiHelper restApiHelper;
    @Inject
    protected PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    protected PushUtil pushUtil;
    @Inject
    protected ToastUtil toastUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //出现异常全部启动到首页去
//        if (savedInstanceState != null && !(mContext instanceof MainActivity)) {
//            startActivity(new Intent(mContext, MainActivity.class));
//            return;
//        }

        mContext = this;
        initializeInjector();
        setStateBarColor();
        if (getLayoutId() != -1) {
            setContentView(getLayoutId());
            ButterKnife.bind(this);
        }
        initView();
        initData();
        SimpleTinkerInApplicationLike.addActivity(this);
    }

    public void setStateBarColor() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtils.i("onSaveInstanceState:"+this.getClass().getSimpleName());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtils.i("onRestoreInstanceState:"+this.getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commonDialogUtils.hideDialog();
        ButterKnife.unbind(this);
        SimpleTinkerInApplicationLike.removeActivity(this);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 是否使用默认加载动画，比如下拉刷新的情况下不一定需要使用
     *
     * @return true, 如果使用加载动画
     */
    protected boolean usingDefaultLoading() {
        return true;
    }

    @Override
    public void showLoading() {
        showCommonDialog(false);
    }

    @Override
    public void showLoading(boolean isCancel) {
        showCommonDialog(isCancel);
    }

    /**
     * 多线程多个diaog显示 通过synchronized 进行加锁避免
     *
     * @param isCancel
     */
    public void showCommonDialog(boolean isCancel) {
        if (usingDefaultLoading()) {
            commonDialogUtils.showDialog(isCancel, this);
        } else {
            showCustomLoading();
        }
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    protected void showCustomLoading() {

    }

    protected void hideCustomLoading() {

    }

    public boolean requestRuntimePermissions(final String[] permissions, final int requestCode) {
        boolean ret = true;
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                ret &= (PermissionChecker.checkSelfPermission(getContext(), permission) == PermissionChecker.PERMISSION_GRANTED);
            else
                ret &= (ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED);
        }
        if (ret) {
            return true;
        }
        boolean rationale = false;
        for (String permission : permissions) {
            rationale |= ActivityCompat.shouldShowRequestPermissionRationale(CommonBaseActivity.this, permission);

        }
        if (rationale) {
            makePrimaryColorSnackBar(R.string.common_request_permission, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.common_allow_permission, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(CommonBaseActivity.this, permissions, requestCode);
                        }
                    })
                    .show();

        } else {
            ActivityCompat.requestPermissions(CommonBaseActivity.this, permissions, requestCode);

        }
        return false;
    }


    protected void closeInputMethod() {
        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public View getSnackBarHolderView() {
        return new FrameLayout(this);
    }

    public Snackbar makeColorSnackBar(@StringRes int resId, int duration, @ColorInt int colorId) {
        Snackbar snackbar = Snackbar.make(getSnackBarHolderView(), resId, duration);
        View view = snackbar.getView();//获取Snackbar的view
        if (view != null) {
            view.setBackgroundColor(colorId);
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.white));//获取Snackbar的message控件，修改字体颜色
        }
        return snackbar;
    }

    public Snackbar makePrimaryColorSnackBar(@StringRes int resId, int duration) {
        return makeColorSnackBar(resId, duration, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void hideLoading() {
        if (usingDefaultLoading()) {
            commonDialogUtils.hideDialog();
        } else {
            hideCustomLoading();
        }
    }

    /**
     * 获取登录成功后返回的token
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_LOGIN_BASE_ACTIVITY:
                    islogin = false;
                    if (data != null && data.getBooleanExtra("Status", false)) {
                        token = data.getStringExtra("TOKEN");
                        retrieveData();
                    }
                    break;
            }
        }
    }

    public String getToken() {
        return token;
    }


    public LoginDialogFragment getLoginDialogFragment() {
        return loginDialogFragment;
    }


    @Override
    public void showError(Throwable t) {
        LogUtil.i("TAG","showError");
//        ResponseMetaDataException
        if (t instanceof ConnectException || t.getCause() instanceof ConnectException) {
            LogUtils.e(TAG, "onError: ConnectException-----" + t.getMessage());
            showMessage(Constants.CONNECT_EXCEPTION);
        } else if (t instanceof AuthException || t instanceof ResponseMetaAuthException && t.getCause() instanceof ConnectException || t instanceof ApiException) {
            if (t instanceof ApiException) {
                ApiException apiException = (ApiException) t;
                if (!TextUtils.equals("2004", apiException.getCode())) {
                    showMessage(apiException.getMessage());
                    return;
                }
            }
            //清除用户信息
            clearUserInfo();
            //登录框重复的问题
            if (!isShow) {
                showMessage(t.getMessage());
//                mNavigator.main(CommonBaseActivity.this);
                mNavigator.userLoginOut(CommonBaseActivity.this, REQUEST_LOGIN_BASE_ACTIVITY);
                isShow = true;
            }
        } else if (t instanceof SocketTimeoutException || t.getCause() instanceof SocketTimeoutException) {
            LogUtils.e(TAG, "onError: SocketTimeoutException----" + t.getMessage());
            showMessage(Constants.SOCKET_TIMEOUT_EXCEPTION);
        } else if (t instanceof UnknownHostException || t.getCause() instanceof UnknownHostException) {
            LogUtils.e(TAG, "onError: UnknownHostException-----" + t.getMessage());
//            showMessage(Constants.UNKNOWN_HOST_EXCEPTION);
            showMessage(Constants.CONNECT_EXCEPTION);
        } else if (t instanceof HttpException || t.getCause() instanceof HttpException) {
            LogUtils.e(TAG, "onError: HttpException-----" + t.getMessage());
            HttpException httpException = (HttpException) t;
            if (httpException.code() == 500) {
                showMessage(Constants.INTERNAL_SERVER_ERROR);
            } else if (httpException.code() == 504) {
                showMessage(Constants.SOCKET_TIMEOUT_EXCEPTION);
            }
            LogUtils.i("TAG", "showError" + t.getMessage());
//            else
//            {
//                showMessage(t.getMessage());
//            }
        } else if (t instanceof NullPointerException || t.getCause() instanceof NullPointerException) {
            LogUtils.e(TAG, "onError:NullPointerException----" + t.getMessage());
//            showMessage(Constants.NULL_POINT_EREXCEPTION);
        } else if (t instanceof JsonParseException
                || t instanceof JSONException
                || t instanceof ParseException) {
            LogUtils.e(TAG, "onError: ParseException-----" + t.getMessage());
            showMessage(Constants.PARSE_EXCEPTION);
        } else if (t instanceof javax.net.ssl.SSLHandshakeException || t.getCause() instanceof javax.net.ssl.SSLHandshakeException) {
            LogUtils.e(TAG, "onError: SSLHandshakeException-----" + t.getMessage());
            showMessage(Constants.SSL_HANDSHAKE_EXCEPTION);
        } else {
            showMessage(t.getMessage());
        }
    }

    public void clearUserInfo() {
        PreferencesUtil.saveBoolean(StaticData.USER_LOGIN_STATUE, false);
        GrowingIO growingIO = GrowingIO.getInstance();
        growingIO.setCS1("user_id", "");
        EventPersonalInfo info = new EventPersonalInfo(EventPersonalInfo.LOGIN_STATE);
        info.setLogin(false);
        EventBus.getDefault().post(info);
        imLogout();
        PreferencesUtil.cleanValues();
        //IM 欢迎语打开
//        PreferencesUtil.saveBoolean(StaticData.IM_WELCOME, false);
    }

    private void imLogout() {
        CommonAppPreferences commonAppPreferences = new CommonAppPreferences(this);
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

    @Override
    public void showMessage(String message) {
        toastUtil.showToast(message);
    }

    @Override
    public void showMessage(int ResId) {
        toastUtil.showToast(ResId);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLogin() {
        if (!islogin) {
            mNavigator.login(CommonBaseActivity.this, REQUEST_LOGIN_BASE_ACTIVITY);
            islogin = true;
        }
    }

    //待测试
    protected void throttle(final View view) {
        if (view != null) {
            view.setEnabled(false);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setEnabled(true);
                }
            }, 600);
        }
    }

    public void onNetWorkStateChange(int netStatus) {
        LogUtils.i("TAG", CommonBaseActivity.this.getClass().getSimpleName() + ":" + (netStatus == NetUtils.NETWORK_NONE ? "无网络连接" : "网络已连接"));
    }

    protected void throttleClickable(final View view) {
        if (view != null) {
            view.setClickable(false);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setClickable(true);
                }
            }, 600);
        }
    }


    protected void initializeInjector() {
        getApplicationComponent().inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return SimpleTinkerInApplicationLike.getApplicationComponent();
    }

    protected boolean showNetWorkInfo() {
        if (NetUtils.isConnected(getApplication()))
            return true;
        else {
            showMessage(getString(R.string.network_error));
            return false;
        }
    }

    /**
     * 获取数据回调,针对进入某个页面，身份验证失效的情况，验证通过后再次请求数据
     */
    protected void retrieveData() {
    }


    protected void notifyNetWorkChange(boolean isConnected) {

    }


    /**
     * 切换目标Fragment
     *
     * @param containerViewId 容器id
     * @param targetFragment  目标fragment对象
     */
    protected void replaceFragment(int containerViewId, Fragment targetFragment) {
        FragmentManager manager = getSupportFragmentManager();//得到FragmentManager
        FragmentTransaction transaction = manager.beginTransaction();//开启事务
        transaction.replace(containerViewId, targetFragment);
        //提交事务
        transaction.commitAllowingStateLoss();
    }

    /**
     * 显示fragment，该fragment切换时只会调用onResume()，不会影响生命周期
     *
     * @param containerViewId
     * @param targetFragment
     */
    protected void showFragment(int containerViewId, Fragment targetFragment) {
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (Fragment fragment : fragmentList) {
            ft.hide(fragment);
            fragment.onPause();
        }
//        ft.add()
        if (targetFragment.isAdded()) {
            //每次手动调用该方法
            targetFragment.onResume();
            ft.show(targetFragment);
        } else {
            ft.add(containerViewId, targetFragment);
            fragmentList.add(targetFragment);
        }
        ft.commitAllowingStateLoss();
    }
}
