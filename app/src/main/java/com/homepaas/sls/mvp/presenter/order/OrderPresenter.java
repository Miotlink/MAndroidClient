package com.homepaas.sls.mvp.presenter.order;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.homepaas.sls.data.network.dataformat.OrderDetailBody;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.OrderInfo;
import com.homepaas.sls.domain.entity.TrackOrderInfo;
import com.homepaas.sls.domain.interactor.CancelOrderInteractor;
import com.homepaas.sls.domain.interactor.CompleteOrderInteractor;
import com.homepaas.sls.domain.interactor.DeleteOrderInteractor;
import com.homepaas.sls.domain.interactor.GetAllOrderInteractor;
import com.homepaas.sls.domain.interactor.GetOrderToConfirmInteractor;
import com.homepaas.sls.domain.interactor.GetOrderToEvaluateInteractor;
import com.homepaas.sls.domain.interactor.GetOrderToPayInteractor;
import com.homepaas.sls.domain.interactor.GetToTakeOrderInteractor;
import com.homepaas.sls.domain.repository.GetOrderListRepo;
import com.homepaas.sls.domain.repository.TrackOrderStatusRepo;
import com.homepaas.sls.event.ConfirmCompletedEvent;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.order.GetOrderView;
import com.homepaas.sls.mvp.view.order.OrderActionView;
import com.homepaas.sls.mvp.view.order.OrderCancelView;
import com.homepaas.sls.mvp.view.order.OrderConfirmView;
import com.homepaas.sls.mvp.view.order.OrderDetailView;
import com.homepaas.sls.newmvp.contract.OrderContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/5/3.
 * 此Presenter用于获取订单列表信息，我的订单，未支付，待确定订单列表,订单详情
 */
@ActivityScope
public class OrderPresenter implements Presenter {

    private static final String TAG = "OrderPresenter";

    @Inject
    GetAllOrderInteractor getOrderInteractor;
    @Inject
    GetOrderToConfirmInteractor getOrderToConfirmInteractor;
    @Inject
    GetOrderToPayInteractor getOrderToPayInteractor;
    @Inject
    GetOrderToEvaluateInteractor getOrderToEvaluateInteractor;//获取待评价订单
    @Inject
    DeleteOrderInteractor deleteOrderInteractor;//删除订单
    //    @Inject
//    GetOrderDetailInteractor getOrderDetailInteractor;//订单详情
    @Inject
    CancelOrderInteractor cancelOrderInteractor;//取消订单
    @Inject
    CompleteOrderInteractor completeOrderInteractor;
    @Inject
    GetToTakeOrderInteractor getToTakeOrderInteractor;
    @Inject
    TrackOrderStatusRepo trackOrderStatusRepo;

    private OldBaseObserver<TrackOrderInfo> oldBaseObserver;
    private GetOrderView getOrderView;
    private OrderDetailView orderDetailView;
    private OrderActionView orderActionView;
    private OrderConfirmView orderConfirmView;
    private OrderCancelView orderCancelView;
    private boolean everLoaded;
    private OrderContract.Model orderModel = ModelFactory.getInstance().getOrderModel();


    @Inject
    public OrderPresenter() {

    }

    public void setGetOrderView(GetOrderView getOrderView) {
        this.getOrderView = getOrderView;
    }

    public void setOrderDetailView(OrderDetailView orderDetailView) {
        this.orderDetailView = orderDetailView;
    }

    public void setOrderActionView(OrderActionView orderActionView) {
        this.orderActionView = orderActionView;
    }

    public void setOrderConfirmView(OrderConfirmView orderConfirmView) {
        this.orderConfirmView = orderConfirmView;
    }


    /**
     * @param orderId  。。
     * @param position 对于订单列表的订单可能需要订单item的位置
     */
    public void cancelOrder(String orderId, final int position, String cancelReason) {
        cancelOrderInteractor.setorderId(orderId, cancelReason);
        cancelOrderInteractor.execute(new OldBaseObserver<String>(orderActionView) {
            @Override
            public void onNext(String s) {
                String[] split = s.split(" ");
                if (TextUtils.equals("0", split[1]))
                    orderActionView.onOrderCancel("订单取消成功", position);
                else orderActionView.showMessage(split[0]);
            }
        });
    }


    /**
     * 确认订单
     */
    public void confirmOrder(final ConfirmCompletedEvent completedEvent) {
        orderActionView.showLoading();
        completeOrderInteractor.setOrderId(completedEvent.getOrderID());
        completeOrderInteractor.execute(new OldBaseObserver<String>(orderActionView) {
            @Override
            public void onNext(String s) {
                orderActionView.onOrderConfirm(completedEvent.getOrderCode(), -1);
            }
        });
    }

    /**
     * 删除订单
     */
    public void deleteOrder(String orderId, @Nullable final int position) {
        orderActionView.showLoading(true);
        deleteOrderInteractor.setOrderId(orderId);
        deleteOrderInteractor.execute(new OldBaseObserver<String>(orderActionView) {
            @Override
            public void onNext(String s) {
                orderActionView.hideLoading();
                if (TextUtils.equals("0", s))
                    orderActionView.onOrderDelete("订单删除成功", position);
            }
        });

    }

    private long startTime = 0;

