package com.order.order.Service.Impl;

import com.order.order.Dto.DeliveryRequestDTO;
import com.order.order.Dto.OrderItemDTO;
import com.order.order.Dto.OrderRequestDTO;
import com.order.order.Model.Order;
import com.order.order.Model.OrderStatus;
import com.order.order.Model.PaymentMethod;
import com.order.order.Model.PaymentStatus;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequestDTO orderRequest);
    List<Order> getAllOrders();
    Order getOrderById(Long orderId);
    List<Order> getOrdersByCustomerId(Long customerId);
    List<Order> getOrdersByRestaurantId(Long restaurantId);
    Order updateOrderStatus(Long orderId, OrderStatus status);
    Order updateOrderItems(Long orderId, List<OrderItemDTO> orderItems);
    Order updateDeliveryRequest(Long orderId, DeliveryRequestDTO deliveryRequest);
    void deleteOrder(Long orderId);
    
    // New methods
    List<Order> getOrdersByStatus(OrderStatus status);
    List<Order> getOrdersByStatusAndDate(OrderStatus status, LocalDate date);
    List<Order> getReadyForDeliveryOrders();
    List<Order> getTodayOrders();
    List<Order> getOrdersByRestaurantIdAndStatus(Long restaurantId, OrderStatus status);
    
    // Payment related methods
    Order updatePaymentStatus(Long orderId, PaymentStatus status, String transactionId);
    List<Order> getOrdersByPaymentStatus(PaymentStatus status);
    List<Order> getOrdersByPaymentMethod(PaymentMethod method);
}
