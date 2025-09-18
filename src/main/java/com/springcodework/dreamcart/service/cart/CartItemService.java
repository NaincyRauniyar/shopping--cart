package com.springcodework.dreamcart.service.cart;

import com.springcodework.dreamcart.exceptions.ResourceNotFoundException;
import com.springcodework.dreamcart.model.Cart;
import com.springcodework.dreamcart.model.CartItem;
import com.springcodework.dreamcart.model.Product;
import com.springcodework.dreamcart.repository.CartItemRepository;
import com.springcodework.dreamcart.repository.CartRepository;
import com.springcodework.dreamcart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());

        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);

        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);

        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);

        cartItemRepository.delete(itemToRemove); //  delete row
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(cartItem.getProduct().getPrice());
        cartItem.setTotalPrice();

        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

        // Update total amount
        BigDecimal totalAmount = cart.getItems().stream().map(cartItem :: getTotalPrice)
                                .reduce(BigDecimal.ZERO , BigDecimal::add);
        cart.setTotalAmount(totalAmount);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
