package com.springcodework.dreamcart.service.cart;

import com.springcodework.dreamcart.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId,Long productId,int quantity);
    void removeItemFromCart(Long cartId,Long productId);
    void updateItemQuantity(Long cartId , Long productId , int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
