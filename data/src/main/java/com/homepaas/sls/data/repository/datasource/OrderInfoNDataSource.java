package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.domain.exception.GetPaySignException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.OrderInfo;
import com.homepaas.sls.domain.entity.OrderPayAliSign;
import com.homepaas.sls.domain.entity.OrderPayWXSign;
import com.homepaas.sls.domain.entity.WxSign;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.param.DirectOrderParam;
import com.homepaas.sls.domain.param.GetOrderPayAliSignParams;
import com.homepaas.sls.domain.param.GetOrderpayWxSignParams;
import com.homepaas.sls.domain.param.OrderCreateParams;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Administrator on 2016/4/28.
 */
public interface OrderInfoNDataSource {

    String createOrder(OrderCreateParams params) throws GetPersistenceDataException, ResponseMetaAuthException, IOException, JSONException, AuthException;
    OrderInfo getAllOrder(boolean fresh) throws GetPersistenceDataException, IOException, ResponseMetaAuthException;
    DetailOrderEntity getOrderDetail(String token, String orderId) throws GetPersistenceDataException, IOException, ResponseMetaAuthException, AuthException;

    OrderPayAliSign getOrderPayAliSign(String token, GetOrderPayAliSignParams params) throws GetPaySignException;

    OrderPayWXSign getOrderPayWXSign(String token, GetOrderpayWxSignParams params) throws GetPaySignException;

    String cancelOrder(String token, String orderId,String cancelReason) throws AuthException;

    String deleteOrder(String token, String orderId);

    String confirmOrder(String token, String orderId) throws Exception;

    String payOrderByBalance(String token, String orderId, String couponId, String balanceMoney);

    String getDirectPayId(String token, String receiverId, String receiverType, String paySource, String totalMoney, String serviceTypeID);

    WxSign getDirectPayWXSign(String token, String directPayId, String wxPayMoney);

    String getDirectPayAliSign(String token, String directPayId, String balancePayMoney);

    String directBalancePay(String token, String directPayId);

    OrderInfo getOrder(String pageSize,String pageIndex,String type) throws AuthException;

    String placeDirectOrder(DirectOrderParam param) throws AuthException;
}
