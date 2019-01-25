package com.homepaas.sls.mvp.presenter.coupon;

import android.text.TextUtils;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.CouponContentsInfo;
import com.homepaas.sls.domain.interactor.GetCouponContentsInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.coupon.CouponContentsView;
import com.homepaas.sls.ui.order.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Sherily on 2016/6/23.
 */
@ActivityScope
public class CouponContentPresenter implements Presenter {
    private static final String TAG = "CouponContentPresenter";
    private CouponContentsView couponContentsView;
    GetCouponContentsInteractor getCouponContentsInteractor;

    public void setCouponContentsView(CouponContentsView couponContentsView) {
        this.couponContentsView = couponContentsView;
    }

    @Inject
    public CouponContentPresenter(GetCouponContentsInteractor getCouponContentsInteractor) {
        this.getCouponContentsInteractor = getCouponContentsInteractor;
    }

    private boolean iscanUse(CouponContents couponContents) {
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        if (!TextUtils.isEmpty(couponContents.getStartTime()) && !TextUtils.isEmpty(couponContents.getEndTime())) {
            if (curtime.compareTo(couponContents.getStartTime()) > 0 && curtime.compareTo(couponContents.getEndTime()) < 0) {//当前时间在有效期内的为可用
                return true;
            } else
                return false;
        } else if (TextUtils.isEmpty(couponContents.getEndTime()) || curtime.compareTo(couponContents.getEndTime()) < 0) {
            return true;
        } else
            return false;

    }

    private List<CouponContents> canPayList;
    private List<CouponContents> canNotPayList;
    private String orderMoney;

    private List<CouponContents> sortNoUsedRedPacket(List<CouponContents> couponContentses, String money) {
        List<CouponContents> resultList = new ArrayList<>();
        canPayList = new ArrayList<>();
        canNotPayList = new ArrayList<>();
        for (CouponContents couponContents : couponContentses) {
            if (iscanUse(couponContents)) {//时间可用
                CouponContents.CouponDetails couponDetails = couponContents.getCouponDetailses().get(0);
                if (TextUtils.equals(Constant.DiscountTypeDiscount, couponDetails.getDiscountType()) || Double.parseDouble(money) >= Double.parseDouble(couponDetails.getAmount())/*money.compareTo(couponDetails.getAmount()) >= 0*/) {
                    couponContents.setTag("1");
                    canPayList.add(couponContents);
                } else {
                    couponContents.setTag("0");
                    canNotPayList.add(couponContents);
                }
            } else {//时间不可用
                couponContents.setTag("0");
                canNotPayList.add(couponContents);
            }
        }
        canPayList = sortCanPayList(canPayList);
        resultList.addAll(canPayList);
        resultList.addAll(canNotPayList);
        return resultList;
    }


    //下单页的红包
    private List<CouponContents> payCanPayList;

    private List<CouponContents> paySortNoUsedRedPacket(List<CouponContents> couponContentses, String money) {
        List<CouponContents> resultList = new ArrayList<>();
        payCanPayList = new ArrayList<>();
        for (CouponContents couponContents : couponContentses) {
            if (iscanUse(couponContents) && couponContents.getCouponDetailses() != null && !couponContents.getCouponDetailses().isEmpty()) {//时间可用
                CouponContents.CouponDetails couponDetails = couponContents.getCouponDetailses().get(0);
                if (TextUtils.equals(Constant.DiscountTypeDiscount, couponDetails.getDiscountType()) || Double.parseDouble(money) >= Double.parseDouble(couponDetails.getAmount())/*money.compareTo(couponDetails.getAmount()) >= 0*/) {
                    couponContents.setTag("1");
                    payCanPayList.add(couponContents);
                }
            }
        }
        payCanPayList = sortCanPayList(payCanPayList);
        resultList.addAll(payCanPayList);
        return resultList;
    }

    /**
     * 是否是相对应的serviceId的服务
     *
     * @return
     */

