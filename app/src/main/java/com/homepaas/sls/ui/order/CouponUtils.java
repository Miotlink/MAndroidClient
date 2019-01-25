package com.homepaas.sls.ui.order;

import android.text.TextUtils;

import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.CouponContentsInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * created by CJJ on 2016/7/4 19:59
 */
public class CouponUtils {

    public String getResultMoney(String totalMoney, CouponContents couponContents) {
        String resultMoney = null;
        long l = System.currentTimeMillis();

        return resultMoney;
    }

    /**
     * 优惠券排序，生成新的集合并返回
     *
     * @param couponContentses
     * @return
     */
    public static List<CouponContents> sortCoupons(List<CouponContents> couponContentses) {
//        List<CouponContents> results = new ArrayList<>();
        final String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        Collections.sort(couponContentses, new Comparator<CouponContents>() {
            @Override
            public int compare(CouponContents lhs, CouponContents rhs) {
                //使用未使用比较
                if (lhs.getIsUsed().compareTo(rhs.getIsUsed()) != 0)
                    return lhs.getIsUsed().compareTo(rhs.getIsUsed());
                else {//过期比较
                    boolean lDateValid = lhs.getStartTime().compareTo(curtime) < 0 && lhs.getEndTime().compareTo(curtime) > 0;
                    boolean rDateValid = rhs.getStartTime().compareTo(curtime) < 0 && rhs.getEndTime().compareTo(curtime) > 0;
                    if (lDateValid && rDateValid)//均过期或者均未过期,进行日期比较
                    {
                        //优惠券有效截止日期距离当前时间最近在前面
                        return Math.abs(lhs.getEndTime().compareTo(curtime)) - Math.abs(rhs.getEndTime().compareTo(curtime));
                    } else {
                        //过期在前面
                        return lDateValid && !rDateValid ? -1 : 1;
                    }
                }
            }
        });

        return couponContentses;
    }

    /**
     * 获取可用优惠券数
     *
     * @param couponContentses
     * @return
     */
    public static int getValidCouponCount(List<CouponContents> couponContentses) {
        int count = 0;
        if (couponContentses == null || couponContentses.isEmpty())
            return count;
        for (CouponContents item :
                couponContentses) {
            final String curtime = String.valueOf(Calendar.getInstance(Locale.CHINA).getTimeInMillis());
            String startTime = item.getStartTime();
            String endTime = item.getEndTime();
            String isUsed = item.getIsUsed();
            if (TextUtils.equals("0", isUsed)) {
                if (curtime.compareTo(startTime) > 0 && curtime.compareTo(endTime) < 0)
                    count++;
            }
        }
        return count;
    }

