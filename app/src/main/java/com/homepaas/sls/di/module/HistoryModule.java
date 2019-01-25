package com.homepaas.sls.di.module;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.newmvp.base.BaseView;
import com.homepaas.sls.newmvp.contract.HistoryContract;
import com.homepaas.sls.newmvp.presenter.HistoryPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mhy on 2017/8/28.
 */
@Module
public class HistoryModule {
    private HistoryContract.View view;

    public HistoryModule(BaseView view) {
        this.view = (HistoryContract.View) view;
    }

    @Provides
    @ActivityScope
    HistoryPresenter historyPresenter() {
        return new HistoryPresenter(view);
    }
}
