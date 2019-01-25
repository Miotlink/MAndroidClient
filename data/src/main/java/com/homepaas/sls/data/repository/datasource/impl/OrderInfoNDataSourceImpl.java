package com.homepaas.sls.data.repository.datasource.impl;

import android.util.Log;

import com.homepaas.sls.data.BuildConfig;
import com.homepaas.sls.data.entity.mapper.OrderEntityMapper;
import com.homepaas.sls.data.entity.mapper.OrderMapper;
import com.homepaas.sls.domain.exception.GetPaySignException;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.exception.ResponseMetaAuthException;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.network.dataformat.AlipaySignBody;
import com.homepaas.sls.data.network.dataformat.AllOrderBody;
import com.homepaas.sls.data.network.dataformat.CreatedOrderBody;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.OrderDetailBody;
import com.homepaas.sls.data.network.dataformat.OrderPayAliSignBody;
import com.homepaas.sls.data.network.dataformat.OrderPayWXSignBody;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.request.CreateOrderRequest;
import com.homepaas.sls.data.repository.datasource.OrderInfoNDataSource;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.DirectPayInfo;
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
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.Response;

/**
 * Created by Administrator on 2016/4/28.
 */
@Singleton
public class OrderInfoNDataSourceImpl implements OrderInfoNDataSource {

    private static final String TAG = "OrderInfoNDataSourceImp";
    @Inject
    RestApiHelper apiHelper;

    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;

    @Inject
    OrderMapper orderMapper;
    @Inject
    OrderEntityMapper detailmapper;
    private static OrderInfo orderInfo;

    @Inject
    public OrderInfoNDataSourceImpl() {
    }

