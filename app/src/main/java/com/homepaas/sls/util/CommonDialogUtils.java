package com.homepaas.sls.util;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.ui.fragment.ProgressFragment;

/**
 * Created by mhy on 2017/8/14.
 * 防止 activity或者fragment 访问 ProgressFragment副本生成多个，不能保证dialog的唯一性，通过工具类进行保证单一实例，通过工具类进行处理显示隐藏
 */

public class CommonDialogUtils {
    public static CommonDialogUtils commonDialogUtils;
    private ProgressFragment mProgressFragment;
    private String dialogInstanceTag;

    private CommonDialogUtils(String dialogInstanceTag) {
        this.dialogInstanceTag = dialogInstanceTag;

    }

    private CommonDialogUtils() {

    }

    public static CommonDialogUtils getInstance() {
        if (commonDialogUtils == null) {
            synchronized (CommonDialogUtils.class) {
                if (commonDialogUtils == null)
                    commonDialogUtils = new CommonDialogUtils();
            }
        }
        return commonDialogUtils;
    }

    public static CommonDialogUtils getInstance(String tag) {
        if (commonDialogUtils == null) {
            synchronized (CommonDialogUtils.class) {
                if (commonDialogUtils == null)
                    commonDialogUtils = new CommonDialogUtils(tag);
            }
        }
        return commonDialogUtils;
    }

    public synchronized void showDialog(boolean isCancel, FragmentActivity fragmentActivity) {
        LogUtils.i("TAG","showDialog");
        Activity currentActivity = SimpleTinkerInApplicationLike.getCurrentActivity();
        if (currentActivity != null && fragmentActivity.isFinishing())
            return;
        //防止进入新的界面后，上一个界面延时显示弹框。会导致
        if (currentActivity != null && currentActivity != fragmentActivity)
            return;
        if (mProgressFragment != null) {
            mProgressFragment.dismissDialog();
            mProgressFragment = null;
        }
        mProgressFragment = ProgressFragment.newInstance(fragmentActivity, isCancel);
        mProgressFragment.showAllowingStateLoss(fragmentActivity.getSupportFragmentManager(), null);
    }

    public synchronized void hideDialog() {
        LogUtils.i("TAG","hideDialog");
        if (mProgressFragment != null) {
            try {
                mProgressFragment.dismissDialog();
            } finally {
                mProgressFragment = null;
            }
        }
    }

    public String getDialogInstanceTag() {
        return dialogInstanceTag;
    }

    public void setDialogInstanceTag(String dialogInstanceTag) {
        this.dialogInstanceTag = dialogInstanceTag;
    }
}
