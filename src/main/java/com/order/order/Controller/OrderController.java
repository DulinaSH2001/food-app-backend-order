package com.order.order.Controller;

import com.order.order.Dto.DeliveryRequestDTO;
import com.order.order.Dto.OrderItemDTO;
import com.order.order.Dto.OrderRequestDTO;
import com.order.order.Model.Order;
import com.order.order.Model.OrderStatus;
import com.order.order.Model.PaymentMethod;
import com.order.order.Model.PaymentStatus;
import com.order.order.Service.Impl.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080/")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequestDTO orderRequest) {
        Order createdOrder = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Order>> getOrdersByRestaurantId(@PathVariable Long restaurantId) {
        List<Order> orders = orderService.getOrdersByRestaurantId(restaurantId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable OrderStatus status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}/date/{date}")
    public ResponseEntity<List<Order>> getOrdersByStatusAndDate(
            @PathVariable OrderStatus status,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Order> orders = orderService.getOrdersByStatusAndDate(status, date);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/ready-for-delivery")
    public ResponseEntity<List<Order>> getReadyForDeliveryOrders() {
        List<Order> orders = orderService.getReadyForDeliveryOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/today")
    public ResponseEntity<List<Order>> getTodayOrders() {
        List<Order> orders = orderService.getTodayOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @PutMapping("/{orderId}/items")
    public ResponseEntity<Order> updateOrderItems(
            @PathVariable Long orderId,
            @Valid @RequestBody List<OrderItemDTO> orderItems) {
        Order updatedOrder = orderService.updateOrderItems(orderId, orderItems);
        return ResponseEntity.ok(updatedOrder);
    }

    @PutMapping("/{orderId}/delivery")
    public ResponseEntity<Order> updateDeliveryRequest(
            @PathVariable Long orderId,
            @Valid @RequestBody DeliveryRequestDTO deliveryRequest) {
        Order updatedOrder = orderService.updateDeliveryRequest(orderId, deliveryRequest);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/restaurant/{restaurantId}/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByRestaurantIdAndStatus(
            @PathVariable Long restaurantId,
            @PathVariable OrderStatus status) {
        List<Order> orders = orderService.getOrdersByRestaurantIdAndStatus(restaurantId, status);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/payment-status")
    public ResponseEntity<Order> updatePaymentStatus(
            @PathVariable Long orderId,
            @RequestParam PaymentStatus status,
            @RequestParam(required = false) String transactionId) {
        Order updatedOrder = orderService.updatePaymentStatus(orderId, status, transactionId);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/payment-status/{status}")
    public ResponseEntity<List<Order>> getOrdersByPaymentStatus(@PathVariable PaymentStatus status) {
        List<Order> orders = orderService.getOrdersByPaymentStatus(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/payment-method/{method}")
    public ResponseEntity<List<Order>> getOrdersByPaymentMethod(@PathVariable PaymentMethod method) {
        List<Order> orders = orderService.getOrdersByPaymentMethod(method);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/statues")
    public ResponseEntity<List<OrderStatus>> getAllOrderStatuses() {
        List<OrderStatus> statuses = List.of(OrderStatus.values());
        return ResponseEntity.ok(statuses);
    }
}