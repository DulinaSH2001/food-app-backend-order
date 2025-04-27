package com.order.order.Dto;

import com.order.order.Model.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

    @NotEmpty(message = "Order items cannot be empty")
    @Valid
    private List<OrderItemDTO> orderItems;

    @NotNull(message = "Delivery request is required")
    @Valid
    private DeliveryRequestDTO deliveryRequest;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
} 