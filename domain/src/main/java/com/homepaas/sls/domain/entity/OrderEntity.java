package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.embedded_class.Service;

import java.io.Serializable;


/**
 * Created by Administrator on 2016/5/3.
 */
public class OrderEntity implements Serializable{

    @SerializedName("Contact")
    public String contact;
    @SerializedName("PhoneNumber")
    public String phoneNumber;
    @SerializedName("OrderId")
    public String OrderId;
    @SerializedName("OrderCode")
    public String OrderCode;
    @SerializedName("CreateTime")
    public String CreateTime;
    @SerializedName("OrderStatus")
    public String OrderStatus;
    @SerializedName("ServiceAddress1_Lat")
    public String ServiceAddress1_Lat;
    @SerializedName("ServiceAddress1_Lng")
    public String ServiceAddress1_Lng;
    @SerializedName("IsPayOff")
    public String IsPayOff;
    @SerializedName("Price")
    public String Price;
    @SerializedName("TotalPrice")
    public String totalPrice;
    @SerializedName("Total")
    public String total;
    @SerializedName("UnitName")
    public String unitName;
    @SerializedName("PayLock")
    public String PayLock;
    @SerializedName("ServiceProviderType")
    public String providerType;
    @SerializedName("ServiceProviderPic")
    public String providerPic;
    @SerializedName("ServiceProviderName")
    public String providerName;
    @SerializedName("ServiceProviderGender")
    public String gender;
    @SerializedName("IsEvaluated")  //是否已评价
    public String IsEvaluated;
    @SerializedName("ServiceName")
    public String serviceName;
    @SerializedName("ServicePrice")
    public String servicePrice;
    @SerializedName("Service")
    public Service service;
    @SerializedName("RefundStatus")
    public String refundStatus;
    @SerializedName("DiscountInfo")
    public String discountInfo;
    @SerializedName("DiscountAmount")
    public String discountAmount;
    @SerializedName("Activity")
    public ActionEntity activity;
    @SerializedName("IsNegotiable")
    public String negotiable;
    @SerializedName("StartingPrice")
    public String startingPrice;
    @SerializedName("OrderType") //0:指定工人下单，1：一键下单，2:指定商户下单
    public String orderType;
    @SerializedName("Merchant")
    public MerchantResponse merchantResponse;
    @SerializedName("Worker")
    public WorkerResponse workerResponse;
    //新活动数据
    @SerializedName("ActivityNgs")
    private ActivityNgInfoNew activityNgInfos;

    /**
     * 快递信息
     * @return
     */
    //是否是快递订单，0：不是，1：快递 2：跑腿
    @SerializedName("IsKdEOrder")
    private String isKdEOrder ;
    // 快递详情，订单不是快递时，为空
    @SerializedName("KdEOrderInfo")
    private KdEOrderInfo kdEOrderInfo;

    /**
     * 保险赔付信息
     * @return
     */
    @SerializedName("ClaimsInfo")
    private ClaimsInfo claimsInfo;

    /**
     * 跑腿信息
     * @return
     */
    @SerializedName("ExpressOrderInfo")
    private ExpressOrderInfo expressOrderInfo;

    /**
     * 新添加
     *
     * @return
     */
    //定金
    @SerializedName("DepositInfo")
    private DepositInfo depositInfo;

    // 支付状态，0:实付（已支付），1：待支付
    @SerializedName("PayStatus")
    private String payStatus;
    // 支付金额，如果PayStatus=0，则表示实付金额，如果PayStatus=1，则表示待付金额
    @SerializedName("PayAmount")
    private String payAmount;
    // 订单按钮的显示
    @SerializedName("OrderBtnInfo")
    private OrderBtnInfo orderBtnInfo;
    //0：不显示 1：显示退款状态 2：显示保险状态
    @SerializedName("LeftDownDisplayType")
    private String leftDownDisplayType;

    public String getLeftDownDisplayType() {
        return leftDownDisplayType;
    }

    public void setLeftDownDisplayType(String leftDownDisplayType) {
        this.leftDownDisplayType = leftDownDisplayType;
    }

    public DepositInfo getDepositInfo() {
        return depositInfo;
    }

    public void setDepositInfo(DepositInfo depositInfo) {
        this.depositInfo = depositInfo;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public OrderBtnInfo getOrderBtnInfo() {
        return orderBtnInfo;
    }

    public void setOrderBtnInfo(OrderBtnInfo orderBtnInfo) {
        this.orderBtnInfo = orderBtnInfo;
    }

    public ExpressOrderInfo getExpressOrderInfo() {
        return expressOrderInfo;
    }

    public void setExpressOrderInfo(ExpressOrderInfo expressOrderInfo) {
        this.expressOrderInfo = expressOrderInfo;
    }

    public ClaimsInfo getClaimsInfo() {
        return claimsInfo;
    }

    public void setClaimsInfo(ClaimsInfo claimsInfo) {
        this.claimsInfo = claimsInfo;
    }

    public String getIsKdEOrder() {
        return isKdEOrder;
    }

    public void setIsKdEOrder(String isKdEOrder) {
        this.isKdEOrder = isKdEOrder;
    }

    public KdEOrderInfo getKdEOrderInfo() {
        return kdEOrderInfo;
    }

    public void setKdEOrderInfo(KdEOrderInfo kdEOrderInfo) {
        this.kdEOrderInfo = kdEOrderInfo;
    }

    public ActivityNgInfoNew getActivityNgInfos() {
        return activityNgInfos;
    }

    public void setActivityNgInfos(ActivityNgInfoNew activityNgInfos) {
        this.activityNgInfos = activityNgInfos;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public MerchantResponse getMerchantResponse() {
        return merchantResponse;
    }

    public void setMerchantResponse(MerchantResponse merchantResponse) {
        this.merchantResponse = merchantResponse;
    }

    public WorkerResponse getWorkerResponse() {
        return workerResponse;
    }

    public void setWorkerResponse(WorkerResponse workerResponse) {
        this.workerResponse = workerResponse;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getServiceAddress1_Lat() {
        return ServiceAddress1_Lat;
    }

    public void setServiceAddress1_Lat(String serviceAddress1_Lat) {
        ServiceAddress1_Lat = serviceAddress1_Lat;
    }

    public String getServiceAddress1_Lng() {
        return ServiceAddress1_Lng;
    }

    public void setServiceAddress1_Lng(String serviceAddress1_Lng) {
        ServiceAddress1_Lng = serviceAddress1_Lng;
    }


    public String getIsPayOff() {
        return IsPayOff;
    }

    public void setIsPayOff(String isPayOff) {
        IsPayOff = isPayOff;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscountInfo() {
        return discountInfo;
    }

    public void setDiscountInfo(String discountInfo) {
        this.discountInfo = discountInfo;
    }

    public String getPayLock() {
        return PayLock;
    }

    public void setPayLock(String payLock) {
        PayLock = payLock;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getProviderPic() {
        return providerPic;
    }

    public void setProviderPic(String providerPic) {
        this.providerPic = providerPic;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getIsEvaluated() {
        return IsEvaluated;
    }

    public void setIsEvaluated(String isEvaluated) {
        IsEvaluated = isEvaluated;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public ActionEntity getActivity() {
        return activity;
    }

    public void setActivity(ActionEntity activity) {
        this.activity = activity;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getNegotiable() {
        return negotiable;
    }

    public String getStartingPrice() {
        return startingPrice;
    }
}
