package com.homepaas.sls.mvp.model;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.TextUtils;

import com.homepaas.sls.common.TimeUtils;
import com.homepaas.sls.domain.entity.PayDetail;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
public class NormalPayItem implements PayItem {


    private PayDetail payDetail;

    public NormalPayItem(PayDetail payDetail) {

        this.payDetail = payDetail;
    }

    public String getItemLeftTop() {
        if (TextUtils.isEmpty(payDetail.getOrderCode())) {
            return payDetail.getDetailType();
        }
        return TextUtils.concat(payDetail.getDetailType(), "(", payDetail.getOrderCode(), ")").toString();
    }

    public String getMoney() {
        return payDetail.getMoney() + "元";
    }

    @ColorInt
    public int getMoneyTextColor() {
        float fMoney = Float.parseFloat(payDetail.getMoney());
        if (fMoney > 0) {
            return Color.argb(255, 255, 88, 102);
        } else {
            return Color.argb(255, 42, 189, 135);
        }
    }


    public boolean isNoRefund() {
//        return payDetail.getRefundStatus() == 0;
        return true;
    }

    public String getDisplayTime() {
        return TimeUtils.formatDate(payDetail.getTime());
    }

    public String getPayType() {
        return payDetail.getPaymentMode();
    }

    public String getState() {
        //return stateString(payDetail.getRefundStatus());
        return "";
    }

    public String getRefundFolw() {
//        return payDetail.getRefundFlow();
        return "";
    }

    private static String stateString(int state) {
        switch (state) {
            case 0:
            default:
                return "无退款";
            case 1:
                return "退款中";
            case 2:
                return "退款结束";
        }
    }

}
