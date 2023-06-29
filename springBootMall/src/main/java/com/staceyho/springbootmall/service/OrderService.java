package com.staceyho.springbootmall.service;

import com.staceyho.springbootmall.dto.CreateOrderRequest;
import com.staceyho.springbootmall.dto.OrderQueryParams;
import com.staceyho.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);
    Order getOrderById(Integer orderId);
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
