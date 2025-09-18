package com.springcodework.dreamcart.service.order;

import com.springcodework.dreamcart.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}
