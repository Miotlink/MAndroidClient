package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.embedded_class.Note;
import com.homepaas.sls.domain.entity.embedded_class.Refund;
import com.homepaas.sls.domain.entity.embedded_class.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by CJJ on 2016/9/11.
 */

public class DetailOrderEntity {

    //剩余时间（秒数）注意：只有定价的服务下单了，还没支付才会有值。
    @SerializedName("ResidualTime")
    private String residualTime;
    @SerializedName("Contact")
    public String contact;
    @SerializedName("PhoneNumber")
    public String phoneNumber;
    @SerializedName("OrderId")
    public String orderId;
    @SerializedName("OrderCode")
    public String orderCode;
    @SerializedName("CreateTime")
    public String createTime;
    @SerializedName("OrderStatus")
    public String orderStatus;
    @SerializedName("AcceptTime")
    public String acceptTime;
    @SerializedName("FinishTime")
    public String finishTime;
    @SerializedName("ConfirmTime")
    public String confirmTime;
    @SerializedName("CancelTime")
    public String cancelTime;
    @SerializedName("IsPayOff")
    public String isPayOff;
    @SerializedName("PayOffTime")
    public String payTime;
    @SerializedName("Price")//订单总价,明显和英文含义对应不了，ERP取名不合理
    public String price;
    @SerializedName("TotalPrice")
    public String totalPrice;
    @SerializedName("Total")
    public String serviceNumber;
    @SerializedName("UnitName")
    public String unitName;
    @SerializedName("DiscountInfo")
    public String discountInfo;
    @SerializedName("DiscountAmount")
    public String discountAmount;
    @SerializedName("PayLock")
    public String payLock;

    //工人或商户头像
    @SerializedName("ServiceProviderPic")
    public String serviceProviderPic;
    @SerializedName("ServiceProviderId")
    public String serviceProviderId;
    @SerializedName("ServiceProviderType")
    public String serviceProvideType;
    @SerializedName("ServiceProviderName")
    public String serviceProviderName;
    @SerializedName("ServiceProviderGender")//“0”：男； “1”：女； 工人类型有效
    public String gender;
    @SerializedName("ServiceProviderPhone")
    public String serviceProviderPhone;
    @SerializedName("Refunds")
    public List<Refund> refunds;
    @SerializedName("Service")
    public Service service;
    @SerializedName("CanCancel")
    public String cancelable;
    @SerializedName("Activity")
    public ActionEntity activity;
    @SerializedName("Notes")
    public List<Note> notes;
    @SerializedName("MinPrice")
    public String minPrice;
    @SerializedName("MaxPrice")
    public String maxPrice;
    @SerializedName("IsRangePrice")
    public String isRange;
    @SerializedName("IsEvaluated")
    public String isEvaluated;
    @SerializedName("IsNegotiable")
    String negotiable;
    @SerializedName("StartingPrice")
    String startingPrice;
    @SerializedName("OrderType") //0:指定工人下单，1：一键下单，2:指定商户下单
    public String orderType;
    @SerializedName("Merchant")
    public MerchantResponse merchantResponse;
    @SerializedName("Worker")
    public WorkerResponse workerResponse;
    @SerializedName("ActivityNgs")
    private ActivityNgInfoNew activityNgInfos;

    /**
     * 快递信息
     *
     * @return
     */
    //是否是快递订单，0：不是，1：是 2:跑腿
    @SerializedName("IsKdEOrder")
    private String isKdEOrder;
    // 快递详情，订单不是快递时，为空
    @SerializedName("KdEOrderInfo")
    private KdEOrderInfo kdEOrderInfo;

    /**
     * 保险赔付信息
     *
     * @return
     */
    @SerializedName("ClaimsInfo")
    private ClaimsInfo claimsInfo;

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

    public DepositInfo getDepositInfo() {
        return depositInfo;
    }

    public void setDepositInfo(DepositInfo depositInfo) {
        this.depositInfo = depositInfo;
    }

    public String getServiceProviderPic() {
        return serviceProviderPic;
    }

