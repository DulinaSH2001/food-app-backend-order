package com.order.order.Service;

import com.order.order.Dto.CartItemDTO;
import com.order.order.Model.CartItem;
import com.order.order.Repository.CartRepository;
import com.order.order.exception.CartItemNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public CartItem addToCart(Long customerId, Long restaurantId, CartItemDTO cartItemDTO) {
        // Check if the item already exists in the cart
        Optional<CartItem> existingItem = cartRepository.findByCustomerIdAndRestaurantIdAndMenuItemIdAndIsActiveTrue(
                customerId, restaurantId, cartItemDTO.getMenuItemId());

        if (existingItem.isPresent()) {
            // If item exists, update its quantity
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + cartItemDTO.getQuantity());
            log.info("Updating existing cart item quantity for menuItemId: {}", cartItemDTO.getMenuItemId());
            return cartRepository.save(item);
        }

        // If item doesn't exist, create a new cart item
        CartItem cartItem = CartItem.builder()
                .customerId(customerId)
                .restaurantId(restaurantId)
                .menuItemId(cartItemDTO.getMenuItemId())
                .name(cartItemDTO.getName())
                .quantity(cartItemDTO.getQuantity())
                .price(cartItemDTO.getPrice())
                .specialInstructions(cartItemDTO.getSpecialInstructions())
                .imageUrl(cartItemDTO.getImageUrl())
                .isActive(true)
                .build();
        
        log.info("Creating new cart item for menuItemId: {}", cartItemDTO.getMenuItemId());
        return cartRepository.save(cartItem);
    }

    @Override
    public List<CartItem> getCartItems(Long customerId) {
        return cartRepository.findByCustomerIdAndIsActiveTrue(customerId);
    }

    @Override
    public List<CartItem> getCartItemsByRestaurant(Long customerId, Long restaurantId) {
        return cartRepository.findByCustomerIdAndRestaurantIdAndIsActiveTrue(customerId, restaurantId);
    }

    @Override
    public CartItem updateCartItem(Long cartItemId, CartItemDTO cartItemDTO) {
        CartItem cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found with id: " + cartItemId));
        
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItem.setPrice(cartItemDTO.getPrice());
        cartItem.setSpecialInstructions(cartItemDTO.getSpecialInstructions());
        cartItem.setImageUrl(cartItemDTO.getImageUrl());
        
        return cartRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, Integer quantity) {
        CartItem cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found with id: " + cartItemId));
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        
        cartItem.setQuantity(quantity);
        return cartRepository.save(cartItem);
    }

    @Override
    public void removeFromCart(Long cartItemId) {
        if (!cartRepository.existsById(cartItemId)) {
            throw new CartItemNotFoundException("Cart item not found with id: " + cartItemId);
        }
        cartRepository.deleteById(cartItemId);
    }

    @Override
    public void clearCart(Long customerId, Long restaurantId) {
        cartRepository.deleteByCustomerIdAndRestaurantId(customerId, restaurantId);
    }

    @Override
    public void deactivateCartItems(Long customerId, Long restaurantId) {
        List<CartItem> cartItems = cartRepository.findByCustomerIdAndRestaurantIdAndIsActiveTrue(customerId, restaurantId);
        cartItems.forEach(item -> {
            item.setIsActive(false);
            cartRepository.save(item);
        });
    }
} 