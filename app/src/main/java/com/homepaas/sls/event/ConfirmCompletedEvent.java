package com.homepaas.sls.event;

/**
 * Created by mhy on 2017/8/28.
 */

public class ConfirmCompletedEvent {
    public String flag;
    //附载的入口
    public static final String ORDER_MAINACTIVITY = "1";  //MainActivity
    public static final String ORDER_ALLORDER = "2"; //OrderAllManager
    public static final String ORDER_DETAIL = "3";  //ClientOrderDetailActivity
    public static final String ORDER_TO_CONFIRM = "3";  //OrderToConfirmFragment

    private String orderID;
    private String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public ConfirmCompletedEvent(String flag, String orderID) {
        this.flag = flag;
        this.orderID = orderID;
    }

    public ConfirmCompletedEvent(String flag, String orderID, String orderCode) {
        this.flag = flag;
        this.orderID = orderID;
        this.orderCode = orderCode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
