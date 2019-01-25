package com.homepaas.sls.domain.repository;
import com.homepaas.sls.domain.entity.DetailOrder;
import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.Order;
import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.domain.entity.OrderInfo;
import com.homepaas.sls.domain.entity.OrderPayAliSign;
import com.homepaas.sls.domain.entity.OrderPayWXSign;
import com.homepaas.sls.domain.entity.WxSign;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.exception.GetPaySignException;
import com.homepaas.sls.domain.param.DirectOrderParam;
import com.homepaas.sls.domain.param.GetOrderPayAliSignParams;
import com.homepaas.sls.domain.param.GetOrderpayWxSignParams;
import com.homepaas.sls.domain.param.OrderCreateParams;

import java.util.List;


/**
 * Created by Administrator on 2016/4/28.
 *
 */
public interface OrderInfoRepo {
    
    OrderInfo getOrder(String pageSize,String pageIndex,String type) throws AuthException;
    String createOrder(OrderCreateParams params) throws AuthException;
    //所有订单
    OrderInfo getAllOrder(boolean fresh) throws InterruptedException, AuthException;
    List<OrderEntity> getToPayOrder() throws AuthException;
    //确认订单
    List<OrderEntity> getToConfirmOrder() throws AuthException;
    List<OrderEntity> getToEvaluateOrder() throws AuthException, InterruptedException;
    //订单详情
    DetailOrderEntity getDetailOrder(String orderId) throws AuthException;
    String cancelOrder(String orderId,String cancelReason) throws AuthException;
    String confirmOrder(String orderId) throws Exception;

    OrderPayAliSign getOrderPayAliSign(GetOrderPayAliSignParams params) throws GetPaySignException;
    OrderPayWXSign  getOrderPayWxSign(GetOrderpayWxSignParams params) throws GetPaySignException;

    String deleteOrder(String orderId);

    String payOrderByBalance(String orderId, String couponId, String balanceMoney);
    String getDirectPayId(String receiverId,String receiverType,String paySource,String TotalMoney,String ServiceTypeID);


    WxSign getDirectPayWXSign(String directPayId, String balancePayMoney);

    String getDirectPayAliSign(String directPayId, String balancePayMoney);

    String directBalancePay(String id);

    String placeDirectOrder(DirectOrderParam param) throws AuthException;

    List<OrderEntity> getToTakeOrder() throws AuthException;
}
