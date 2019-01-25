package com.homepaas.sls.mvp.presenter.addressmanage;


import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.AddressEntity;
import com.homepaas.sls.domain.interactor.AddServiceAddressInteractor;
import com.homepaas.sls.domain.interactor.DeleteServiceAddressInteractor;
import com.homepaas.sls.domain.interactor.GetServiceAddressInteractor;
import com.homepaas.sls.domain.interactor.UpdateServiceAddressInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.addressmanage.AddAddressView;
import com.homepaas.sls.mvp.view.addressmanage.ManageAddressView;
import com.homepaas.sls.mvp.view.addressmanage.UpdateServiceAddressView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by CJJ on 2016/9/13.
 */
@ActivityScope
public class AddressPresenter implements Presenter {


    @Inject
    public AddressPresenter() {

    }

    ManageAddressView addressView;
    UpdateServiceAddressView updateServiceAddressView;
    AddAddressView addAddressView;

    public void setAddAddressView(AddAddressView addAddressView) {
        this.addAddressView = addAddressView;
    }

    public void setUpdateServiceAddressView(UpdateServiceAddressView updateServiceAddressView) {
        this.updateServiceAddressView = updateServiceAddressView;
    }

    public void setAddressView(ManageAddressView addressView) {
        this.addressView = addressView;
    }

    @Inject
    GetServiceAddressInteractor getServiceAddressInteractor;
    @Inject
    DeleteServiceAddressInteractor deleteServiceAddressInteractor;
    @Inject
    AddServiceAddressInteractor appendServiceAddressInteractor;
    @Inject
    UpdateServiceAddressInteractor updateServiceAddressInteractor;

    public void getMyServiceAddressList() {
        getServiceAddressInteractor.execute(new OldBaseObserver<List<AddressEntity>>(addressView) {
            @Override
            public void onNext(List<AddressEntity> addressEntities) {

                addressView.renderAddress(addressEntities);
            }
        });
    }

    /**
     * 删除我的服务地址
     *
     * @param id
     */
    public void deleteServiceAddress(String id, final int delIndex) {
        deleteServiceAddressInteractor.setId(id);
        deleteServiceAddressInteractor.execute(new OldBaseObserver<String>(addressView) {
            @Override
            public void onNext(String s) {
                if (s != null) {
                    addressView.showMessage("删除成功");
                    addressView.deleteSuccess(delIndex);
                } else addressView.showMessage("地址删除失败，请检查您的网络后重试");
            }
        });
    }

    public void addServiceAddress(final AddressEntity entity) {

        appendServiceAddressInteractor.setParam(entity);
        appendServiceAddressInteractor.execute(new OldBaseObserver<String>(updateServiceAddressView) {
            @Override
            public void onNext(String s) {
                entity.setId(s);
                updateServiceAddressView.onUpdateServiceAddressSuccess(entity);
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
        getServiceAddressInteractor.unsubscribe();
        deleteServiceAddressInteractor.unsubscribe();
        appendServiceAddressInteractor.unsubscribe();
        updateServiceAddressInteractor.unsubscribe();
    }

    /**
     * 更新服务地址
     *
     * @param addressEntity
     */
    public void updateServiceAddress(final AddressEntity addressEntity) {
        updateServiceAddressView.showLoading();
        updateServiceAddressInteractor.setParam(addressEntity);
        updateServiceAddressInteractor.execute(new OldBaseObserver<String>(updateServiceAddressView) {
            @Override
            public void onNext(String s) {
                updateServiceAddressView.onUpdateServiceAddressSuccess(addressEntity);
            }
        });
    }
}
