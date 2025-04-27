package com.order.order.Repository;

import com.order.order.Model.CartItem;
import com.order.order.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCustomerIdAndIsActiveTrue(Long customerId);
    List<CartItem> findByCustomerIdAndRestaurantIdAndIsActiveTrue(Long customerId, Long restaurantId);
    void deleteByCustomerIdAndRestaurantId(Long customerId, Long restaurantId);
    Optional<CartItem> findByCustomerIdAndRestaurantIdAndMenuItemIdAndIsActiveTrue(Long customerId, Long restaurantId, Long menuItemId);

    interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    }
}