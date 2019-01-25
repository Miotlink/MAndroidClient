package com.homepaas.sls.data.entity;

/**
 * Created by mhy on 2017/8/31.
 */

public class OrderListPopEntity {


    /**
     * PopType (string, optional): 弹框类型，0：不弹框，1：弹确认页，2：弹评价页。默认不弹框,
     * <p>
     * PopTitle (string, optional): 弹框标题,
     * <p>
     * PopDescription (string, optional): 弹框描述,
     * <p>
     * OrderId (string, optional): 订单Id
     * PopType : string
     * PopTitle : string
     * PopDescription : string
     * OrderId : string
     */

    private String PopType;
    private String PopTitle;
    private String PopDescription;
    private String OrderId;
    private String OrderCode;

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getPopType() {
        return PopType;
    }

    public void setPopType(String PopType) {
        this.PopType = PopType;
    }

    public String getPopTitle() {
        return PopTitle;
    }

    public void setPopTitle(String PopTitle) {
        this.PopTitle = PopTitle;
    }

    public String getPopDescription() {
        return PopDescription;
    }

    public void setPopDescription(String PopDescription) {
        this.PopDescription = PopDescription;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }
}