    private boolean correspondingService(List<CouponContents.MyServiceType> serviceTypes, String serviceId) {
        for (int i = 0; i < serviceTypes.size(); i++) {
            if (serviceTypes.get(i) != null && !TextUtils.isEmpty(serviceTypes.get(i).getServiceId()) && TextUtils.equals(serviceTypes.get(i).getServiceId(), serviceId)) {
                return true;
            }
        }
        return false;
    }

    private List<CouponContents> sortCanPayList(List<CouponContents> couponContentses) {
        final String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        Collections.sort(couponContentses, new Comparator<CouponContents>() {
            @Override
            public int compare(CouponContents lhs, CouponContents rhs) {//目前每张优惠券就一个优惠条件没有多重
                CouponContents.CouponDetails lhsDt = lhs.getCouponDetailses().get(0);
                CouponContents.CouponDetails rhsDt = rhs.getCouponDetailses().get(0);
                if (TextUtils.equals(Constant.DiscountTypeFullCut, lhsDt.getDiscountType())) {
                    if (TextUtils.equals(Constant.DiscountTypeFullCut, rhsDt.getDiscountType())) {
                        int result = rhsDt.getDiscountAmount().compareTo(lhsDt.getDiscountAmount());
                        if (result == 0) {
                            if (!TextUtils.isEmpty(lhs.getEndTime()) && !TextUtils.isEmpty(rhs.getEndTime()))//当优惠金额一样时优惠券有效截止日期距离当前时间最近在前面
                                return Math.abs(lhs.getEndTime().compareTo(curtime)) - Math.abs(rhs.getEndTime().compareTo(curtime));
                            else //当没有时间限制时候
                                return 0;
                        } else
                            return result;
                    } else {
                        double discount = Double.valueOf(rhsDt.getDiscount()) * Double.valueOf(orderMoney);
                        int result = Double.toString(discount).compareTo(lhsDt.getDiscountAmount());
                        if (result == 0) {
                            if (!TextUtils.isEmpty(lhs.getEndTime()) && !TextUtils.isEmpty(rhs.getEndTime()))//当优惠金额一样时优惠券有效截止日期距离当前时间最近在前面
                                return Math.abs(lhs.getEndTime().compareTo(curtime)) - Math.abs(rhs.getEndTime().compareTo(curtime));
                            else //当没有时间限制时候
                                return 0;
                        } else
                            return result;
                    }
                } else {
                    double discount = Double.valueOf(lhsDt.getDiscount()) * Double.valueOf(orderMoney);
                    if (TextUtils.equals(Constant.DiscountTypeFullCut, rhsDt.getDiscountType())) {
                        int result = rhsDt.getDiscountAmount().compareTo(Double.toString(discount));
                        if (result == 0) {
                            if (!TextUtils.isEmpty(lhs.getEndTime()) && !TextUtils.isEmpty(rhs.getEndTime()))//当优惠金额一样时优惠券有效截止日期距离当前时间最近在前面
                                return Math.abs(lhs.getEndTime().compareTo(curtime)) - Math.abs(rhs.getEndTime().compareTo(curtime));
                            else //当没有时间限制时候
                                return 0;
                        } else
                            return result;
                    } else {
                        double discount2 = Double.valueOf(rhsDt.getDiscount()) * Double.valueOf(orderMoney);
                        int result = Double.toString(discount2).compareTo(Double.toString(discount));
                        if (result == 0) {
                            if (!TextUtils.isEmpty(lhs.getEndTime()) && !TextUtils.isEmpty(rhs.getEndTime()))//当优惠金额一样时优惠券有效截止日期距离当前时间最近在前面
                                return Math.abs(lhs.getEndTime().compareTo(curtime)) - Math.abs(rhs.getEndTime().compareTo(curtime));
                            else //当没有时间限制时候
                                return 0;
                        } else
                            return result;
                    }
                }
            }
        });

        return couponContentses;
    }


    private boolean isOutofDate(String timeStamp) {
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        if (!TextUtils.isEmpty(timeStamp) && timeStamp.compareTo(curtime) <= 0)
            return true;
        else
            return false;
    }

