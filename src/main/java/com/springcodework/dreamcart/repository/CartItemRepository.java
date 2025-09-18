package com.springcodework.dreamcart.repository;

import com.springcodework.dreamcart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository <CartItem, Long> {
    // Custom delete query based on cartId
    void deleteAllByCartId(Long cartId);
}
