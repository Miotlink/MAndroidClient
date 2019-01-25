package com.homepaas.sls.httputils;

/**
 * on 2017/6/10.
 */

public interface RequestCallBack<T> {
    void successRequest(T data);
    //请求结束调用此方法，不管请求成功、失败
    void onFinish();
     //如果重新的话标识错误自行处理
    void onError(Throwable t);
}
