package com.springcodework.dreamcart.service.order;

import com.springcodework.dreamcart.enums.OrderStatus;
import com.springcodework.dreamcart.exceptions.ResourceNotFoundException;
import com.springcodework.dreamcart.model.Cart;
import com.springcodework.dreamcart.model.Order;
import com.springcodework.dreamcart.model.OrderItem;
import com.springcodework.dreamcart.model.Product;
import com.springcodework.dreamcart.repository.OrderRepository;
import com.springcodework.dreamcart.repository.ProductRepository;
import com.springcodework.dreamcart.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements  IOrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculatedTotalAmount(orderItemList));
        Order saveOrder = orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
      return  cart.getItems().stream().map(cartItem ->{
          Product product =cartItem.getProduct();
          product.setInventory(product.getInventory() - cartItem.getQuantity());
          productRepository.save(product);
          return new OrderItem(
                  order,
                  product,
                  cartItem.getQuantity(),
                  cartItem.getUnitPrice());
      }).toList();
    }

    private BigDecimal calculatedTotalAmount(List<OrderItem> orderItemList){
        return orderItemList
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal :: add);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()-> new ResourceNotFoundException("order not found"));
    }
    @Override
    public List<Order> getUserOrders(Long userId){
        return orderRepository.findByUserId(userId);

    }
}
