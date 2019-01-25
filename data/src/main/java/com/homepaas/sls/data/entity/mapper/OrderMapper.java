package com.homepaas.sls.data.entity.mapper;

import com.homepaas.sls.data.entity.DetailOrderEntity;
import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.domain.entity.DetailOrder;
import com.homepaas.sls.domain.entity.Order;
import com.homepaas.sls.domain.entity.OrderInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/5/3.
 */
@Singleton
public class OrderMapper {

    @Inject
    public OrderMapper() {
    }


    public OrderInfo transform(List<OrderEntity> items) {
        OrderInfo orderInfo = new OrderInfo();
        if (items == null || items.isEmpty()) return orderInfo;
        List<OrderEntity> list = new ArrayList<>();
        for (OrderEntity entity : items) {
            if (entity != null) {
//                list.add(transform(entity));
            }

        }
        orderInfo.setOrders(list);

        return orderInfo;
    }


   /* public Order transform(OrderEntity entity) {
        if (entity == null) return null;
        Order orderInfo;

        orderInfo = new Order();
        orderInfo.setOrderId(entity.getOrderId());
        orderInfo.setOrderCode(entity.getOrderCode());
        orderInfo.setCreateTime(entity.getCreateTime());
        orderInfo.setOrderStatus(entity.getOrderStatus());
        orderInfo.setIsPayOff(entity.getIsPayOff());
        orderInfo.setServiceProvidePic(entity.getServiceProvidePic());
        orderInfo.setPrice(entity.getPrice());
        orderInfo.setDiscountInfo(entity.getDiscountInfo());
        orderInfo.setPayLock(entity.getPayLock());
        orderInfo.setServiceProviderId(entity.getServiceProviderId());
        orderInfo.setServiceProvideType(entity.getServiceProvideType());
        orderInfo.setServiceProviderName(entity.getServiceProviderName());
        orderInfo.setService(transform(entity.getService()));
        orderInfo.setIsEvaluated(entity.getIsEvaluted());
        return orderInfo;
    }

    public Order.OrderService transform(OrderEntity.OrderService entity) {
        if (entity == null) return null;
        Order.OrderService orderServcie;
        orderServcie = new Order.OrderService();
        orderServcie.setServiceTypeId(entity.getServiceTypeId());
        orderServcie.setServiceName(entity.getServiceName());
        orderServcie.setContent(entity.getContent());
        orderServcie.setPictures(trans(entity.getPictures()));

        return orderServcie;
    }

    public List<Order.OrderService.Picture> trans(List<OrderEntity.OrderService.Picture> entities) {
        if (entities == null || entities.isEmpty()) return null;
        List<Order.OrderService.Picture> pictures;

        pictures = new ArrayList<>();
        Order.OrderService.Picture pic;
        for (OrderEntity.OrderService.Picture entity : entities
                ) {
            pic = new Order.OrderService.Picture();
            pic.setHqPic(entity.getHqPic());
            pic.setSmallPic(entity.getSmallPic());
            pic.setPictureId(entity.getPictureId());
            pictures.add(pic);
        }

        return pictures;
    }*/

    public DetailOrder transform(DetailOrderEntity entity) {
        DetailOrder detailOrder = new DetailOrder();
        if (entity == null) return detailOrder;

        detailOrder.setAcceptTime(entity.acceptTime);
        detailOrder.setConfirmTime(entity.confirmTime);
        detailOrder.setCancelTime(entity.cancelTime);
        detailOrder.setFinishTime(entity.finishTime);
        detailOrder.setCreateTime(entity.createTime);
        detailOrder.setServiceProviderName(entity.serviceProviderName);
        detailOrder.setServiceProvidePic(entity.serviceProvidePic);
        detailOrder.setServiceProviderScore(entity.serviceProviderScore);
        detailOrder.setDiscountInfo(entity.discountInfo);
        detailOrder.setOrderStatus(entity.orderStatus);
        detailOrder.setOrderCode(entity.orderCode);
        detailOrder.setOrderId(entity.orderId);

        /*DetailOrder---Evaluation*/
        DetailOrder.Evaluation evaluation = new DetailOrder.Evaluation();
        DetailOrderEntity.Evaluation evaluation1 = new DetailOrderEntity.Evaluation();
            /*DetailOrder---Evaluation-----AdditionalEvaluation*/
        DetailOrder.AdditionalEvaluation additionalEvaluation = new DetailOrder.AdditionalEvaluation();
        DetailOrderEntity.AdditionalEvaluation additionalEvaluation1 = entity.evaluation.additionalEvaluation;
        additionalEvaluation.setContent(additionalEvaluation1.content);
        additionalEvaluation.setDate(additionalEvaluation1.date);
        evaluation.setAdditionalEvaluation(additionalEvaluation);
        evaluation.setDate(evaluation1.date);
        evaluation.setContent(evaluation1.content);
        evaluation.setClientPic(evaluation1.clientPic);
        evaluation.setClientName(evaluation1.clientName);
        evaluation.setId(evaluation1.id);
                /*DetailOrder--Evaluation---Replay*/
        DetailOrder.Reply reply = new DetailOrder.Reply();
        DetailOrderEntity.Reply reply1 = entity.evaluation.reply;
        if (reply1 != null) {
            reply.setContent(reply1.content);
            reply.setDate(reply1.date);
        }
        evaluation.setReply(reply);
        List<DetailOrder.Picture> pics = new ArrayList<>();
        List<DetailOrderEntity.Picture> pics1 = evaluation1.pictures;
        if (pics1 != null) {
            for (DetailOrderEntity.Picture item : pics1) {
                DetailOrder.Picture pic = new DetailOrder.Picture();
                pic.setPictureId(item.pictureId);
                pic.setHqPic(item.hqPic);
                pic.setSmallPIc(item.smallPIc);
                pics.add(pic);
            }
        }
        evaluation.setPictures(pics);
        detailOrder.setEvaluation(evaluation);


        /*DetailOrder---Service*/
        DetailOrder.OrderService service = new DetailOrder.OrderService();
        DetailOrderEntity.OrderService sService = new DetailOrderEntity.OrderService();
        service.setPrice(sService.price);
        service.setServiceName(sService.serviceName);
        service.setContent(sService.content);
        service.setServiceId(sService.serviceId);


            /*DetailOrder----Service---List<Pictures>*/
        List<DetailOrder.Picture> pictures = new ArrayList<>();
        List<DetailOrderEntity.Picture> pictures1 = sService.pictures;
        if (pictures1 != null) {
            for (DetailOrderEntity.Picture item : pictures1) {
                DetailOrder.Picture pic = new DetailOrder.Picture();
                pic.setPictureId(item.pictureId);
                pic.setHqPic(item.hqPic);
                pic.setSmallPIc(item.smallPIc);
                pictures.add(pic);
            }
        }
        service.setPictures(pictures);
        detailOrder.setService(service);

        return detailOrder;

    }
}
