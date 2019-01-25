package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/7/20.
 */

public class OrderBtnInfo {
    // 如:下单成功 - 待支付
    @SerializedName("Title")
    private String title;
    // 如：请在剩余时间内完成支付
    @SerializedName("Description")
    private String description;
    // 即时状态，如：下单成功，支付成功，师傅已接单
    @SerializedName("Status")
    private String status;
    /**
     * 以下按钮状态均为，0：不显示，1：显示，默认是0.
     */
    // 是否显示“去支付”按钮
    @SerializedName("IsDisplayGotoPayBtn")
    private String isDisplayGotoPayBtn;
    // 是否显示“取消订单”按钮
    @SerializedName("IsDisplayCancelOrderBtn")
    private String isDisplayCancelOrderBtn;
    //是否是真的取消
    @SerializedName("IsGotoCancelPage")
    private String isGotoCancelPage;
    // 是否显示“确认完成”按钮
    @SerializedName("IsDisplayClientConfirmBtn")
    private String isDisplayClientConfirmBtn;
    // 是否显示“去评价”按钮
    @SerializedName("IsDisplayGotoEvaluateBtn")
    private String isDisplayGotoEvaluateBtn;
    // 是否显示“删除订单”按钮
    @SerializedName("IsDisplayDeleteOrderBtn")
    private String isDisplayDeleteOrderBtn;
    //是否显示投诉按钮
    @SerializedName("IsDisplayComplaintsBtn")
    private String isDisplayComplaintsBtn;

    //是否显示增加服务按钮
    @SerializedName("IsDisplayAddServiceBtn")
    private String isDisplayAddServiceBtn;

    public String getIsDisplayAddServiceBtn() {
        return isDisplayAddServiceBtn;
    }

    public void setIsDisplayAddServiceBtn(String isDisplayAddServiceBtn) {
        this.isDisplayAddServiceBtn = isDisplayAddServiceBtn;
    }

    public String getIsDisplayComplaintsBtn() {
        return isDisplayComplaintsBtn;
    }

    public void setIsDisplayComplaintsBtn(String isDisplayComplaintsBtn) {
        this.isDisplayComplaintsBtn = isDisplayComplaintsBtn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsDisplayGotoPayBtn() {
        return isDisplayGotoPayBtn;
    }

    public void setIsDisplayGotoPayBtn(String isDisplayGotoPayBtn) {
        this.isDisplayGotoPayBtn = isDisplayGotoPayBtn;
    }

    public String getIsDisplayCancelOrderBtn() {
        return isDisplayCancelOrderBtn;
    }

    public void setIsDisplayCancelOrderBtn(String isDisplayCancelOrderBtn) {
        this.isDisplayCancelOrderBtn = isDisplayCancelOrderBtn;
    }

    public String getIsGotoCancelPage() {
        return isGotoCancelPage;
    }

    public void setIsGotoCancelPage(String isGotoCancelPage) {
        this.isGotoCancelPage = isGotoCancelPage;
    }

    public String getIsDisplayClientConfirmBtn() {
        return isDisplayClientConfirmBtn;
    }

    public void setIsDisplayClientConfirmBtn(String isDisplayClientConfirmBtn) {
        this.isDisplayClientConfirmBtn = isDisplayClientConfirmBtn;
    }

    public String getIsDisplayGotoEvaluateBtn() {
        return isDisplayGotoEvaluateBtn;
    }

    public void setIsDisplayGotoEvaluateBtn(String isDisplayGotoEvaluateBtn) {
        this.isDisplayGotoEvaluateBtn = isDisplayGotoEvaluateBtn;
    }

    public String getIsDisplayDeleteOrderBtn() {
        return isDisplayDeleteOrderBtn;
    }

    public void setIsDisplayDeleteOrderBtn(String isDisplayDeleteOrderBtn) {
        this.isDisplayDeleteOrderBtn = isDisplayDeleteOrderBtn;
    }
}