    /**
     * 创建订单（下单）
     *
     * @param orderCreateParams
     * @return
     * @throws GetPersistenceDataException
     * @throws IOException
     * @throws JSONException
     * @throws ResponseMetaAuthException
     */
    @Override
    public String createOrder(OrderCreateParams orderCreateParams) throws AuthException, JSONException {
        String orderId = null;
        try {
            String token = null;
            token = personalInfoPDataSource.getToken();
            CreateOrderRequest orderRequest = new CreateOrderRequest();
            orderRequest.setFilePath(orderCreateParams.getFilePaths());
            String objectType = orderCreateParams.getObjectType();
            String objectId = orderCreateParams.getObjectId();
            String serviceTypeId = orderCreateParams.getServiceTypeId();
            String serviceContent = orderCreateParams.getServiceContent();
//            String servicePrice = orderCreateParams.getServicePrice();
            String orderForm = orderCreateParams.getOrderForm();
            List<String> filePaths = orderCreateParams.getFilePaths();
            String addressId = orderCreateParams.getAddressId();
            String serviceNumber = orderCreateParams.getServiceNumber();
            String serviceTime = orderCreateParams.getServiceTime();
            orderRequest.setJsonParams(new CreateOrderRequest.JSONDATA(token, objectType, objectId, serviceTypeId, serviceContent, orderForm,serviceNumber,serviceTime,addressId));
            orderRequest.setFilePath(filePaths);
            Response<ResponseWrapper<CreatedOrderBody>> response = apiHelper.createOrder(orderRequest);
            if (response.code() == 200) {
                if (response.body().meta.isValid()) {
                    if (BuildConfig.DEBUG)
                        Log.i(TAG, "createOrder: 下单成功");
                    CreatedOrderBody data = response.body().data;
                    orderId = data.getOrderId();
                    return orderId;
                } else {
                    if (BuildConfig.DEBUG)
                        Log.i(TAG, "createOrder: 下单失败");
                }
            } else {

            }
        } catch (GetPersistenceDataException | IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage());
        }
        return orderId;
    }

    @Override
    public OrderInfo getAllOrder(boolean fresh) throws GetPersistenceDataException, IOException, ResponseMetaAuthException {
//        orderInfo = null;
/*        if (!fresh){
            if (orderInfo!=null)
            return orderInfo;
        }*/
        String token = personalInfoPDataSource.getToken();
        Log.i(TAG, "getAllOrder: " + token);
        Response<ResponseWrapper<AllOrderBody>> response = apiHelper.getAllOrder(token, "0", "9999");
        Meta meta = response.body().meta;
        if (meta.isValid()) {
            AllOrderBody data = response.body().data;
            orderInfo = orderMapper.transform(data.getOrderInfos());
        }
        return orderInfo;
    }

    @Override
    public DetailOrderEntity getOrderDetail(String token, String orderCode) throws IOException, AuthException {
        try {
            token = personalInfoPDataSource.getToken();
            Response<ResponseWrapper<OrderDetailBody>> response = apiHelper.getOrderDetail(token, orderCode);
            Meta meta = response.body().meta;
            if (meta.isValid()) {
                DetailOrderEntity data = response.body().data.getDetailOrder();
                return data;
            }
        } catch (GetPersistenceDataException | ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public OrderPayAliSign getOrderPayAliSign(String token, GetOrderPayAliSignParams params) throws GetPaySignException {
        try {
            Response<ResponseWrapper<OrderPayAliSignBody>> response = apiHelper.getOrderPayAliSign(token, params);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    OrderPayAliSignBody data = response.body().data;
                    return new OrderPayAliSign(data.sign);
                }else{
                    throw new GetPaySignException(meta.getErrorMsg());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderPayWXSign getOrderPayWXSign(String token, GetOrderpayWxSignParams params) throws GetPaySignException {
        try {
//            public String orderCode;
//            public String redEnvelopIds;
//            public String needPay;
//            public String balancePay;
            Response<ResponseWrapper<OrderPayWXSignBody>> response = apiHelper.getOrderPayWXSign(token, params);
            Log.v("PayWXSign","token::"+token.toString()+"params::"+params.toString()
            +"orderCode::"+params.getOrderCode()+"redEnvelopIds::"+params.getRedEnvelopIds()
                    +"needPay::"+params.getNeedPay()+"balancePay::"+params.getBalancePay());
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    OrderPayWXSignBody data = response.body().data;
                    WxSign wxSign = data.getWxSign();
                    OrderPayWXSign orderPayWXSign = new OrderPayWXSign();
                    orderPayWXSign.setAppid(wxSign.appid);
                    orderPayWXSign.setNoncestr(wxSign.noncestr);
                    orderPayWXSign.setPackag(wxSign.packag);
                    orderPayWXSign.setPrepayid(wxSign.prepayid);
                    orderPayWXSign.setSign(wxSign.sign);
                    orderPayWXSign.setPartnerid(wxSign.partnerid);
                    orderPayWXSign.setTimestamp(wxSign.timestamp);
                   Log.v("PayWXSign","wxsign"+data.getWxSign().toString()+"::appid"+wxSign.appid+
                            "::noncestr"+wxSign.noncestr+"::packag"+wxSign.packag+"::prepayid"+wxSign.prepayid+"::sign"
                            +wxSign.sign+"::partnerid"+wxSign.partnerid+"::timestamp"+wxSign.timestamp);
                    return orderPayWXSign;
                }else{
                    Log.v("PayWXSign","errorMessage"+meta.getErrorMsg());
                    throw new GetPaySignException(meta.getErrorMsg());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String cancelOrder(String token, String orderId,String cancelReason) throws AuthException {
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.cancelOder(token, orderId,cancelReason);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                   /* for (int i = 0; i < orderInfo.getOrders().size(); i++) {
                        if (TextUtils.equals(orderInfo.getOrders().get(i).getOrderId(),orderId))
                        {
                            orderInfo.getOrders().get(i).setOrderStatus(Constant.ORDER_STATUS_CANCELED);
                            break;
                        }
                    }*/
                    return meta.getErrorMsg()+" "+meta.getErrorCode();
                }
                return meta.getErrorMsg()+" "+meta.getErrorCode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        }
        return "连接服务器失败，请稍后重试";
    }

    @Override
    public String deleteOrder(String token, String orderId) {
        Response<ResponseWrapper<Void>> response = null;
        try {
            response = apiHelper.deleteOrder(token, orderId);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                       /* for (int i = 0; i < orderInfo.getOrders().size(); i++) {
                            if (TextUtils.equals(orderInfo.getOrders().get(i).getOrderId(),orderId))
                            {
                                orderInfo.getOrders().remove(i);
                                break;
                            }
                        }*/
                    return response.body().meta.getErrorCode();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String confirmOrder(String token, String orderId) throws Exception {
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.confirmOrder(token, orderId);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    /*for (int i = 0; i < orderInfo.getOrders().size(); i++) {
                        if (TextUtils.equals(orderInfo.getOrders().get(i).getOrderId(),orderId))
                        {
                            orderInfo.getOrders().get(i).setOrderStatus(Constant.ORDER_STATUS_CONFIRM);
                            break;
                        }
                    }*/
                    return meta.getErrorMsg();
                } else
                    throw new Exception(meta.getErrorMsg());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String payOrderByBalance(String token, String orderId, String couponId, String balanceMoney) {
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.payOrderByBalance(token, orderId, couponId, balanceMoney);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return meta.getErrorCode();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取直接支付Id
     *
     * @param token         ..
     * @param receiverId    ..
     * @param receiverType  ..
     * @param paySource     ..
     * @param totalMoney    ..
     * @param serviceTypeID ..
     * @return
     */
    @Override
    public String getDirectPayId(String token, String receiverId, String receiverType, String paySource, String totalMoney, String serviceTypeID) {
        try {
            Response<ResponseWrapper<DirectPayInfo>> response = apiHelper.getDirectPayId(token, receiverId, receiverType, paySource, totalMoney, serviceTypeID);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {

                    return response.body().data.getDirectPayId();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取直接支付微信签名
     *
     * @param token
     * @param directPayId
     * @return
     */
    @Override
    public WxSign getDirectPayWXSign(String token, String directPayId, String balancePayMoney) {

        try {
            Response<ResponseWrapper<OrderPayWXSignBody>> response = apiHelper.getDirectPayWXSign(token, directPayId, balancePayMoney);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.getWxSign();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取直接支付支付宝签名
     *
     * @param token
     * @param directPayId
     * @param balancePayMoney
     * @return
     */
    @Override
    public String getDirectPayAliSign(String token, String directPayId, String balancePayMoney) {

        try {
            Response<ResponseWrapper<AlipaySignBody>> response = apiHelper.getDirectPayAliSign(token, directPayId, balancePayMoney);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().data.alipaySign;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String directBalancePay(String token, String directPayId) {
        try {
            Response<ResponseWrapper<Void>> response = apiHelper.directBalancePay(token, directPayId);
            if (response.code() == 200) {
                Meta meta = response.body().meta;
                if (meta.isValid()) {
                    return response.body().meta.getErrorMsg();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderInfo getOrder(String pageSize, String pageIndex, String type) throws AuthException {
        OrderInfo orderInfo = new OrderInfo();
        String token;
        try {
            token = personalInfoPDataSource.getToken();
            Response<ResponseWrapper<AllOrderBody>> response = apiHelper.getOrder(token, "99999", "1", type);
            Meta meta = response.body().meta;
            if (meta.isValid()) {
                AllOrderBody data = response.body().data;

                return new OrderInfo(data.getOrderInfos());
            }
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseMetaAuthException e) {
            throw new AuthException(e.getMessage(), e);
        }

        return orderInfo;
    }

    @Override
    public String placeDirectOrder(DirectOrderParam param) throws AuthException {
        try {
            Response<ResponseWrapper<CreatedOrderBody>> response = apiHelper.placeDirectOrder(param);
            if (response.body().meta.isValid()) {
                return response.body().data.getOrderId();
            }
        } catch (ResponseMetaAuthException e) {
            throw  new AuthException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;//为空失败
    }
}
