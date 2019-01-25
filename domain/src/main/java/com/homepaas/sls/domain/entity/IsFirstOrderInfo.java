package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sherily on 2017/2/15.
 */

public class IsFirstOrderInfo {
    /***
     * 0:不是第一次下单，1：是第一次下单
     */
    @SerializedName("IsFirstOrder")
    private String isFirstOrder;

    @SerializedName("Service")
    private Service lastOrderService;

    public static class Service{
        @SerializedName("Id")
        private String id;
        @SerializedName("Name")
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public Service getLastOrderService() {
        return lastOrderService;
    }

    public void setLastOrderService(Service lastOrderService) {
        this.lastOrderService = lastOrderService;
    }

    public String getIsFirstOrder() {
        return isFirstOrder;
    }

    public void setIsFirstOrder(String isFirstOrder) {
        this.isFirstOrder = isFirstOrder;
    }
}
