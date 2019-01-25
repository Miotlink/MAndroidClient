package com.homepaas.sls.mvp.presenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.ServiceTypeEx;
import com.homepaas.sls.domain.repository.ServiceTypeExRepository;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.view.ServiceListView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/1/16.
 *
 */

@ActivityScope
public class ServiceListPresenter implements Presenter {

    private ServiceListView view;

    @Inject
    ServiceTypeExRepository repository;

    @Inject
    public ServiceListPresenter() {
    }

    public void setView(ServiceListView view) {
        this.view = view;
    }

    public void loadServiceList() {
        repository.getInfoListEx()
                .compose(RxUtil.<List<ServiceTypeEx>>applySchedulers())
                .subscribe(new OldBaseObserver<List<ServiceTypeEx>>(view) {
                    @Override
                    public void onNext(List<ServiceTypeEx> serviceTypeExList) {
                        ServiceListPresenter.this.view.render(serviceTypeExList);
                    }
                });
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
    }
}
