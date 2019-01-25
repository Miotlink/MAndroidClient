package com.homepaas.sls.newmvp.base;

/**
 *  on 2017/6/4.
 */

public abstract class IBasePresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView = null;//p中v的引用

    @Override
    public void unView() {
        mView = null;
        dispose();
    }

    @Override
    public void bindView(T view) {
        this.mView = view;
    }

    //解绑view或者model子类实现方法
   public abstract void dispose();
}
