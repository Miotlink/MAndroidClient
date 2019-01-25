package com.homepaas.sls.ui.order;

/**
 * Created by CMJ on 2016/6/22.
 */

public class Constant {

    /**
     * 已下单=10,已接单=20,已完成=30,已确认=40,已取消=50
     */
    public static final int PLACE = 10;
    public static final int TAKEN = 20;
    public static final int COMPLETE = 30;
    public static final int CONFIRM = 40;
    public static final int CANCEL = 50;

    public static final int PAYOFF = 1;//付清
    public static final int NOTPAYOFF = 0;//未付清

    public static final CharSequence CANCALL = "1";//可以拨打电话

    public static final String EVALUATED = "1";

    public static String OrderId = "OrderId";
    public static String OrderCode = "OrderId";
    public static final String WITHENVELOPS = "WITHENVELOPS";
    public static final String ServiceType = "ServiceType";

    public static CharSequence DiscountTypeFullCut="0";//满减类型
    public static final String DiscountTypeDiscount = "1";//折扣类型

    public static final String SERVICE_TYPE_WORKER = "1";
    public static final String SERVICE_TYPE_BUSINESS = "2";
    public static final String SERVICE_TYPE_ALL = "0";
}
