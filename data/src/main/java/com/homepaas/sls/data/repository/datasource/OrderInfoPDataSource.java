package com.homepaas.sls.data.repository.datasource;

import com.homepaas.sls.data.exception.InvalidPersistenceDataException;
import com.homepaas.sls.domain.entity.Order;
import com.homepaas.sls.domain.entity.OrderInfo;

import java.util.List;

/**
 * Created by CMJ on 2016/6/21.
 */

public interface OrderInfoPDataSource {

    List<Order> getAllOrder() throws InvalidPersistenceDataException;
    List<Order> getToPayOrder() throws InvalidPersistenceDataException;
    List<Order> getToConfirmOrder() throws InvalidPersistenceDataException;
    List<Order> getToEvaluateOrder() throws InvalidPersistenceDataException;
    int save(OrderInfo allOrder);
}
