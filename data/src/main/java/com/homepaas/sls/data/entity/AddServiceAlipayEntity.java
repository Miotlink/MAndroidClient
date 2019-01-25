package com.homepaas.sls.data.entity;

/**
 * Created by mhy on 2017/9/1.
 */

public class AddServiceAlipayEntity {
    /**
     * AlipaySign : string
     * out_trade_no : string
     * GATEWAY_NEW : string
     */

    private String AlipaySign;
    private String out_trade_no;
    private String GATEWAY_NEW;

    public String getAlipaySign() {
        return AlipaySign;
    }

    public void setAlipaySign(String AlipaySign) {
        this.AlipaySign = AlipaySign;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getGATEWAY_NEW() {
        return GATEWAY_NEW;
    }

    public void setGATEWAY_NEW(String GATEWAY_NEW) {
        this.GATEWAY_NEW = GATEWAY_NEW;
    }
}
