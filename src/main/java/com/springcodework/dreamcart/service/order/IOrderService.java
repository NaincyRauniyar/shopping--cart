package com.springcodework.dreamcart.service.order;

import com.springcodework.dreamcart.dto.OrderDto;
import com.springcodework.dreamcart.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