    public void setServiceProviderPic(String serviceProviderPic) {
        this.serviceProviderPic = serviceProviderPic;
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

    public String getResidualTime() {
        return residualTime;
    }

    public void setResidualTime(String residualTime) {
        this.residualTime = residualTime;
    }

    private String payMoney;//实际支付金额
    private String couponTitle;//红包名称
    private String couponMoney;//红包减免
    private String actionTitle;//活动名称
    private String actionMoney;//活动减免

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

    public List<Refund> getRefunds() {
        return refunds;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getIsPayOff() {
        return isPayOff;
    }

    public void setIsPayOff(String isPayOff) {
        this.isPayOff = isPayOff;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountInfo() {
        return discountInfo;
    }

    public void setDiscountInfo(String discountInfo) {
        this.discountInfo = discountInfo;
    }

    public String getPayLock() {
        return payLock;
    }

    public void setPayLock(String payLock) {
        this.payLock = payLock;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getServiceProvideType() {
        return serviceProvideType;
    }

    public void setServiceProvideType(String serviceProvideType) {
        this.serviceProvideType = serviceProvideType;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getServiceProviderPhone() {
        return serviceProviderPhone;
    }

    public void setServiceProviderPhone(String serviceProviderPhone) {
        this.serviceProviderPhone = serviceProviderPhone;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getCancelable() {
        return cancelable;
    }

    public void setCancelable(String cancelable) {
        this.cancelable = cancelable;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public ActionEntity getActivity() {
        return activity;
    }

    public void setActivity(ActionEntity activity) {
        this.activity = activity;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public boolean isNegotiable() {
        return "面议".equals(this.totalPrice) || "面议".equals(this.price);
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public boolean isRange() {
        boolean equals = "1".equals(isRange);//抢单改版后订单详情只有面议和定价，不存在区间价格
        return false;
    }

    public String getIsRange() {
        return isRange;
    }

    public void setRange(String range) {
        isRange = range;
    }

    public String getIsEvaluated() {
        return isEvaluated;
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

    public String getNegotiable() {
        return negotiable;
    }

    public void setNegotiable(String negotiable) {
        this.negotiable = negotiable;
    }

    public String getStartingPrice() {
        return startingPrice;
    }



    public String getPayMoney() {
        if (getTotalPrice() != null && !"".equals(getTotalPrice())) {
            String minus = "0";
            BigDecimal paySum = new BigDecimal(getTotalPrice());
            BigDecimal minusDecimal = new BigDecimal(minus).setScale(2, RoundingMode.HALF_UP);
            if (activityNgInfos != null && activityNgInfos.isSpecialSatisfied(Float.valueOf(totalPrice))) {
                for (ActivityNgInfoNew.ActivityNgDetail activityNgDetail : activityNgInfos.getSatisfiedSpecialActivityList()) {
                    String minuss = activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getMinus();
                    BigDecimal newminusDecimal = new BigDecimal(minuss).setScale(2, RoundingMode.HALF_UP);
                    minusDecimal = minusDecimal.add(newminusDecimal);
                }
            }
            paySum = paySum.subtract(minusDecimal);
//            if (activity != null && activity.isSpecialSatisfied(Float.parseFloat(getTotalPrice()))) {
//                BigDecimal actionSum = new BigDecimal(activity.getSpecialRule().get(0).getMinus()).setScale(1, RoundingMode.HALF_UP);
//                paySum = paySum.subtract(actionSum);
//            }


            if (getCouponMoney() != null && !"".equals(getCouponMoney())) {
                BigDecimal couponSum = new BigDecimal(getCouponMoney()).setScale(1, RoundingMode.HALF_UP);
                paySum = paySum.subtract(couponSum);
            }

            return String.valueOf(paySum.floatValue());
        }
        return totalPrice;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getCouponTitle() {
        return discountInfo;
    }


    public String getCouponMoney() {
        return discountAmount;
    }

    public String getActionTitle() {
        if (activity != null && activity.isSpecialSatisfied(Float.parseFloat(getTotalPrice()))) {
            return activity.getSpecialTitle();
        } else
            return null;
    }

    public String getActionMoney() {
        if (activity != null && activity.isSpecialSatisfied(Float.parseFloat(getTotalPrice()))) {
            return activity.getSpecialRule().get(0).getMinus();
        } else
            return null;
    }

}
