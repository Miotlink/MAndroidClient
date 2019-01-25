package com.homepaas.sls.ui.common.activity;

import android.os.Bundle;

import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

import butterknife.ButterKnife;

//import com.umeng.analytics.MobclickAgent;


/**
 * Created by Administrator on 2015/12/23.
 */
public abstract class CommonMvpBaseActivity<P extends BasePresenter> extends CommonBaseActivity implements BaseView {

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = getPresenter();
        if (mPresenter != null) {
            //传递BaseView引用给mPresenter
            mPresenter.bindView(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {//p v 解绑
            mPresenter.unView();
            mPresenter = null;
        }
        ButterKnife.unbind(this);
    }

    //p实例通过基类机芯返回
    protected abstract P getPresenter();
}
