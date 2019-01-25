package com.homepaas.sls.mvp.presenter;

import com.homepaas.sls.data.entity.OrderListPopEntity;
import com.homepaas.sls.domain.interactor.GetUnreadMessageCountInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.mvp.view.UnReadMessageCountView;
import com.homepaas.sls.newmvp.contract.OrderContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;

import javax.inject.Inject;

/**
 * Created by CJJ on 2016/9/9.
 */
//@ActivityScope
public class MessagePresenter implements Presenter {

    @Inject
    public MessagePresenter() {
    }

    UnReadMessageCountView unReadMessageCountView;

    public void setUnReadMessageCountView(UnReadMessageCountView unReadMessageCountView) {
        this.unReadMessageCountView = unReadMessageCountView;
    }

    @Inject
    GetUnreadMessageCountInteractor getUnreadMessageCountInteractor;

    private OrderContract.Model orderModel = ModelFactory.getInstance().getOrderModel();

    public void getOrderListPop() {
        orderModel.getOrderListPop(unReadMessageCountView, new RetrofitRequestCallBack<OrderListPopEntity>() {
            @Override
            public void successRequest(OrderListPopEntity data) {
                unReadMessageCountView.initOrderListPop(data);
            }
        });
    }

    public void getUnreadMessageCount() {
        getUnreadMessageCountInteractor.execute(new OldBaseObserver<Integer>(unReadMessageCountView) {
            @Override
            public void onNext(Integer integer) {
                unReadMessageCountView.renderUnreadMsgCount(integer > 0);
            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
            }
        });
    }

//    http://copen.homepaas.com/api/v3/

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        if (orderModel != null)
            orderModel.dispose();
        getUnreadMessageCountInteractor.unsubscribe();
    }
}
