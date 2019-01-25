package com.homepaas.sls.httputils.rxbinding;

import android.view.View;

import com.homepaas.sls.util.LogUtils;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * 2017/4/19.
 */

public class RxBindingViewClickHelper {
    public static final int TIME = 500;

    public static void onClick(final View view, final OnRxClick action) {
        //防抖处理
        RxView.clicks(view)
                .throttleFirst(TIME, TimeUnit.MILLISECONDS)   //500 毫秒之内只取一个点击事件，防抖操作
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (action != null)
                            action.rxClick(view);
                        LogUtils.i("TAG","RxView.clicks");
                    }
                });
    }

    public interface OnRxClick {
        void rxClick(View view);
    }
}
