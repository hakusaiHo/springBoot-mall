package com.staceyho.springbootmall.service.impl;

import com.staceyho.springbootmall.dao.OrderDao;
import com.staceyho.springbootmall.dao.ProductDao;
import com.staceyho.springbootmall.dao.UserDao;
import com.staceyho.springbootmall.dto.BuyItem;
import com.staceyho.springbootmall.dto.CreateOrderRequest;
import com.staceyho.springbootmall.model.Order;
import com.staceyho.springbootmall.model.OrderItem;
import com.staceyho.springbootmall.model.Product;
import com.staceyho.springbootmall.model.User;
import com.staceyho.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public Order getOrderById(Integer orderId) {

        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        User user = userDao.getUserById(userId);

        if (user == null){

            log.warn("該UserId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            //檢查product 是否存在，庫存是否足夠
            if (product == null){
             log.warn("該商品 {} 不存在", buyItem.getProductId());
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
             
            } else if (product.getStock() < buyItem.getQuantity()) {

                log.warn("該商品 {}.{} 數量或庫存不足，無法購買。剩餘庫存為 {} ",
                        buyItem.getProductId(),product.getProductName(), product.getStock());
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //成功購買product並扣除商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            //計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            //轉換BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        //創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
