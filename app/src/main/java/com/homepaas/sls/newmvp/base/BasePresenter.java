package com.homepaas.sls.newmvp.base;

/**
 *  on 2017/6/4.
 */

public interface BasePresenter<T extends BaseView> {
    void unView();

    void bindView(T view);
}
