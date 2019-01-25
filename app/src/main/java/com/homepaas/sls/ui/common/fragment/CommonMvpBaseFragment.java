package com.homepaas.sls.ui.common.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 * 没有和p结合
 * fragment通过事物管理器进行创建的话，可以通过onHiddenChanged进行回调fragment的显示和隐藏的状态，在该状态下进行对应的业务逻辑的处理
 * fragment和viewpager结合使用的话，可以通过setUserVisibleHint方法进行判断fragment的显示隐藏回调
 */
public abstract class CommonMvpBaseFragment<P extends BasePresenter> extends CommonBaseFragment implements BaseView {
    protected P mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter = getPresenter();
        if (mPresenter != null) {
            //传递BaseView引用给mPresenter
            mPresenter.bindView(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {//p v 解绑
            mPresenter.unView();
            mPresenter = null;
        }
    }

    //p实例通过基类机芯返回
    protected abstract P getPresenter();
}
