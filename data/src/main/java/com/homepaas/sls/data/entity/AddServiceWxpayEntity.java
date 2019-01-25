package com.homepaas.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mhy on 2017/9/1.
 */

public class AddServiceWxpayEntity {
    /**
     * WxpaySign : {"appid":"string","partnerid":"string","prepayid":"string","package":"string","noncestr":"string","timestamp":"string","sign":"string"}
     * out_trade_no : string
     */

    private WxpaySignBean WxpaySign;
    private String out_trade_no;

    public WxpaySignBean getWxpaySign() {
        return WxpaySign;
    }

    public void setWxpaySign(WxpaySignBean WxpaySign) {
        this.WxpaySign = WxpaySign;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public static class WxpaySignBean {
        /**
         * appid : string
         * partnerid : string
         * prepayid : string
         * package : string
         * noncestr : string
         * timestamp : string
         * sign : string
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
