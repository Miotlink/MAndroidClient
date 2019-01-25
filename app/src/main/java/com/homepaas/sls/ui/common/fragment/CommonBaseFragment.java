package com.homepaas.sls.ui.common.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.homepaas.sls.R;
import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.common.NetUtils;
import com.homepaas.sls.di.HasComponent;
import com.homepaas.sls.di.component.ApplicationComponent;
import com.homepaas.sls.navigation.Navigator;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.ui.common.activity.CommonBaseActivity;
import com.homepaas.sls.util.CommonDialogUtils;
import com.homepaas.sls.util.LogUtils;
import com.homepaas.sls.util.ToastUtil;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Created by mhy on 2017/8/17.
 */

public abstract class CommonBaseFragment extends Fragment implements BaseView {
    protected Context mContext;
    protected View mView;
    protected Navigator mNavigator = SimpleTinkerInApplicationLike.getApplicationComponent().navigator();
    private ToastUtil toastUtil = SimpleTinkerInApplicationLike.getApplicationComponent().getToastUtil();
    private CommonDialogUtils commonDialogUtils = CommonDialogUtils.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void showLogin() {
        if (getActivity() instanceof CommonBaseActivity) {
            ((CommonBaseActivity) getActivity()).showLogin();
        }
    }

    @Override
    public void showLoading(boolean isCancel) {
        showCommonDialog(isCancel);
    }

    @Override
    public void hideLoading() {
        if (usingDefaultLoading()) {
            commonDialogUtils.hideDialog();
        } else {
            hideCustomLoading();
        }
    }

    public void showCommonDialog(boolean isCancel) {
        if (usingDefaultLoading()) {
            commonDialogUtils.showDialog(isCancel, getActivity());
        } else {
            showCustomLoading();
        }
    }

    /**
     * 是否使用默认加载动画，比如下拉刷新的情况下不一定需要使用
     *
     * @return true, 如果使用加载动画
     */
    protected boolean usingDefaultLoading() {
        return true;
    }

    @Override
    public void showError(Throwable e) {
        if (getActivity() instanceof CommonBaseActivity) {
            ((CommonBaseActivity) getActivity()).showError(e);
        }
    }

    @Override
    public void showMessage(String message) {
        toastUtil.showToast(message);
    }

    protected void showCustomLoading() {
    }

    protected void hideCustomLoading() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showMessage(int ResId) {
        toastUtil.showToast(ResId);
    }

    @Override
    public void showLoading() {
        showCommonDialog(false);
    }

    protected ApplicationComponent getApplicationComponent() {
        return SimpleTinkerInApplicationLike.getApplicationComponent();
    }

    public boolean showNetWorkInfo() {
        if (NetUtils.isConnected(getActivity()))
            return true;
        else {
            showMessage(getString(R.string.network_error));
            return false;
        }
    }


    protected void notifyNetWorkChange(boolean isConnected) {

    }

    protected void initializeInjector() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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

    @SuppressWarnings("unchecked")
    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((HasComponent<T>) getActivity()).getComponent());
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
            rationale |= shouldShowRequestPermissionRationale(permission);
        }
        if (rationale) {
            if (getActivity() instanceof CommonBaseActivity) {
                ((CommonBaseActivity) getActivity()).makePrimaryColorSnackBar(R.string.common_request_permission, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.common_allow_permission, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestPermissions(permissions, requestCode);
                            }
                        })
                        .show();
            }
        } else {
            requestPermissions(permissions, requestCode);
        }
        return false;
    }
}
