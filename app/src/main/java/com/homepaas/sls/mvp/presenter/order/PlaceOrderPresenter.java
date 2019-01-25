package com.homepaas.sls.mvp.presenter.order;

import android.util.Log;

import com.google.gson.Gson;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.interactor.PlaceDirectInteractor;
import com.homepaas.sls.domain.interactor.PlaceOrderInteractor;
import com.homepaas.sls.domain.param.DirectOrderParam;
import com.homepaas.sls.domain.param.OrderCreateParams;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.order.OrderPlaceView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/4/28.
 * 用于下单的Presenter
 */
@ActivityScope
public class PlaceOrderPresenter implements Presenter {


    private OrderPlaceView orderPlaceView;

    @Inject
    PlaceOrderInteractor placeOrderInteractor;
    @Inject
    PlaceDirectInteractor placeDirectInteractor;

    @Inject
    public PlaceOrderPresenter() {
    }

    public void setOrderPlaceView(OrderPlaceView orderPlaceView) {
        this.orderPlaceView = orderPlaceView;
    }

    /**
     * 指定下单
     *
     * @param filePaths
     * @param objectType
     * @param objectId
     * @param serviceTypeId
     * @param serviceContent
     * @param orderForm
     * @param serviceNumber
     */
    public void placeOrder(List<String> filePaths, String objectType, String objectId, String serviceTypeId, String serviceContent, String serviceNumber, String orderForm, String serviceTime, String addressId) {
        currentserviceTypeId = serviceTypeId;
        OrderCreateParams params;
        params = new OrderCreateParams(filePaths, objectType, objectId, serviceTypeId, serviceContent, serviceNumber, orderForm, serviceTime, addressId);

        placeOrderInteractor.setParams(params);
        orderPlaceView.showLoading();
        placeOrderInteractor.execute(new OldBaseObserver<String>(orderPlaceView) {
            @Override
            public void onNext(String orderId) {
                orderPlaceView.onOrderCreate(orderId,currentserviceTypeId);
            }
        });
    }

    private String currentserviceTypeId;
    public void placeDirectOrder(String serviceTypeId, String serviceContent, List<String> imgPath, String addressId, String serviceTime, String serviceNumber, String servicePrice) {
       currentserviceTypeId = serviceTypeId;
        DirectOrderParam param = new DirectOrderParam();
        param.setPhotoPaths(imgPath);//图片地址
        param.setServiceAddressId(addressId);//服务地址
        param.setServiceTypeId(serviceTypeId);//服务类型
        param.setServiceTime(serviceTime);//服务时间
        param.setServiceContent(serviceContent);//服务内容
        param.setServiceNumber(serviceNumber);//服务数量
        param.setServicePrice(servicePrice);
        Gson gson = new Gson();
        String rr = gson.toJson(param);
        Log.d("rrrrrrrr",rr);
        placeDirectInteractor.setParam(param);
        orderPlaceView.showLoading();
        placeDirectInteractor.execute(new OldBaseObserver<String>(orderPlaceView) {
            @Override
            public void onNext(String s) {
                orderPlaceView.onOrderCreate(s,currentserviceTypeId);
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
        placeDirectInteractor.unsubscribe();
        placeOrderInteractor.unsubscribe();
    }
}