    private List<CouponContents> getNoUsedCouponList(CouponContentsInfo couponContentsInfo) {
        List<CouponContents> noUsedCouponList = new ArrayList<>();
        if (couponContentsInfo != null) {
            for (CouponContents couponContents : couponContentsInfo.getCouponContentsList()) {
                if (couponContents != null) {
                    if (!couponContents.isUsed() && !isOutofDate(couponContents.getEndTime())) {
                        noUsedCouponList.add(couponContents);
                    }
                }
            }
        }
        return noUsedCouponList;
    }

    private List<CouponContents> getUsedCouponList(CouponContentsInfo couponContentsInfo) {
        List<CouponContents> usedCouponList = new ArrayList<>();
        for (CouponContents couponContents : couponContentsInfo.getCouponContentsList()) {
            if (couponContents.isUsed()) {
                usedCouponList.add(couponContents);
            }
        }
        return usedCouponList;
    }

    private List<CouponContents> getOutofDateCouponList(CouponContentsInfo couponContentsInfo) {
        List<CouponContents> outofDateCouponList = new ArrayList<>();
        for (CouponContents couponContents : couponContentsInfo.getCouponContentsList()) {
            if (!couponContents.isUsed() && isOutofDate(couponContents.getEndTime())) {
                outofDateCouponList.add(couponContents);
            }
        }
        return outofDateCouponList;
    }

    public void getCouponList() {
        //   couponContentsView.showLoading();
        getCouponContentsInteractor.execute(new OldBaseObserver<CouponContentsInfo>(couponContentsView) {
            @Override
            public void onNext(CouponContentsInfo couponContentsInfo) {
//                List<CouponContents> results = CouponUtils.sortCoupons(couponContentsInfo.getCouponContentsList());
                couponContentsView.render(couponContentsInfo.getCouponContentsList());
            }
        });
    }

    public void getPayCouponList(String money, String serviceId) {//0：未使用；1：已使用；2：已过期;3:使用列表红包；
        orderMoney = money;
        couponContentsView.showLoading();
        getCouponContentsInteractor.setServiceId(serviceId);
        getCouponContentsInteractor.setIspay("1");
        getCouponContentsInteractor.execute(new OldBaseObserver<CouponContentsInfo>(couponContentsView) {
            @Override
            public void onNext(CouponContentsInfo couponContentsInfo) {
                if (!TextUtils.isEmpty(orderMoney)) {
                    couponContentsView.render(paySortNoUsedRedPacket(getNoUsedCouponList(couponContentsInfo), orderMoney));
                }
            }
        });
    }


    private int couponStatus;

    public void getCouponList(int status, String money, final String serviceId, String longitude, String latitude, boolean ispay) {//0：未使用；1：已使用；2：已过期;3:使用列表红包；
        couponStatus = status;
        orderMoney = money;
        couponContentsView.showLoading();
        getCouponContentsInteractor.setServiceId(serviceId);
        getCouponContentsInteractor.setLatitude(latitude);
        getCouponContentsInteractor.setLongitude(longitude);
        if (ispay) {
            getCouponContentsInteractor.setIspay("1");
        } else {
            getCouponContentsInteractor.setIspay("0");
        }
        getCouponContentsInteractor.execute(new OldBaseObserver<CouponContentsInfo>(couponContentsView) {
            @Override
            public void onNext(CouponContentsInfo couponContentsInfo) {
                if (couponStatus == 0) {
                    couponContentsView.render(getNoUsedCouponList(couponContentsInfo));
                } else if (couponStatus == 1) {
                    couponContentsView.render(getUsedCouponList(couponContentsInfo));
                } else if (couponStatus == 2) {
                    couponContentsView.render(getOutofDateCouponList(couponContentsInfo));
                } else {
                    if (orderMoney != null) {
                        couponContentsView.render(sortNoUsedRedPacket(getNoUsedCouponList(couponContentsInfo), orderMoney));
                        couponContentsView.renderCount(canPayList.size());
                    }
                }
            }
        });
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getCouponContentsInteractor.unsubscribe();
    }
}
