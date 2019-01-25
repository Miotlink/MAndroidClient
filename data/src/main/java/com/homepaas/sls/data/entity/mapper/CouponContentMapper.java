package com.homepaas.sls.data.entity.mapper;

import android.nfc.NfcEvent;

import com.homepaas.sls.data.entity.CouponContentsEntity;
import com.homepaas.sls.domain.entity.CouponContents;
import com.homepaas.sls.domain.entity.CouponContentsInfo;
import com.homepaas.sls.domain.entity.ServiceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/6/23.
 */
@Singleton
public class CouponContentMapper {

    @Inject
    public CouponContentMapper() {
    }

    private List<CouponContents.MyServiceType> createMyServiceType(List<CouponContents.MyServiceType> myServiceTypes, Collection<CouponContentsEntity.MyServiceType> myServiceEntityTypes) {
        if (myServiceEntityTypes != null) {
            for (CouponContentsEntity.MyServiceType myServiceType : myServiceEntityTypes) {
                CouponContents.MyServiceType myServiceType1 = new CouponContents.MyServiceType();
                myServiceType1.setServiceId(myServiceType.getServiceId());
                myServiceType1.setServiceName(myServiceType.getServiceName());
                myServiceType1.setTypeLogo(myServiceType.getTypeLogo());
                myServiceType1.setTypeId(myServiceType.getTypeId());
                myServiceType1.setTypeName(myServiceType.getTypeName());
                if (myServiceType.getServicePrice() != null){
                    CouponContents.ServicePrice servicePrice = new CouponContents.ServicePrice();
                    servicePrice.setIsNegotiable(myServiceType.getServicePrice().getIsNegotiable());
                    servicePrice.setMax(myServiceType.getServicePrice().getMax());
                    servicePrice.setMin(myServiceType.getServicePrice().getMin());
                    servicePrice.setPriceList(myServiceType.getServicePrice().getPriceList());
                    servicePrice.setServiceTypeId(myServiceType.getServicePrice().getServiceTypeId());
                    servicePrice.setStartingPrice(myServiceType.getServicePrice().getStartingPrice());
                    servicePrice.setUnit(myServiceType.getServicePrice().getUnit());
                    servicePrice.setUnitName(myServiceType.getServicePrice().getUnitName());
                    myServiceType1.setServicePrice(servicePrice);
                }
                if (myServiceType.getChildren() != null){
                    List<CouponContents.MyServiceType> myServiceTypesChildren = new ArrayList<>();
                    myServiceType1.setChildren(createMyServiceType(myServiceTypesChildren,myServiceType.getChildren()));
                }
                    myServiceTypes.add(myServiceType1);
            }
        }
        return myServiceTypes;
    }

    public CouponContents transform (CouponContentsEntity couponContentsEntity) {
        CouponContents  couponContents = null;
        if (couponContentsEntity != null) {
            couponContents = new CouponContents();
            couponContents.setStart(couponContentsEntity.getStart());
            couponContents.setSeq(couponContentsEntity.getSeq());
            couponContents.setCouponType(couponContentsEntity.getCouponType());
            couponContents.setId(couponContentsEntity.getId());
            couponContents.setTitle(couponContentsEntity.getTitle());
            couponContents.setUserId(couponContentsEntity.getUserId());
            couponContents.setIsUsed(couponContentsEntity.getIsUsed());
            couponContents.setCreateTime(couponContentsEntity.getCreateTime());
            couponContents.setServiceItem(couponContentsEntity.getServiceItem());
            List<CouponContents.CouponDetails> couponDetailses = new ArrayList<>();
            if (couponContentsEntity.getCouponDetailses() != null) {
                for (CouponContentsEntity.CouponDetails couponDetails : couponContentsEntity.getCouponDetailses()) {
                    CouponContents.CouponDetails couponDetial = new CouponContents.CouponDetails();
                    couponDetial.setAmount(couponDetails.getAmount());
                    couponDetial.setDiscountAmount(couponDetails.getDiscountAmount());
                    couponDetial.setDiscount(couponDetails.getDiscount());
                    couponDetial.setDiscountType(couponDetails.getDiscountType());
                    couponDetailses.add(couponDetial);
                }
            }
            couponContents.setCouponDetailses(couponDetailses);
            couponContents.setPlatFormString(couponContentsEntity.getPlatFormString());
            couponContents.setStartTime(couponContentsEntity.getStartTime());
            couponContents.setEndTime(couponContentsEntity.getEndTime());
            List<CouponContents.MyServiceType> myServiceTypes = new ArrayList<>();
//            if (couponContentsEntity.getMyServiceTypes() != null) {
//                for (CouponContentsEntity.MyServiceType myServiceType : couponContentsEntity.getMyServiceTypes()) {
//                    CouponContents.MyServiceType myServiceType1 = new CouponContents.MyServiceType();
//                    myServiceType1.setServiceTypeId(myServiceType.getServiceTypeId());
//                    myServiceType1.setServiceName(myServiceType.getServiceName());
//                    myServiceType1.setTypeLogo(myServiceType.getTypeLogo());
//                    myServiceType1.setTypeId(myServiceType.getTypeId());
//                    myServiceType1.setTypeName(myServiceType.getTypeName());
//                    if (myServiceType.getServicePrice() != null){
//                        CouponContents.ServicePrice servicePrice = new CouponContents.ServicePrice();
//                        servicePrice.setIsNegotiable(myServiceType.getServicePrice().getIsNegotiable());
//                        servicePrice.setMax(myServiceType.getServicePrice().getMax());
//                        servicePrice.setMin(myServiceType.getServicePrice().getMin());
//                        servicePrice.setPriceList(myServiceType.getServicePrice().getPriceList());
//                        servicePrice.setServiceTypeId(myServiceType.getServicePrice().getServiceTypeId());
//                        servicePrice.setStartingPrice(myServiceType.getServicePrice().getStartingPrice());
//                        servicePrice.setUnit(myServiceType.getServicePrice().getUnit());
//                        servicePrice.setUnitName(myServiceType.getServicePrice().getUnitName());
//                        myServiceType1.setServicePrice(servicePrice);
//                    }
//                    myServiceTypes.add(myServiceType1);
//                }
//            }

            couponContents.setMyServiceTypes(createMyServiceType(myServiceTypes,couponContentsEntity.getMyServiceTypes()));
        }

        return couponContents;
    }

    public CouponContentsInfo transform(List<CouponContentsEntity> coupomContentEntities) {
        CouponContentsInfo couponContentsInfo = new CouponContentsInfo();
        if (coupomContentEntities != null) {
            List<CouponContents> list = new ArrayList<>();
            for (CouponContentsEntity couponContentsEntity : coupomContentEntities) {
                list.add(transform(couponContentsEntity));
            }
            couponContentsInfo.setCouponContentsList(list);
        }
        else {
            couponContentsInfo.setCouponContentsList(new ArrayList<CouponContents>());
        }
        return couponContentsInfo;
    }
}
