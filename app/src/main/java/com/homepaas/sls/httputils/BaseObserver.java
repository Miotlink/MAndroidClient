package com.homepaas.sls.httputils;

import com.homepaas.sls.newmvp.base.BaseView;


/**
 * Observer，使用此方式，需要进行订阅管理
 */

public class BaseObserver<T> extends OldBaseObserver<T> {
    private static final String TAG = "Observer";
    private RequestCallBack mRequestCallBack;

    public BaseObserver(BaseView baseView) {
        super(baseView);
    }

    public BaseObserver(RequestCallBack requestCallBack, BaseView baseView) {
        super(baseView);
        this.mRequestCallBack = requestCallBack;
    }


    @Override
    public void onNext(T t) {
        mRequestCallBack.successRequest(t);
    }

    @Override
    public void onCompleted() {
        mRequestCallBack.onFinish();
    }

    @Override
    public void showError(Throwable t) {
        if (t instanceof ApiException) {
            ApiException apiException = (ApiException) t;
            if (apiException.getCode().equals("0")) {
                mRequestCallBack.successRequest(apiException.getCode());
            } else {
                super.showError(t);
            }
        } else {
            super.showError(t);
        }
    }
}
