package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/3/25.
 */
public class AliPaySignEntry {

    @SerializedName("AlipaySign")
    private String alipaysign;
    @SerializedName("out_trade_no")
    private String outTradeNo;

    public String getAlipaysign() {
        return alipaysign;
    }

    public void setAlipaysign(String alipaysign) {
        this.alipaysign = alipaysign;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

}
