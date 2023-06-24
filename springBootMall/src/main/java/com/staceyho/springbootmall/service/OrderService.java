package com.staceyho.springbootmall.service;

import com.staceyho.springbootmall.dto.CreateOrderRequest;
import com.staceyho.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
