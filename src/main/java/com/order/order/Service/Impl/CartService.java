package com.order.order.Service.Impl;

import com.order.order.Dto.CartItemDTO;
import com.order.order.Model.CartItem;

import java.util.List;

public interface CartService {
    CartItem addToCart(Long customerId, Long restaurantId, CartItemDTO cartItemDTO);
    List<CartItem> getCartItems(Long customerId);
    List<CartItem> getCartItemsByRestaurant(Long customerId, Long restaurantId);
    CartItem updateCartItem(Long cartItemId, CartItemDTO cartItemDTO);
    CartItem updateCartItemQuantity(Long cartItemId, Integer quantity);
    void removeFromCart(Long cartItemId);
    void clearCart(Long customerId, Long restaurantId);
    void deactivateCartItems(Long customerId, Long restaurantId);
} 