    /**
     * 筛选可以使用的优惠券集合
     */
    public static List<CouponContents> getAvailableCoupons(List<CouponContents> couponContentses) {

        List<CouponContents> resultSet = new ArrayList<>();
        if (couponContentses == null || couponContentses.isEmpty())
            return resultSet;
        for (CouponContents item :
                couponContentses) {
            final String curtime = String.valueOf(Calendar.getInstance(Locale.CHINA).getTimeInMillis());
            String startTime = item.getStartTime();
            String endTime = item.getEndTime();
            String isUsed = item.getIsUsed();
            if (TextUtils.equals("0", isUsed)) {
                if (curtime.compareTo(startTime) > 0 && curtime.compareTo(endTime) < 0)
                    resultSet.add(item);
            }
        }
        return resultSet;
    }
    /**
     * 获取最大优惠券减免金额的优惠券
     *
     * @param money
     * @param couponContents
     * @return
     */
    public static BestCountResult getMaxDiscountFromCoupons(String money,List<CouponContents> couponContents) {
        BestCountResult bestOne = null;
        int pos = -1;
        double serviceMoney = Double.valueOf(money);
        double resultMoney = serviceMoney;//保存结果金额
        double temp = serviceMoney;//临时变量，保存当前少的结果金额
        int index = -1;
        double resultAmount = 0;
        boolean isFullCut = false;
        List<CouponContents> availableCoupons = couponContents;
        for (CouponContents item :
                availableCoupons) {
            index++;
            List<CouponContents.CouponDetails> detailses = item.getCouponDetailses();
            for (CouponContents.CouponDetails detailItem :
                    detailses) {

                if (TextUtils.equals(Constant.DiscountTypeFullCut, detailItem.getDiscountType()))//满减
                {
                    isFullCut = true;
                    double amount = Double.valueOf(detailItem.getAmount());
                    if (amount <= serviceMoney) {
                        double discountAmount = Double.valueOf(detailItem.getDiscountAmount());
                        if (serviceMoney - discountAmount < temp) {
                            temp = serviceMoney - discountAmount;
                            pos = index;
                            resultMoney = temp;
                            resultAmount = Double.valueOf(detailItem.getAmount());
                        }
                    }
                } else if (TextUtils.equals(Constant.DiscountTypeDiscount, detailItem.getDiscountType()))//折扣
                {
                    isFullCut = false;
                    double discount = Double.valueOf(detailItem.getDiscount());
                    if (serviceMoney * discount < temp) {
                        if (discount == 0){
                            temp = serviceMoney;
                        } else {
                            temp = serviceMoney * discount;
                        }
                        pos = index;
                        resultMoney = temp;
                    }
                }
            }
        }
        if (pos != -1) {//选出了有效的优惠券
            bestOne = new BestCountResult();
            bestOne.setBestCoupon(couponContents.get(pos));
            bestOne.setResultMoney(resultMoney < 1 ? 1.00 : resultMoney);
            bestOne.setBestCouponId(couponContents.get(pos).getId());
            bestOne.setAmount(String.valueOf(resultAmount));
            bestOne.setDiscount(String.valueOf(serviceMoney - resultMoney));
        }
        return bestOne;
    }

    /**
     * 根据服务金额来对优惠券使用做限制
     * @param money
     * @param couponContentses
     */
    public static void sortValidCoupons(String money, List<CouponContents> couponContentses) {

        BestCountResult bestOne = null;
        int pos = -1;
        double serviceMoney = Double.valueOf(money);
        double resultMoney = serviceMoney;//保存结果金额
        double temp = serviceMoney;//临时变量，保存当前少的结果金额
        int index = -1;
        double resultAmount = 0;
        List<CouponContents> availableCoupons = getAvailableCoupons(couponContentses);
        for (CouponContents item :
                availableCoupons) {
            index++;
            List<CouponContents.CouponDetails> detailses = item.getCouponDetailses();
            for (CouponContents.CouponDetails detailItem :
                    detailses) {
                String tag = "0";
                if (TextUtils.equals(Constant.DiscountTypeFullCut, detailItem.getDiscountType()))//满减
                {
                    double amount = Double.valueOf(detailItem.getAmount());
                    if (amount <= serviceMoney) {
                        tag = "1";
                    }
                } else if (TextUtils.equals(Constant.DiscountTypeDiscount, detailItem.getDiscountType()))//折扣
                {
                        tag = "1";
                }
                item.setTag(tag);
            }
        }

    }

    public static class BestCountResult {
        double resultMoney;
        public CouponContents bestCoupon;
        public String discount;//优惠金额
        public String bestCouponId;
        public String amount;//满减金额

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public double getResultMoney() {
            return resultMoney;
        }

        public void setResultMoney(double resultMoney) {
            this.resultMoney = resultMoney;
        }

        public CouponContents getBestCoupon() {
            return bestCoupon;
        }

        public void setBestCoupon(CouponContents bestCoupon) {
            this.bestCoupon = bestCoupon;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getBestCouponId() {
            return bestCouponId;
        }

        public void setBestCouponId(String bestCouponId) {
            this.bestCouponId = bestCouponId;
        }
    }
}
