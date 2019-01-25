package com.homepaas.sls.event;

/**
 * Created by Administrator on 2016/7/5.
 */
public class WXPayResultEvent {

    public static final int Success = 0;
    public static final int Fail = 1;
    public static final int Cancel = 2;
    public String msg;
    private static String[] messges = {"支付成功","支付失败","支付取消"};

    public int type;//回调PayActivity = 1，OrderPayActivity页面 = 2

    public WXPayResultEvent(int type) {
        this.type = type;
        this.msg =messges[type];
    }
}
