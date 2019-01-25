package com.homepaas.sls.newmvp.presenter;

import com.homepaas.sls.domain.entity.OrderCancelReasonEntity;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.IBasePresenter;
import com.homepaas.sls.newmvp.contract.TestContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;

/**
 * on 2017/6/4.
 */

public class HomePresenter extends IBasePresenter<TestContract.View> implements TestContract.Presenter {

    private TestContract.Model iHomeModel = ModelFactory.getInstance().getiTestModel();

    @Override
    public void getData() {
        iHomeModel.modelGetData( mView, new RetrofitRequestCallBack<OrderCancelReasonEntity>() {
            @Override
            public void successRequest(OrderCancelReasonEntity orderCancelReasonEntity) {
                mView.initDataSuccess(orderCancelReasonEntity.getCancelReasonList().get(0).toString());//回传view
            }
        });
    }

    @Override
    public void dispose() {
        if (iHomeModel != null)
            iHomeModel.dispose();
    }
}