    /**
     * 获取订单详情
     *
     * @param orderId
     */
    public void getOrderDetail(final String orderId) {
        orderDetailView.showLoading(true);
//        getOrderDetailInteractor.setOrderId(orderId);
        startTime = System.currentTimeMillis();

        orderModel.getOrderDetail(orderDetailView, orderId, new RetrofitRequestCallBack<OrderDetailBody>() {
            @Override
            public void successRequest(OrderDetailBody data) {
                orderDetailView.render(data.getDetailOrder(), startTime);
            }
        });
//        getOrderDetailInteractor.execute(new OldBaseObserver<DetailOrderEntity>(orderDetailView) {
//            @Override
//            public void onNext(DetailOrderEntity orderInfo) {
//                orderDetailView.render(orderInfo, startTime);
//            }
//        });
    }

    /**
     * 订单追踪信息
     *
     * @param orderId
     */
    public void getTrackOrderStatus(String orderId) {
        orderDetailView.showLoading(true);
        oldBaseObserver = new OldBaseObserver<TrackOrderInfo>(orderDetailView) {
            @Override
            public void onNext(TrackOrderInfo trackOrderInfo) {
                orderDetailView.renderTrackOrderStatus(trackOrderInfo);
            }
        };
        trackOrderStatusRepo.getTrackOrderStatus(orderId)
                .compose(RxUtil.<TrackOrderInfo>applySchedulers())
                .subscribe(oldBaseObserver);


    }

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
        if (oldBaseObserver != null)
            oldBaseObserver.unsubscribe();
        getOrderInteractor.unsubscribe();
        getOrderToConfirmInteractor.unsubscribe();
        getOrderToPayInteractor.unsubscribe();
        getOrderToEvaluateInteractor.unsubscribe();
        deleteOrderInteractor.unsubscribe();
//        getOrderDetailInteractor.unsubscribe();
        cancelOrderInteractor.unsubscribe();
        completeOrderInteractor.unsubscribe();
        getToTakeOrderInteractor.unsubscribe();
    }

    /**
     * 分页加载
     */
    @Inject
    GetOrderListRepo getOrderListRepo;

    private int allCurrentIndex = 1;       //全部当前index
    private int toTakeCurrentIndex = 1;  //待接单当前index
    private int toPayCurrentIndex = 1;  //待接单当前index
    private int toConfirmCurrentIndex = 1; //待确定当前index
    private int toEvaluationCurrentIndex = 1; //待评价当前的index
    private int toCurrentIndex = 1; //当前订单当前index
    private int toHistoryIndex = 1; //历史订单当前的index

    /**
     * @param type
     * @param isShowCommonDialog 是否显示公共加载，刷新时不需要显示
     */
    public void getOrderList(String type, boolean isShowCommonDialog) {
        if (isShowCommonDialog)
            getOrderView.showLoading(true);
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
        } else if (TextUtils.equals("6", type))//当前订单
        {
            toCurrentIndex = 1;
        } else if (TextUtils.equals("7", type))//历史订单
        {
            toHistoryIndex = 1;
        }
        final String finalType = type;
        getOrderListRepo.getOrderList(StaticData.PAGE_SIZE, "1", type)
                .compose(RxUtil.<OrderInfo>applySchedulers())
                .subscribe(new OldBaseObserver<OrderInfo>(getOrderView) {
                    @Override
                    public void onNext(OrderInfo orderInfo) {
                        if (orderInfo != null) {

                            if (orderInfo.getOrders().size() > 0) {
                                if (TextUtils.equals("0", finalType)) {//全部
                                    PreferencesUtil.saveObject(StaticData.ALL_ORDER_LIST, OrderInfo.class);
                                } else if (TextUtils.equals("1", finalType)) {//待支付
                                    PreferencesUtil.saveObject(StaticData.OBLIGATION_LIST, OrderInfo.class);
                                } else if (TextUtils.equals("2", finalType)) {//待确认
                                    PreferencesUtil.saveObject(StaticData.TBC_LIST, OrderInfo.class);
                                } else if (TextUtils.equals("3", finalType)) {//待评价
                                    PreferencesUtil.saveObject(StaticData.NO_EVALUATE, OrderInfo.class);
                                } else if (TextUtils.equals("4", finalType)) {//待接单
                                    PreferencesUtil.saveObject(StaticData.WAITING_LIST, OrderInfo.class);
                                }
                            }
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
        } else if (TextUtils.equals("6", type))//当前订单
        {
            toCurrentIndex  = toCurrentIndex + 1;
            currentIndex = toCurrentIndex;
        } else if (TextUtils.equals("7", type))//历史订单
        {
            toHistoryIndex = toHistoryIndex + 1;
            currentIndex = toHistoryIndex;
        }
        if (currentIndex != 0) {
            getOrderListRepo.getOrderList(StaticData.PAGE_SIZE, currentIndex + "", type)
                    .compose(RxUtil.<OrderInfo>applySchedulers())
                    .subscribe(new OldBaseObserver<OrderInfo>(getOrderView) {
                        @Override
                        public void onNext(OrderInfo orderInfo) {
                            if (orderInfo != null) {
                                getOrderView.renderMore(orderInfo.getOrders());
                            }
                        }
                    });
        }
    }
}
