package com.homepaas.sls.newmvp.presenter;

import com.homepaas.sls.newmvp.base.IBasePresenter;
import com.homepaas.sls.newmvp.contract.HistoryContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;

/**
 * on 2017/6/4.
 */

public class HistoryPresenter extends IBasePresenter<HistoryContract.View> implements HistoryContract.Presenter {

    private HistoryContract.Model iHomeModel = ModelFactory.getInstance().getHistoryModel();

    public HistoryPresenter(HistoryContract.View view) {
        bindView(view);
    }

    @Override
    public void dispose() {
        if (iHomeModel != null)
            iHomeModel.dispose();
    }

    @Override
    public void getHistoryOrder() {
//        iHomeModel.modelGetData( mView, new RetrofitRequestCallBack<OrderCancelReasonEntity>() {
//            @Override
//            public void successRequest(OrderCancelReasonEntity orderCancelReasonEntity) {
//                mView.initDataSuccess(orderCancelReasonEntity.getCancelReasonList().get(0).toString());//回传view
//            }
//        });
    }
}
