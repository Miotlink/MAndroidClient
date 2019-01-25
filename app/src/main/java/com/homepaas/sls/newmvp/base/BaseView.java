package com.homepaas.sls.newmvp.base;

import android.content.Context;

/**
 * on 2017/6/4.
 */

public interface BaseView {
    void showLogin();

    void showLoading(boolean isCancel);

    void hideLoading();

    void showError(Throwable e);

    void showMessage(String message);

    void showMessage(int ResId);

    Context getContext();
    void showLoading();
}