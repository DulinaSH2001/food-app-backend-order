package com.order.order.Controller;

import com.order.order.Dto.CartItemDTO;
import com.order.order.Model.CartItem;
import com.order.order.Service.Impl.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping("/{customerId}/restaurant/{restaurantId}")
    public ResponseEntity<CartItem> addToCart(
            @PathVariable Long customerId,
            @PathVariable Long restaurantId,
            @Valid @RequestBody CartItemDTO cartItemDTO) {
        log.info("Adding item to cart for customer: {}, restaurant: {}", customerId, restaurantId);
        return new ResponseEntity<>(cartService.addToCart(customerId, restaurantId, cartItemDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long customerId) {
        log.info("Getting cart items for customer: {}", customerId);
        return ResponseEntity.ok(cartService.getCartItems(customerId));
    }

    @GetMapping("/{customerId}/restaurant/{restaurantId}")
    public ResponseEntity<List<CartItem>> getCartItemsByRestaurant(
            @PathVariable Long customerId,
            @PathVariable Long restaurantId) {
        log.info("Getting cart items for customer: {} and restaurant: {}", customerId, restaurantId);
        return ResponseEntity.ok(cartService.getCartItemsByRestaurant(customerId, restaurantId));
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody CartItemDTO cartItemDTO) {
        log.info("Updating cart item: {}", cartItemId);
        return ResponseEntity.ok(cartService.updateCartItem(cartItemId, cartItemDTO));
    }

    @PutMapping("/{cartItemId}/quantity")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestParam @Valid Integer quantity) {
        log.info("Updating quantity for cart item: {} to {}", cartItemId, quantity);
        return ResponseEntity.ok(cartService.updateCartItemQuantity(cartItemId, quantity));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartItemId) {
        log.info("Removing cart item: {}", cartItemId);
        cartService.removeFromCart(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}/restaurant/{restaurantId}")
    public ResponseEntity<Void> clearCart(
            @PathVariable Long customerId,
            @PathVariable Long restaurantId) {
        log.info("Clearing cart for customer: {} and restaurant: {}", customerId, restaurantId);
        cartService.clearCart(customerId, restaurantId);
        return ResponseEntity.noContent().build();
    }
} 