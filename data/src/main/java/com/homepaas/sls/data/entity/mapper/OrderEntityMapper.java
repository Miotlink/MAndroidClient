package com.homepaas.sls.data.entity.mapper;

import com.homepaas.sls.data.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.domain.entity.DetailOrder;
import com.homepaas.sls.domain.entity.Order;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by CMJ on 2016/6/21.
 */
@Singleton
public class OrderEntityMapper {

    @Inject
    public OrderEntityMapper() {
    }

    public List<OrderEntity> transform(List<OrderEntity> list) {
        List<OrderEntity> results = new ArrayList<>();
        if (list == null || list.isEmpty()) return results;
        for (OrderEntity item :
                list) {/*
            OrderEntity order = new OrderEntity();
            if (item != null) {
                order.setCreateTime(item.getCreateTime());
                order.setDiscountInfo(item.getDiscountInfo());
                order.setIsPayOff(item.getIsPayOff());
                order.setOrderCode(item.getOrderCode());
                order.setOrderId(item.getOrderId());
                order.setOrderStatus(item.getOrderStatus());
                order.setPayLock(item.getPayLock());
                order.setPrice(item.getPrice());
                order.setServiceProviderId(item.getServiceProviderId());
                order.setServiceProviderName(item.getServiceProviderName());
                order.setServiceProvideType(item.getServiceProvideType());
                item.setServiceProvidePic(item.getServiceProvidePic());
            }

            OrderEntity.OrderService service = new OrderEntity.OrderService();
            Order.OrderService sService = item.getService();
            if (sService != null) {
                List<Order.OrderService.Picture> sPictures = sService.getPictures();
                List<OrderEntity.OrderService.Picture> pictures = new ArrayList<>();
                if (sPictures != null && !sPictures.isEmpty()) {
                    for (Order.OrderService.Picture pItem : sPictures
                            ) {
                        OrderEntity.OrderService.Picture p = new OrderEntity.OrderService.Picture();
                        p.setHqPic(pItem.getHqPic());
                        p.setPictureId(pItem.getPictureId());
                        p.setSmallPic(pItem.getSmallPic());
                        pictures.add(p);
                    }
                }
                service.setPictures(pictures);
                service.setContent(sService.getContent());
                service.setServiceTypeId(sService.getServiceTypeId());
                service.setServiceName(sService.getServiceName());
                order.setService(service);
            }
            results.add(order);*/

        }
        return results;
    }


    public List<Order> transformEntities(List<OrderEntity> list) {

        List<Order> results = new ArrayList<>();
        for (OrderEntity item :
                list) {/*
            Order order = new Order();
            order.setCreateTime(item.getCreateTime());
            order.setDiscountInfo(item.getDiscountInfo());
            order.setIsPayOff(item.getIsPayOff());
            order.setOrderCode(item.getOrderCode());
            order.setOrderId(item.getOrderId());
            order.setOrderStatus(item.getOrderStatus());
            order.setPayLock(item.getPayLock());
            order.setPrice(item.getPrice());
            order.setServiceProviderId(item.getServiceProviderId());
            order.setServiceProviderName(item.getServiceProviderName());
            order.setServiceProvideType(item.getServiceProvideType());
            order.setServiceProvidePic(item.getServiceProvidePic());

            Order.OrderService service = new Order.OrderService();
            OrderEntity.OrderService sService = item.getService();
            if (sService != null)

            {
                List<OrderEntity.OrderService.Picture> sPictures = sService.getPictures();
                List<Order.OrderService.Picture> pictures = service.getPictures();
                if (sPictures != null && !sPictures.isEmpty())
                    for (OrderEntity.OrderService.Picture pItem : sPictures
                            ) {
                        Order.OrderService.Picture p = new Order.OrderService.Picture();
                        p.setHqPic(pItem.getHqPic());
                        p.setPictureId(pItem.getPictureId());
                        p.setSmallPic(pItem.getSmallPic());
                        pictures.add(p);
                    }
                service.setContent(sService.getContent());
                service.setServiceTypeId(sService.getServiceTypeId());
                service.setServiceName(sService.getServiceName());
                order.setService(service);
            }
            results.add(order);
*/
        }
        return results;
    }


