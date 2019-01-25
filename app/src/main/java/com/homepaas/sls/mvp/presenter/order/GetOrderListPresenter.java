package com.homepaas.sls.mvp.presenter.order;

import android.text.TextUtils;

import com.homepaas.sls.domain.entity.OrderInfo;
import com.homepaas.sls.domain.repository.GetOrderListRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.order.GetOrderView;
import com.homepaas.sls.util.StaticData;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/7/21.
 */

public class GetOrderListPresenter implements Presenter {
    private GetOrderView getOrderView;
    public void setGetOrderView(GetOrderView getOrderView){
        this.getOrderView=getOrderView;
    }

    @Inject
    public GetOrderListPresenter() {
    }
    @Inject
    GetOrderListRepo getOrderListRepo;

    private int allCurrentIndex = 1;       //全部当前index
    private int toTakeCurrentIndex = 1;  //待接单当前index
    private int toPayCurrentIndex = 1;  //待接单当前index
    private int toConfirmCurrentIndex = 1; //待确定当前index
    private int toEvaluationCurrentIndex = 1; //待评价当前的index

    public void getOrderList(String type) {
        if (TextUtils.equals("0", type)) {//全部
            allCurrentIndex = 1;
        } else if (TextUtils.equals("1", type)) {//待支付
            toPayCurrentIndex = 1;
        } else if (TextUtils.equals("2", type)) {//待确认
            toConfirmCurrentIndex = 1;
        } else if (TextUtils.equals("3", type)) {//待评价
            toEvaluationCurrentIndex = 1;
        } else if (TextUtils.equals("4", type)) {//待接单
            toTakeCurrentIndex = 1;
        }
        getOrderListRepo.getOrderList(StaticData.PAGE_SIZE, "1", type)
              .compose(RxUtil.<OrderInfo>applySchedulers())
                .subscribe(new OldBaseObserver<OrderInfo>(getOrderView) {
                    @Override
                    public void onNext(OrderInfo orderInfo) {
                        if(orderInfo!=null) {
                            getOrderView.render(orderInfo.getOrders());
                        }
                    }
                });
    }

    public void getMoreOrderList(String type) {
        int currentIndex = 0;
        if (TextUtils.equals("0", type)) {//全部
            allCurrentIndex = allCurrentIndex + 1;
            currentIndex = allCurrentIndex;
        } else if (TextUtils.equals("1", type)) {//待支付
            toPayCurrentIndex = toPayCurrentIndex + 1;
            currentIndex = toPayCurrentIndex;
        } else if (TextUtils.equals("2", type)) {//待确认
            toConfirmCurrentIndex = toConfirmCurrentIndex + 1;
            currentIndex = toConfirmCurrentIndex;
        } else if (TextUtils.equals("3", type)) {//待评价
            toEvaluationCurrentIndex = toEvaluationCurrentIndex + 1;
            currentIndex = toEvaluationCurrentIndex;
        } else if (TextUtils.equals("4", type)) {//待接单
            toTakeCurrentIndex = toTakeCurrentIndex + 1;
            currentIndex = toTakeCurrentIndex;
        }
        if(currentIndex!=0) {
            getOrderListRepo.getOrderList(StaticData.PAGE_SIZE, currentIndex + "", type)
                   .compose(RxUtil.<OrderInfo>applySchedulers())
                    .subscribe(new OldBaseObserver<OrderInfo>(getOrderView) {
                        @Override
                        public void onNext(OrderInfo orderInfo) {
                            if(orderInfo!=null){
                                getOrderView.renderMore(orderInfo.getOrders());
                            }
                        }
                    });
        }
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
