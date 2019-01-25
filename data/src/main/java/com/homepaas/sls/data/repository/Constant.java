package com.homepaas.sls.data.repository;

/**
 * on 2016/3/30 0030
 *
 * @author zhudongjie .
 */
public class Constant {
    public static final String NETWORK_ERROR = "网络不稳定,请检查设置并重试";

    public static final String ORDER_STATUS_TOASSIGN = "1";//待指派
    public static final String ORDER_STATUS_TOTAKE = "10";//待接单
    public static final String ORDER_STATUS_MERCHANT_ASSIGNMENT="14";//待商户指派
    public static final String ORDER_STATUS_WORKER_ORDER="17";//待工人接单(商户指派后工人还没接单)
    public static final String ORDER_STATUS_TAKEN = "20";//已接单
    public static final String ORDER_STATUS_WORKER_COMPLETE = "30"; //待确认
    public static final String ORDER_STATUS_CONFIRMED = "40";//客户已经确认完成
    public static final String ORDER_STATUS_CANCELED = "50";//已取消
    public static final String PAYOFF = "1";
    public static final CharSequence EVALUATED = "1";//已经评价
}