    public DetailOrder transform(DetailOrderEntity detailOrderEntity) {
        DetailOrder detailOrder = new DetailOrder();
        detailOrder.setDiscountInfo(detailOrderEntity.discountInfo);
        detailOrder.setOrderCode(detailOrderEntity.orderCode);
        detailOrder.setOrderId(detailOrderEntity.orderId);
        detailOrder.setCreateTime(detailOrderEntity.createTime);
        detailOrder.setAcceptTime(detailOrderEntity.acceptTime);
        detailOrder.setCancelTime(detailOrderEntity.cancelTime);
        detailOrder.setConfirmTime(detailOrderEntity.confirmTime);
        detailOrder.setFinishTime(detailOrderEntity.finishTime);
        detailOrder.setPrice(detailOrderEntity.price);
        detailOrder.setIsPayOff(detailOrderEntity.isPayOff);
        detailOrder.setPayLock(detailOrderEntity.price);
        detailOrder.setPayLock(detailOrderEntity.payLock);
        detailOrder.setOrderStatus(detailOrderEntity.orderStatus);
        detailOrder.setServiceProviderPhoneNumber(detailOrderEntity.getServiceProviderPhoneNumber());
        detailOrder.setServiceProviderCanCall(detailOrderEntity.serviceProviderCanCall);
        detailOrder.setCustomerServicePhoneNumber(detailOrderEntity.customerServicePhoneNumber);
        detailOrder.setServiceProvideType(detailOrderEntity.serviceProvideType);
        detailOrder.setServiceProviderId(detailOrderEntity.serviceProviderId);
        detailOrder.setServiceProviderCollection(detailOrderEntity.serviceProviderCollection);
        detailOrder.setServiceProviderName(detailOrderEntity.serviceProviderName);
        detailOrder.setServiceProvidePic(detailOrderEntity.serviceProvidePic);
        detailOrder.setServiceProviderPraise(detailOrderEntity.serviceProviderPraise);
        detailOrder.setServiceProviderScore(detailOrderEntity.serviceProviderScore);
        detailOrder.setCanCancel(detailOrderEntity.canCancel);

        /*Service*/
        DetailOrder.OrderService service = new DetailOrder.OrderService();
        DetailOrderEntity.OrderService sService = detailOrderEntity.service;
        service.setContent(sService.content);
        service.setPrice(sService.price);
        service.setServiceName(sService.serviceName);
        service.setServiceId(sService.serviceId);
        List<DetailOrder.Picture> pics = new ArrayList<>();
        if (sService.pictures != null && !sService.pictures.isEmpty()) {
            int size = sService.pictures.size();
            for (int i = 0; i < size; i++) {
                DetailOrderEntity.Picture spic = sService.pictures.get(i);
                DetailOrder.Picture pic = new DetailOrder.Picture();
                pic.setHqPic(spic.hqPic);
                pic.setSmallPIc(spic.smallPIc);
                pic.setPictureId(spic.pictureId);
                pics.add(pic);

            }
        }
        service.setPictures(pics);
        detailOrder.setService(service);

        /*Evaluation*/
        DetailOrder.Evaluation evaluation = new DetailOrder.Evaluation();
        DetailOrderEntity.Evaluation sEvaluation = detailOrderEntity.evaluation;
        if (sEvaluation != null) {
            evaluation.setContent(sEvaluation.content);
            DetailOrder.AdditionalEvaluation additionalEvaluation = new DetailOrder.AdditionalEvaluation();
            DetailOrderEntity.AdditionalEvaluation sadditionalEvaluation1 = sEvaluation.additionalEvaluation;
            additionalEvaluation.setContent(sadditionalEvaluation1.content);
            additionalEvaluation.setDate(sadditionalEvaluation1.date);
            evaluation.setAdditionalEvaluation(additionalEvaluation);

            evaluation.setClientPic(sEvaluation.clientPic);
            evaluation.setClientName(sEvaluation.clientName);
            List<DetailOrder.Picture> pictures = new ArrayList<>();
            List<DetailOrderEntity.Picture> pictures1 = sEvaluation.pictures;
            if (pictures1 != null && !pictures1.isEmpty()) {
                int sz = pictures1.size();
                for (int i = 0; i < sz; i++) {
                    DetailOrderEntity.Picture spic = pictures1.get(i);
                    DetailOrder.Picture pic = new DetailOrder.Picture();
                    pic.setHqPic(spic.hqPic);
                    pic.setSmallPIc(spic.smallPIc);
                    pic.setPictureId(spic.pictureId);
                    pictures.add(pic);
                }
            }
            evaluation.setPictures(pictures);
            /*Refund*/
        }

        if (sEvaluation != null) {
            DetailOrder.Reply reply = new DetailOrder.Reply();
            DetailOrderEntity.Reply sReply = sEvaluation.reply;
            if (sReply != null) {
                reply.setContent(sReply.content);
                reply.setDate(sReply.date);
                evaluation.setReply(reply);
            }
        }
        detailOrder.setEvaluation(evaluation);

        List<DetailOrder.Refund> refunds = new ArrayList<>();
        List<DetailOrderEntity.Refund> sRefunds = detailOrderEntity.refunds == null ? new ArrayList<DetailOrderEntity.Refund>() : detailOrderEntity.refunds;
        DetailOrder.Refund refund = null;
            for (int i = 0; i < sRefunds.size(); i++) {
                refund = new DetailOrder.Refund();
                DetailOrderEntity.Refund item = sRefunds.get(i);
                refund.lostIncome = item.lostIncome;
                refund.status = item.status;
                refund.refundAmount = item.refundAmount;
                refund.refundtime = item.refundtime;
                refund.orderId = item.orderId;
                refund.userId = item.userId;
                refunds.add(refund);
            }
        detailOrder.setRefunds(refunds);

        return detailOrder;

    }
}
