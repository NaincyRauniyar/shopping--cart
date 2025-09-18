package com.springcodework.dreamcart.service.cart;

import com.springcodework.dreamcart.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();
    Cart getCartByUserId(Long userId);
}
