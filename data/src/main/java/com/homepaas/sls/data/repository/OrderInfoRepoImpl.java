package com.homepaas.sls.data.repository;

import com.homepaas.sls.domain.exception.GetPaySignException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.repository.datasource.OrderInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.domain.entity.OrderInfo;
import com.homepaas.sls.domain.entity.OrderPayAliSign;
import com.homepaas.sls.domain.entity.OrderPayWXSign;
import com.homepaas.sls.domain.entity.WxSign;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.domain.param.DirectOrderParam;
import com.homepaas.sls.domain.param.GetOrderPayAliSignParams;
import com.homepaas.sls.domain.param.GetOrderpayWxSignParams;
import com.homepaas.sls.domain.param.OrderCreateParams;
import com.homepaas.sls.domain.repository.OrderInfoRepo;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/4/28.
 */
@Singleton
public class OrderInfoRepoImpl implements OrderInfoRepo {
    private static final String TAG = "OrderInfoRepoImpl";
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    OrderInfoNDataSource orderInfoNDataSource;
    List<OrderEntity> NULL_SAFE_LIST = new ArrayList<>(0);
    @Inject
    public OrderInfoRepoImpl() {
    }


    @Override
    public OrderInfo getOrder(String pageSize,String pageIndex,String type) throws AuthException {
        return orderInfoNDataSource.getOrder(pageSize,pageIndex,type);
    }

    @Override
    public String createOrder(OrderCreateParams params) throws AuthException {
        try {
            String orderCode = orderInfoNDataSource.createOrder(params);
            return orderCode;
        } catch (GetPersistenceDataException | ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(),e);
        }  catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取订单管理中的所有订单列表信息
     *
     * @return
     */

    @Override
    public OrderInfo getAllOrder(boolean fresh) throws InterruptedException, AuthException {
//        if (allOrder != null) return allOrder;
////        List<Order> pallOrder = null;
        OrderInfo allOrder = orderInfoNDataSource.getOrder(null, null, Constant.ORDER_LIST_ALL);
        return allOrder==null?new OrderInfo(NULL_SAFE_LIST):allOrder;
    }

    @Override
    public List<OrderEntity> getToPayOrder() throws AuthException {
        OrderInfo order = getOrder(null, null, Constant.ORDER_LIST_TOPAY);
        List<OrderEntity> orders = order.getOrders();
        return orders==null? NULL_SAFE_LIST :orders;
    }

    @Override
    public List<OrderEntity> getToConfirmOrder() throws AuthException {
        List<OrderEntity> orders = getOrder(null,null,Constant.ORDER_LIST_TOCONFIRM).getOrders();
        return orders==null? NULL_SAFE_LIST :orders;
    }

    @Override
    public List<OrderEntity> getToEvaluateOrder() throws AuthException, InterruptedException {

        List<OrderEntity> orders = getOrder(null,null,Constant.ORDER_LIST_TOEVALUATE).getOrders();
        return orders==null? NULL_SAFE_LIST :orders;
    }


    public DetailOrderEntity getDetailOrder(String orderId) throws AuthException {

        String token;
        try {
            token = personalInfoPDataSource.getToken();
            return orderInfoNDataSource.getOrderDetail(token,orderId);
        } catch (GetPersistenceDataException | IOException | ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(),e);
        }
    }

    @Override
    public String cancelOrder(String orderId,String cancelReason) throws AuthException {
        String token ;
        String result ;
        try {
             token = personalInfoPDataSource.getToken();
             result = orderInfoNDataSource.cancelOrder(token, orderId,cancelReason);
            return result;
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String confirmOrder(String orderId) throws Exception {
        String token ;
        String result ;
        try {
            token = personalInfoPDataSource.getToken();
            result = orderInfoNDataSource.confirmOrder(token, orderId);
            return result;
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderPayAliSign getOrderPayAliSign(GetOrderPayAliSignParams params) throws GetPaySignException {
        try {
            String token = personalInfoPDataSource.getToken();
            OrderPayAliSign orderPayAliSign = orderInfoNDataSource.getOrderPayAliSign(token, params);
            return orderPayAliSign;
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderPayWXSign getOrderPayWxSign(GetOrderpayWxSignParams params) throws GetPaySignException {
        String token  = null;
        try {
            token = personalInfoPDataSource.getToken();
            OrderPayWXSign orderPayWXSign = orderInfoNDataSource.getOrderPayWXSign(token,params);
            return orderPayWXSign;
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteOrder(String orderId) {
        String token  = null;

        try {
            token = personalInfoPDataSource.getToken();
            String s = orderInfoNDataSource.deleteOrder(token,orderId);
            return s;
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String payOrderByBalance(String orderId,String couponId, String balanceMoney) {
        String token  = null;

        try {
            token = personalInfoPDataSource.getToken();
            String s = orderInfoNDataSource.payOrderByBalance(token,orderId,couponId,balanceMoney);
            return s;
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getDirectPayId(String receiverId, String receiverType, String paySource, String totalMoney, String ServiceTypeID) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            String directPayId = orderInfoNDataSource.getDirectPayId(token, receiverId, receiverType, paySource, totalMoney, ServiceTypeID);
            return directPayId;
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public WxSign getDirectPayWXSign(String directPayId, String balancePayMoney) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            WxSign wxSign = orderInfoNDataSource.getDirectPayWXSign(token,directPayId,balancePayMoney);
            return wxSign;
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getDirectPayAliSign(String directPayId, String balancePayMoney) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            String sign = orderInfoNDataSource.getDirectPayAliSign(token, directPayId, balancePayMoney);
            return sign;
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String directBalancePay(String directPayId) {
        String token = null;
        try {
            token = personalInfoPDataSource.getToken();
            String s = orderInfoNDataSource.directBalancePay(token, directPayId);
            return s;
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String placeDirectOrder(DirectOrderParam param) throws AuthException {
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            param.setToken(token);
            return orderInfoNDataSource.placeDirectOrder(param);
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderEntity> getToTakeOrder() throws AuthException {
        List<OrderEntity> orders = getOrder(null,null,Constant.ORDER_LIST_TOTAKE).getOrders();
        return orders==null? NULL_SAFE_LIST :orders;
    }


}
