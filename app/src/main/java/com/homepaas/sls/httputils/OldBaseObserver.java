package com.homepaas.sls.httputils;

import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.common.NetUtils;
import com.homepaas.sls.newmvp.base.BaseView;

import java.net.ConnectException;

import rx.Subscriber;


/**
 * 基于历史代码进行统一封装的错误回调和启动的网络检查
 */

public class OldBaseObserver<T extends Object> extends Subscriber<T> {
    private static final String TAG = "Observer";
    private BaseView baseView;

    public OldBaseObserver(BaseView baseView) {
        this.baseView = baseView;
    }

    public OldBaseObserver() {
    }

    @Override
    public void onStart() {
        super.onStart();
        //接口调用增加检测网络情况,并给出提示
        if (!NetUtils.isConnected(SimpleTinkerInApplicationLike.getMainApplication())) {
            onError(new ConnectException(Constants.CONNECT_EXCEPTION));
        }
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onCompleted() {
        hideLoading();
    }

    @Override
    public void onError(Throwable t) {
        hideLoading();
        showError(t);
    }

    /**
     * 如果error需要自行处理，重写并不调用父类方法即可
     *
     * @param t
     */
    public void showError(Throwable t) {
        if (baseView != null)
            baseView.showError(t);
    }

    private void hideLoading() {
        if (baseView != null)
            baseView.hideLoading();
    }
}
