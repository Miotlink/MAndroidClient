package com.homepaas.sls.data.entity;

/**
 * Created by JWC on 2017/8/30.
 */

public class HomeOrderStatusEntity {


    /**
     * Status (string, optional): 层浮状态,
     * <p>
     * Description (string, optional): 层浮描述,
     * <p>
     * OrderId (string, optional): 订单Id，点击层浮，跳转到订单详情
     * Status : null
     * Description : null
     * OrderId : 3bad2231-3e70-e711-b7c4-14dda950621b
     */

    private String Status;
    private String Description;
    private String OrderId;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
}
