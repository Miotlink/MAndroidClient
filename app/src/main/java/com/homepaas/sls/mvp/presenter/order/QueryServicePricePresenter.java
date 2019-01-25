package com.homepaas.sls.mvp.presenter.order;


import android.text.TextUtils;

import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.CommonCouponEntity;
import com.homepaas.sls.domain.entity.CommonCouponInfo;
import com.homepaas.sls.domain.entity.PlaceOrderEntry;
import com.homepaas.sls.domain.entity.QueryServicePriceEntry;
import com.homepaas.sls.domain.entity.ServiceTimeStartAtEntry;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.param.PlaceOrderParam;
import com.homepaas.sls.domain.repository.CommonCouponInfoRepo;
import com.homepaas.sls.domain.repository.GetActivityExRepo;
import com.homepaas.sls.domain.repository.PlaceOrderRepo;
import com.homepaas.sls.domain.repository.QueryActivityServicePriceRepo;
import com.homepaas.sls.domain.repository.QueryServicePriceRepo;
import com.homepaas.sls.domain.repository.ServiceTimeStartAtRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.order.QueryServicePriceView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by JWC on 2017/3/27.
 */

public class QueryServicePricePresenter implements Presenter {

    private QueryServicePriceView queryServicePriceView;

    public void setQueryServicePriceView(QueryServicePriceView queryServicePriceView) {
        this.queryServicePriceView = queryServicePriceView;
    }

    @Inject
    QueryServicePriceRepo queryServicePriceRepo;
    @Inject
    ServiceTimeStartAtRepo serviceTimeStartAtRepo;
    @Inject
    PlaceOrderRepo placeOrderRepo;
    @Inject
    GetActivityExRepo getActivityExRepo;
    @Inject
    CommonCouponInfoRepo commonCouponInfoRepo;  //红包
    @Inject
    QueryActivityServicePriceRepo queryActivityServicePriceRepo;

    /**
     * 正常获取服务价格
     *
     * @param serviceId
     * @param longitude
     * @param latitude  serviceLevel，2：二级，3：三级；如果为空，则默认为二级
     *                  //                    ProviderId，工人或者商户的Id
     *                  //                    ProviderUserId，类型，2：工人，3：商户
     */
    public void getQueryServicePrice(String serviceId, String longitude, String latitude, String serviceLevel, String providerId, String providerUserType) {
        queryServicePriceRepo.getQueryServicePrice(serviceId, longitude, latitude, serviceLevel,
                providerId, providerUserType)
                .compose(RxUtil.<QueryServicePriceEntry>applySchedulers())
                .subscribe(new OldBaseObserver<QueryServicePriceEntry>(queryServicePriceView) {
                    @Override
                    public void onNext(QueryServicePriceEntry queryServicePriceEntry) {
                        queryServicePriceView.renderServices(queryServicePriceEntry);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (t instanceof GetDataException) {
                            GetDataException getDataException = (GetDataException) t;
                            //该商家无法服务此地址，请选择其他服务商 服务器返回错误码 10
                            if (!TextUtils.isEmpty(getDataException.getErrorCode()) && getDataException.getErrorCode().equals("10")) {
                                queryServicePriceView.addressError();
                            }
                        }
                        queryServicePriceView.netWrokError();
                    }
                });
    }

    /**
     * 活动下单获取服务价格
     *
     * @param activityProductId
     * @param longitude
     * @param latitude
     */
    public void getQueryActivityServicePrice(String activityProductId, String longitude, String latitude) {
        queryActivityServicePriceRepo.getQueryActivityServicePrice(activityProductId, longitude, latitude)
                .compose(RxUtil.<QueryServicePriceEntry>applySchedulers())
                .subscribe(new OldBaseObserver<QueryServicePriceEntry>(queryServicePriceView) {
                    @Override
                    public void onNext(QueryServicePriceEntry queryServicePriceEntry) {
                        queryServicePriceView.renderServices(queryServicePriceEntry);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (t instanceof GetDataException) {
                            GetDataException getDataException = (GetDataException) t;
                            //该商家无法服务此地址，请选择其他服务商 服务器返回错误码 10
                            if (TextUtils.isEmpty(getDataException.getErrorCode()) && getDataException.getErrorCode().equals("10")) {
                                queryServicePriceView.addressError();
                            }
                        }
                        queryServicePriceView.netWrokError();
                    }
                });
    }

    /**
     * 获取对应服务的服务时间
     *
     * @param serviceId
     * @param longitude
     * @param latitude
     */
    public void getServiceTime(String serviceId, String longitude, String latitude) {
        serviceTimeStartAtRepo.getServiceTimeStartAt(serviceId, longitude, latitude)
                .compose(RxUtil.<ServiceTimeStartAtEntry>applySchedulers())
                .subscribe(new OldBaseObserver<ServiceTimeStartAtEntry>(queryServicePriceView) {
                    @Override
                    public void onNext(ServiceTimeStartAtEntry serviceTimeStartAtEntry) {
                        queryServicePriceView.renderServiceTime(serviceTimeStartAtEntry);
                    }
                });
    }

