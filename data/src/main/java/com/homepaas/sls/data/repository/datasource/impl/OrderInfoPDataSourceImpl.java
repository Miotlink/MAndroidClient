package com.homepaas.sls.data.repository.datasource.impl;

import android.text.TextUtils;

import com.homepaas.sls.domain.entity.OrderEntity;
import com.homepaas.sls.data.entity.mapper.OrderEntityMapper;
import com.homepaas.sls.data.exception.InvalidPersistenceDataException;
import com.homepaas.sls.data.repository.datasource.OrderInfoPDataSource;
import com.homepaas.sls.domain.entity.Order;
import com.homepaas.sls.domain.entity.OrderInfo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by CMJ on 2016/6/21.
 */
@Singleton
public class OrderInfoPDataSourceImpl implements OrderInfoPDataSource{


    OrderEntityMapper mapper;
    Dao<OrderEntity,String> dao;

    @Inject
    public OrderInfoPDataSourceImpl(OrderEntityMapper mapper, Dao<OrderEntity, String> dao) {
        this.mapper = mapper;
        this.dao = dao;
    }

    /**
     * 获取所有订单
     */
    public List<Order> getAllOrder() throws InvalidPersistenceDataException {
        List<Order> list = new ArrayList<>();
        try {
            List<OrderEntity> orderEntities = dao.queryForAll();
            if (orderEntities == null||orderEntities.isEmpty())
                throw new InvalidPersistenceDataException("木有本地订单");
            list.addAll(mapper.transformEntities(orderEntities));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取待付款订单
     * @return
     */
    public List<Order> getToPayOrder() throws InvalidPersistenceDataException {
        List<Order> results = new ArrayList<>();
        List<Order> allOrder = getAllOrder();
        for (Order item:allOrder
             ) {
            if (TextUtils.equals(item.getIsPayOff(),"0"))
            {
                results.add(item);
            }
        }
        return results;
    }

    /**
     * 获取待确认的订单
     * @return
     */
   public List<Order> getToConfirmOrder() throws InvalidPersistenceDataException {
       List<Order> results = new ArrayList<>();
       List<Order> allOrder = getAllOrder();

       for (Order item :
               allOrder) {
           if (Integer.valueOf(item.getOrderStatus()) == 2)
               results.add(item);
       }

       return results;
    }

    /**
     *获取待评价的订单
     * @return
     */
    public List<Order> getToEvaluateOrder() throws InvalidPersistenceDataException {
        List<Order> results = new ArrayList<>();
        List<Order> allOrder = getAllOrder();

        for (Order item :
                allOrder) {
            if (Integer.valueOf(item.getOrderStatus()) == 5)
                results.add(item);
        }

        return results;
    }


    /**
     * 保存所有的订单信息
     * @param allOrder
     * @return
     */
    @Override
    public int save(OrderInfo allOrder) {/*
        if (allOrder == null) return 0;
        List<OrderEntity> orderEntities = mapper.transform(allOrder.getOrders());
        if (orderEntities == null|| orderEntities.isEmpty()) return 0;
        for (OrderEntity item :
                orderEntities) {
            try {
                if (dao.idExists(item.getOrderId())){
                    dao.createOrUpdate(item);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }*/
        return 0;

    }

}
