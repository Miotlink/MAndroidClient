package com.homepaas.sls.mvp.presenter.newdetail;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.GetAtStoreActivityEntity;
import com.homepaas.sls.domain.repository.GetAtStoreActivityRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.newdetail.GetAtStoreActivityView;

import javax.inject.Inject;


/**
 * Created by JWC on 2017/2/23.
 */
@ActivityScope
public class PayinStoreActivityPresent implements Presenter {

    private GetAtStoreActivityView getAtStoreActivityView;

    public  void setGetAtStoreActivityView(GetAtStoreActivityView getAtStoreActivityView){
        this.getAtStoreActivityView=getAtStoreActivityView;
    }

    @Inject
    public PayinStoreActivityPresent() {
    }
    @Inject
    GetAtStoreActivityRepo getAtStoreActivityRepo;


    public void getGetAtStoreActivity(String merchantId){
        getAtStoreActivityRepo.getGetAtStoreActivity(merchantId)
                .compose(RxUtil.<GetAtStoreActivityEntity>applySchedulers())
                .subscribe(new OldBaseObserver<GetAtStoreActivityEntity>(getAtStoreActivityView) {
                    @Override
                    public void onNext(GetAtStoreActivityEntity getAtStoreActivityEntity) {
                        getAtStoreActivityView.render(getAtStoreActivityEntity);
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
