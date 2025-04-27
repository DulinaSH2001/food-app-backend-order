package com.order.order.Service;

import com.order.order.Dto.OrderItemDTO;
import com.order.order.Dto.OrderRequestDTO;
import com.order.order.Dto.DeliveryRequestDTO;
import com.order.order.Model.Order;
import com.order.order.Model.OrderItem;
import com.order.order.Model.OrderStatus;
import com.order.order.Model.PaymentMethod;
import com.order.order.Model.PaymentStatus;
import com.order.order.Repository.OrderRepository;
import com.order.order.Service.Impl.OrderService;
import com.order.order.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(OrderRequestDTO orderRequest) {
        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setRestaurantId(orderRequest.getRestaurantId());
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setPaymentStatus(PaymentStatus.PENDING);
        
        // Set delivery request
        order.setDeliveryRequest(orderRequest.getDeliveryRequest());
        
        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(this::convertToOrderItem)
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);
        
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Order> getOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderItems(Long orderId, List<OrderItemDTO> orderItems) {
        Order order = getOrderById(orderId);
        List<OrderItem> updatedItems = orderItems.stream()
                .map(this::convertToOrderItem)
                .collect(Collectors.toList());
        order.setOrderItems(updatedItems);
        return orderRepository.save(order);
    }

    @Override
    public Order updateDeliveryRequest(Long orderId, DeliveryRequestDTO deliveryRequest) {
        Order order = getOrderById(orderId);
        order.setDeliveryRequest(deliveryRequest);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Order not found with id: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<Order> getOrdersByStatusAndDate(OrderStatus status, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return orderRepository.findByStatusAndCreatedAtBetween(status, startOfDay, endOfDay);
    }

    @Override
    public List<Order> getReadyForDeliveryOrders() {
        return orderRepository.findByStatus(OrderStatus.READY_FOR_DELIVERY);
    }

    @Override
    public List<Order> getTodayOrders() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        return orderRepository.findByCreatedAtBetween(startOfDay, endOfDay);
    }

    @Override
    public List<Order> getOrdersByRestaurantIdAndStatus(Long restaurantId, OrderStatus status) {
        return orderRepository.findByRestaurantIdAndStatus(restaurantId, status);
    }

    @Override
    public Order updatePaymentStatus(Long orderId, PaymentStatus status, String transactionId) {
        Order order = getOrderById(orderId);
        order.setPaymentStatus(status);
        if (transactionId != null) {
            order.setPaymentTransactionId(transactionId);
        }
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByPaymentStatus(PaymentStatus status) {
        return orderRepository.findByPaymentStatus(status);
    }

    @Override
    public List<Order> getOrdersByPaymentMethod(PaymentMethod method) {
        return orderRepository.findByPaymentMethod(method);
    }

    private OrderItem convertToOrderItem(OrderItemDTO dto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItemId(dto.getMenuItemId());
        orderItem.setName(dto.getName());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPrice(dto.getPrice());
        orderItem.setSpecialInstructions(dto.getSpecialInstructions());
        return orderItem;
    }
} 