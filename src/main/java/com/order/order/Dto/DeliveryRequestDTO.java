package com.order.order.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequestDTO {
    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    @NotBlank(message = "Delivery location is required")
    private String deliveryLocation;

    @NotBlank(message = "Restaurant address is required")
    private String restaurantAddress;

    @NotBlank(message = "Restaurant location is required")
    private String restaurantLocation;

    @NotBlank(message = "Delivery instructions are required")
    private String deliveryInstructions;
} 