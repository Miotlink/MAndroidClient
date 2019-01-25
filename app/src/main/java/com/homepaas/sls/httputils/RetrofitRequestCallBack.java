package com.homepaas.sls.httputils;

/**
 * Created by Administrator on 2017/6/20.
 */

public abstract class RetrofitRequestCallBack<T> implements RequestCallBack<T> {

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onFinish() {

    }
}