    /**
     * 获取活动
     *
     * @param serviceId
     */
    public void getAvailableActivity(String serviceId) {
        getActivityExRepo.getGetActivityEx(serviceId)
                .compose(RxUtil.<ActivityNgInfoNew>applySchedulers())
                .subscribe(new OldBaseObserver<ActivityNgInfoNew>(queryServicePriceView) {
                    @Override
                    public void onNext(ActivityNgInfoNew activityNgInfoNew) {
                        queryServicePriceView.renderActivity(activityNgInfoNew);
                    }
                });
    }


    /**
     * 获取红包
     */
    public void getPlaceOrderCoupon(String serviceId, String longitude, String latitude, String isPay) {
        commonCouponInfoRepo.getCommonCouponInfo(serviceId, longitude, latitude, isPay)
                .compose(RxUtil.<CommonCouponInfo>applySchedulers())
                .subscribe(new OldBaseObserver<CommonCouponInfo>(queryServicePriceView) {
                    @Override
                    public void onNext(CommonCouponInfo commonCouponInfo) {
                        queryServicePriceView.renderCouponInfo(commonCouponInfo);
                    }
                });
    }


    /**
     * 提交订单
     *
     * @param placeOrderParam
     */
    public void getPlaceOrder(PlaceOrderParam placeOrderParam) {
        queryServicePriceView.showLoading();
        placeOrderRepo.getPlaceOrdre(placeOrderParam)
                .compose(RxUtil.<PlaceOrderEntry>applySchedulers())
                .subscribe(new OldBaseObserver<PlaceOrderEntry>(queryServicePriceView) {
                    @Override
                    public void onNext(PlaceOrderEntry placeOrderEntry) {
                        queryServicePriceView.renderPlaceOrder(placeOrderEntry);
                    }
                });
    }

    /**
     * 符合指定服务的最大红包
     */
    public CommonCouponEntity getServiceBigCoupon(List<CommonCouponEntity> commonCouponEntityList) {
        CommonCouponEntity resultCoupon = null;
        if (commonCouponEntityList != null) {
            for (CommonCouponEntity commonCouponEntity : commonCouponEntityList) {
                if (commonCouponEntity != null) {
                    resultCoupon = bigCoupon(resultCoupon, commonCouponEntity);
                }
            }
        }
        return resultCoupon;
    }

    /**
     * 比较优惠大小 选出最大的
     *
     * @param lastCoupon
     * @param currCoupon
     * @return
     */
    private CommonCouponEntity bigCoupon(CommonCouponEntity lastCoupon, CommonCouponEntity currCoupon) {
        if (lastCoupon == null) {
            return currCoupon;
        } else {
            if (lastCoupon.getCouponDetailses() != null && currCoupon.getCouponDetailses() != null) {
                CommonCouponEntity.CouponDetails lastDetail = lastCoupon.getCouponDetailses().get(0);
                CommonCouponEntity.CouponDetails currDetail = currCoupon.getCouponDetailses().get(0);
                if (lastDetail != null && currDetail != null) {
                    if (!TextUtils.isEmpty(lastDetail.getDiscountAmount()) && !TextUtils.isEmpty(currDetail.getDiscountAmount())) {
                        if (Double.parseDouble(lastDetail.getDiscountAmount()) - Double.parseDouble(currDetail.getDiscountAmount()) <= 0) {
                            return currCoupon;
                        } else {
                            return lastCoupon;
                        }
                    } else if (TextUtils.isEmpty(lastDetail.getDiscountAmount()) && !TextUtils.isEmpty(currDetail.getDiscountAmount())) {
                        return currCoupon;
                    } else if (!TextUtils.isEmpty(lastDetail.getDiscountAmount()) && TextUtils.isEmpty(currDetail.getDiscountAmount())) {
                        return lastCoupon;
                    } else {
                        return currCoupon;
                    }
                } else if (lastDetail == null && currDetail != null) {
                    return currCoupon;
                } else if (lastDetail != null && currDetail == null) {
                    return lastCoupon;
                } else {
                    return currCoupon;
                }
            } else if (lastCoupon.getCouponDetailses() == null && currCoupon.getCouponDetailses() != null) {
                return currCoupon;
            } else if (lastCoupon.getCouponDetailses() != null && currCoupon.getCouponDetailses() == null) {
                return lastCoupon;
            } else {
                return currCoupon;
            }
        }
    }


    @Inject
    public QueryServicePricePresenter() {
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
