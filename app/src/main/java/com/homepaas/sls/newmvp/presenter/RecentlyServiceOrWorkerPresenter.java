package com.homepaas.sls.newmvp.presenter;

import com.homepaas.sls.domain.entity.SelectServiceOrWorkerEntity;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.IBasePresenter;
import com.homepaas.sls.newmvp.contract.RecentlyServiceOrWorkerContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;

/**
 * on 2017/6/4.
 */

public class RecentlyServiceOrWorkerPresenter extends IBasePresenter<RecentlyServiceOrWorkerContract.View> implements RecentlyServiceOrWorkerContract.Presenter {

    private RecentlyServiceOrWorkerContract.Model recentlyServiceOrWorkerModel = ModelFactory.getInstance().getRecentlyServiceOrWorkerModel();


    @Override
    public void dispose() {
        if (recentlyServiceOrWorkerModel != null)
            recentlyServiceOrWorkerModel.dispose();
    }


    @Override
    public void getListData(String tabType, String serviceId, String addressId, String isEnablePaging, String pageIndex, String pageSize) {
        mView.showLoading(true);
        recentlyServiceOrWorkerModel.getListData(mView, tabType, serviceId, addressId, isEnablePaging, pageIndex, pageSize, new RetrofitRequestCallBack<SelectServiceOrWorkerEntity>() {
                    @Override
                    public void successRequest(SelectServiceOrWorkerEntity data) {
                        mView.initListData(data);
                    }
                }
        );
    }
}
