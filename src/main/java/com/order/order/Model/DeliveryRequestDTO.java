package com.order.order.Model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequestDTO {
    @NotNull(message = "Delivery location is required")
    @Valid
    private Location deliveryLocation;

    @NotBlank(message = "Delivery instructions are required")
    private String deliveryInstructions;

    private String contactNumber;
} 